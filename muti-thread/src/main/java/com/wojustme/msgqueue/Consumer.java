package com.wojustme.msgqueue;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xurenhe on 2017/6/27.
 */
public class Producer implements Runnable {

  BlockingQueue<String> queue;

  public Producer(BlockingQueue<String> queue) {
    this.queue = queue;
  }

  public void run() {
    while (true) {
      try {
        String temp = queue.put("hello");//如果队列为空，会阻塞当前线程
        System.out.println(temp);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
