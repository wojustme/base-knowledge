package com.wojustme.netty.socket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by xurenhe on 2017/6/22.
 */
public class DiscardServerHandler implements ChannelHandler, EventExecutorGroup {
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

  }

  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

  }

  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

  }

  public boolean isShuttingDown() {
    return false;
  }

  public Future<?> shutdownGracefully() {
    return null;
  }

  public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
    return null;
  }

  public Future<?> terminationFuture() {
    return null;
  }

  public void shutdown() {

  }

  public List<Runnable> shutdownNow() {
    return null;
  }

  public EventExecutor next() {
    return null;
  }

  public Iterator<EventExecutor> iterator() {
    return null;
  }

  public Future<?> submit(Runnable task) {
    return null;
  }

  public <T> Future<T> submit(Runnable task, T result) {
    return null;
  }

  public <T> Future<T> submit(Callable<T> task) {
    return null;
  }

  public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
    return null;
  }

  public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
    return null;
  }

  public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
    return null;
  }

  public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
    return null;
  }

  public boolean isShutdown() {
    return false;
  }

  public boolean isTerminated() {
    return false;
  }

  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return false;
  }

  public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
    return null;
  }

  public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
    return null;
  }

  public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
    return null;
  }

  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return null;
  }

  public void execute(Runnable command) {

  }
}
