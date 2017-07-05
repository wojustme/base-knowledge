package com.wojustme.netty.ch5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wojustme
 * @date 2017/7/4
 * @package com.wojustme.netty.ch5
 */
public class EchoServer {

  public void bind(int port) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 100)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
              socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
              socketChannel.pipeline().addLast(new StringDecoder());
              socketChannel.pipeline().addLast(new EchoServerHandler());
            }
          });

      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    new EchoServer().bind(9527);
  }

}


class EchoServerHandler extends ChannelHandlerAdapter {

  int counter = 0;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    String body = (String) msg;

    System.out.println("This is " + ++counter + " times receive client: [" + body + "]");

    body += "$_";

    ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
    ctx.writeAndFlush(echo);

  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
