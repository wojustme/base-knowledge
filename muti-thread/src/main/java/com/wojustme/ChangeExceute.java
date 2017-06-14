package com.wojustme;

/**
 * 改变变量，从而改变线程执行
 * Created by wojustme on 2017/6/13.
 */
public class ChangeExceute {

  public static void main(String[] args) {
    Task task = new Task("task");
    Thread thread1 = new Thread(task);
    Thread thread2 = new Thread(task);
    thread1.start();
    thread2.start();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    task.setTaskType("ok");

  }
}

class Task implements Runnable {

  private String taskName;
  private String taskType = "error";

  public Task(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public void run() {
    while (true) {
      try {
        Thread.sleep(1000);
        if (taskType.equals("ok")) {
          System.out.println("this task is do ok");
        } else {
          System.out.println("this task is do error");
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}