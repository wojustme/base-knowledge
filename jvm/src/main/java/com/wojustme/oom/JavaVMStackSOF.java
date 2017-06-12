package com.wojustme.oom;

/**
 * Created by wojustme on 2017/6/12.
 */

// -Xss128k
public class JavaVMStackSOF {
  private int stackLength = 1;

  public void stackLeak() {
    stackLength++;
    stackLeak();
  }

  public static void main(String[] args) throws Throwable {
    JavaVMStackSOF javaVMStackSOF = new JavaVMStackSOF();

    javaVMStackSOF.stackLeak();
    try {

    } catch (Throwable e) {
      System.out.println("stack length" + javaVMStackSOF.stackLength);
      throw e;
    }
  }
}
