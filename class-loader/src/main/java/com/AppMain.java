package com;

import org.apache.log4j.Logger;

/**
 * Created by wojustme on 2017/6/20.
 */
public class AppMain {

  private static Logger LOGGER = Logger.getLogger("");

  public static void main(String[] args) {
    LOGGER.info("logger hello");
    System.out.println("hello");
  }
}
