package com.wojustme.netty.ch11;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * @author wojustme
 * @date 2017/7/6
 * @package com.wojustme.netty.ch11
 */
public class WebSocketServer {

  public void run(int port) throws Exception {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
              ChannelPipeline pipeline = socketChannel.pipeline();
              pipeline.addLast("http-codec", new HttpServerCodec());
              pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
              pipeline.addLast("http-chunked", new ChunkedWriteHandler());
              pipeline.addLast("handler", new WebSocketServerHandler());
            }
          });
      Channel ch = b.bind(port).sync().channel();
      System.out.println("Web socket server started at port " + port + ".");
      ch.closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }

  }

  public static void main(String[] args) {
    try {
      new WebSocketServer().run(9527);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}


class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

  private WebSocketServerHandshaker handshaker;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof FullHttpRequest) {
      handleHttpRequest(ctx, (FullHttpRequest) msg);
    } else if (msg instanceof WebSocketFrame) {
      handleWebSocket(ctx, (WebSocketFrame) msg);
    }
  }


  private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
    if (!msg.getDecoderResult().isSuccess() || !"websocket".equals(msg.headers().get("Upgrade"))) {
      sendHttpResponse(ctx, msg, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
      return;

    }

    WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws:localhost:9527/websocket", null, false);

    handshaker = wsFactory.newHandshaker(msg);
    if (handshaker == null) {
      WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
    } else {
      handshaker.handshake(ctx.channel(), msg);
    }

  }

  private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
    if (frame instanceof CloseWebSocketFrame) {
      handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
      return;
    }
    if (frame instanceof PingWebSocketFrame) {
      ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
      return;
    }
    if (! (frame instanceof TextWebSocketFrame)) {
      throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
    }
    String request = ((TextWebSocketFrame) frame).text();
    ctx.channel().write(new TextWebSocketFrame(request + ", 欢迎使用Netty WebSocket服务，心在时刻：" + new Date().toString()));

  }



  private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse resp) {

    if (resp.getStatus().code() != 200) {
      ByteBuf buf = Unpooled.copiedBuffer(resp.getStatus().toString(), CharsetUtil.UTF_8);
      resp.content().writeBytes(buf);
      buf.release();
//      setContentLength(resp, resp.content().readableBytes());
    }

    ChannelFuture f = ctx.channel().writeAndFlush(resp);
    if (resp.getStatus().code() != 200) {
      f.addListener(ChannelFutureListener.CLOSE);
    }

  }


  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }


//  private boolean isKeepAlive(FullHttpRequest req) {
//    req.
//  }

//  private void setContentLength(DefaultFullHttpResponse resp, int i) {
//  }

}
