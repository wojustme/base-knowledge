package com.wojustme.netty.ch7;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author wojustme
 * @date 2017/7/5
 * @package com.wojustme.netty.ch7
 */
public class SubReqClient {

  public void connect(int port, String host) {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group).channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(new ObjectDecoder(
                  1024 * 1024,
                  ClassResolvers.cacheDisabled(this.getClass().getClassLoader())
              ));
              socketChannel.pipeline().addLast(new ObjectEncoder());
              socketChannel.pipeline().addLast(new SubReqClientrHandler());
            }
          });

      ChannelFuture f = b.connect(host, port).sync();

      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    new SubReqClient().connect(9527, "localhost");
  }
}

class SubReqClientrHandler extends ChannelHandlerAppender {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    for (int i = 0; i < 10; i++) {
      ctx.write(subReq(i));
    }
    ctx.flush();
  }


  private SubscribeReq subReq(int i) {
    SubscribeReq req = new SubscribeReq();

    req.setAddress("南京");
    req.setPhoneNumber("138");
    req.setProductName("Netty 权威指南");
    req.setSubReqID(i);
    req.setUserName("Lilinfeng");

    return req;
  }


  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("Receive server response: [" + msg + "]");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
