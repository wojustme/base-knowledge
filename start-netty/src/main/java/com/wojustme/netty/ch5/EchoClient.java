package com.wojustme.netty.ch5;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;


/**
 * @author wojustme
 * @date 2017/7/4
 * @package com.wojustme.netty.ch5
 */
public class EchoClient {

  public void connet(int port, String host) {


    EventLoopGroup group = new NioEventLoopGroup();

    try {

      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
              socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
              socketChannel.pipeline().addLast(new StringDecoder());
              socketChannel.pipeline().addLast(new EchoClientHandler());
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
    new EchoClient().connet(9527, "localhost");
  }

}

class EchoClientHandler extends ChannelHandlerAdapter {

  private int counter;

  static final String ECHO_REQ = "Hi, Xurenhe. Welcome to Netty.$_";

  public EchoClientHandler() {

  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    for (int i = 0; i < 10; i++) {
      ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println("This is " + ++counter + " times receive server: [" + msg + "]");
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}