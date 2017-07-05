package com.wojustme.msgqueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xurenhe on 2017/6/27.
 */
public class Consumer implements Runnable {

  BlockingQueue<String> queue;

  public Consumer(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {

  }
}
