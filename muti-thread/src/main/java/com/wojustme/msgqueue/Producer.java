package com.wojustme.msgqueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xurenhe on 2017/6/27.
 */
public class Task1 implements Runnable {

  BlockingQueue<String> queue;

  public Task1(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  public void run() {
    while (true)
  }
}
