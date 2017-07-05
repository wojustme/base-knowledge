package com.wojustme.netty.ch7;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wojustme
 * @date 2017/7/5
 * @package com.wojustme.netty.ch7
 */
public class SubReqServer {

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
              socketChannel.pipeline().addLast(new ObjectDecoder(
                 1024 * 1024,
                  ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())
              ));
              socketChannel.pipeline().addLast(new ObjectEncoder());
              socketChannel.pipeline().addLast(new SubReqServerHandler());
            }
          });

      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {

    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    new SubReqServer().bind(9527);
  }
}

class SubReqServerHandler extends ChannelHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    SubscribeReq req = (SubscribeReq) msg;

//    if ("xurenhe".equalsIgnoreCase(req.getUserName())) {
      System.out.println("Server accept client subscribe req: [" + req.toString() + "]");
      ctx.writeAndFlush(resp(req.getSubReqID()));
//    }
  }

  private SubscribeResp resp(int subReqID) {
    SubscribeResp resp = new SubscribeResp();
    resp.setSubReqID(subReqID);
    resp.setRespCode(0);
    resp.setDesc("Netty book order succeed, 3days later, sent to the designated address");
    return resp;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}