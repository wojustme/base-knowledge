package com.wojustme.netty.httpfile;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author wojustme
 * @date 2017/7/14
 * @package com.wojustme.netty.httpfile
 */
public class HttpServer {

  public void run(int port) {

    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(bossGroup, workGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast(new HttpRequestDecoder());
              pipeline.addLast(new HttpResponseEncoder());
              pipeline.addLast(new ChunkedWriteHandler());
              pipeline.addLast(new FileHandler());
            }
          });

      ChannelFuture sync = serverBootstrap.bind(port).sync();
      sync.channel().closeFuture().sync();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) {
    new HttpServer().run(8787);
  }
}
