package com.wojustme.netty.ch3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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

  private final ByteBuf firstMessage;


  public TimeClientHandler() {
    byte[] req = "QUERY TIME ORDER".getBytes();
    firstMessage = Unpooled.buffer(req.length);
    firstMessage.writeBytes(req);
  }


  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(firstMessage);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf) msg;

    byte[] req = new byte[buf.readableBytes()];

    buf.readBytes(req);
    String body = new String(req, "UTF-8");
    System.out.println("Now is: " + body);


    ctx.close();

  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}