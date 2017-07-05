package com.wojustme.netty.socket;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @author wojustme
 * @date 2017/7/3
 * @package com.wojustme.netty.socket
 */
public class DiscardServer {

  private int port;

  public DiscardServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap(); // (2)
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class) // (3)
          .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ch.pipeline().addLast(new DiscardServerHandler());
            }
          })
          .option(ChannelOption.SO_BACKLOG, 128)          // (5)
          .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

      // 绑定端口，开始接收进来的连接
      ChannelFuture f = b.bind(port).sync(); // (7)

      // 等待服务器  socket 关闭 。
      // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {

    new DiscardServer(9527).run();
  }
}


class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
//    // 默默地丢弃收到的数据
//    ((ByteBuf) msg).release(); // (3)
    ByteBuf in = (ByteBuf) msg;
    try {
      while (in.isReadable()) { // (1)
        System.out.print((char) in.readByte());
        System.out.flush();
      }
    } finally {
      ReferenceCountUtil.release(msg); // (2)
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
    // 当出现异常就关闭连接
    cause.printStackTrace();
    ctx.close();
  }
}