package com.wojustme.executor;

import java.util.concurrent.*;

/**
 * callable 有返回值
 * runnable 无返回值
 * 构建ExecutorService线程池框架调用
 * Created by wojustme on 2017/6/13.
 */
public class ExecutorTest {
  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(2);
    Task task = new Task();
    Task1 task1 = new Task1();
    Future<String> submit = es.submit(task);
    Future<?> submit1 = es.submit(task1);
    try {
      System.out.println(submit.get());
      System.out.println(" -> " + submit1.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    es.shutdown();
  }
}

class Task implements Callable<String> {
  public String call() throws Exception {
    return "hello";
  }
}
class Task1 implements Runnable {
  public void run() {
    System.out.println("hello task1");
  }
}
