package com.wojustme.netty.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

/**
 * @author wojustme
 * @date 2017/7/4
 * @package com.wojustme.netty.ch3
 */
public class TimeServer {

  public void bind(int port) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .childHandler(new ChildHander());

      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();

    } catch (InterruptedException e) {

    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }


  private class ChildHander extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
      socketChannel.pipeline().addLast(new TimeServerHandler());
    }
  }


  public static void main(String[] args) {
    int port = 9527;
    new TimeServer().bind(port);
  }

}


class TimeServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    ByteBuf buf = (ByteBuf) msg;
    byte[] req = new byte[buf.readableBytes()];

    buf.readBytes(req);
    String body = new String(req, "UTF-8");
    System.out.println("The time server receive order: " + body);

    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";

    ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
    ctx.write(resp);

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