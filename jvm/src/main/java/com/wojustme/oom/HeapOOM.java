package com.wojustme.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wojustme on 2017/6/12.
 */

// -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/wojustme/test

public class HeapOOM {
  static class OOMObject {

  }

  public static void main(String[] args) {
    List<OOMObject> list = new ArrayList<OOMObject>();
    while (true) {
      list.add(new OOMObject());
    }
  }
}
