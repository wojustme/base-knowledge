package com.wojustme.saveself;

/**
 * 1. 对象可以在被GC时自我拯救
 * 2. 这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次。
 * Created by wojustme on 2017/6/13.
 */
public class FinalizeEscapeGC {

  public static FinalizeEscapeGC SAVE_HOOK = null;

  public void isAlive() {
    System.out.println("yes, i am still alive :)");
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    System.out.println("finalize method executed!");
    FinalizeEscapeGC.SAVE_HOOK = this;
  }

  public static void main(String[] args) throws Throwable {
    SAVE_HOOK = new FinalizeEscapeGC();

    SAVE_HOOK = null;
    System.gc();
    // 因为finalize方法优先级很低，所以暂停0.5秒等待它
    Thread.sleep(500);
    if (SAVE_HOOK != null) {
      SAVE_HOOK.isAlive();
    } else {
      System.out.println("no, iam dead :(");
    }


    // 自救失败
    SAVE_HOOK = null;
    System.gc();
    Thread.sleep(500);
    if (SAVE_HOOK != null) {
      SAVE_HOOK.isAlive();
    } else {
      System.out.println("no, iam dead :(");
    }


  }
}
