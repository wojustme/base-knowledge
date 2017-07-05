package com.wojustme.netty.ch4;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wojustme
 * @date 2017/7/4
 * @package com.wojustme.netty.ch3
 */
public class TimeClient {

  public void connect(int port, String host) {
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap();
      b.group(group).channel(NioSocketChannel.class)
          .option(ChannelOption.TCP_NODELAY, true)
          .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
              socketChannel.pipeline().addLast(new StringDecoder());
              socketChannel.pipeline().addLast(new TimeClientHandler());
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
    new TimeClient().connect(9527, "127.0.0.1");
  }

}


class TimeClientHandler extends ChannelInboundHandlerAdapter {

  private int counter;

  private byte[] req;


  public TimeClientHandler() {
    req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();

  }


  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    ByteBuf message = null;
    for (int i = 0; i < 100; i++) {
      message = Unpooled.buffer(req.length);
      message.writeBytes(req);
      ctx.writeAndFlush(message);
    }

  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    String body = (String) msg;

    System.out.println("Now is: " + body + "; the counter is: " + ++counter);


  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}