package com.bean;

import java.io.Serializable;

/**
 * Created by wojustme on 2017/6/15.
 */
public class Teacher implements Serializable {
  private String name;

  public Teacher(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
