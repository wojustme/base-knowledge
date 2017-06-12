package com.wojustme.observer;

/**
 * Created by wojustme on 2017/6/10.
 */
public interface Observer {
  void update(float temp, float humidity, float pressure);
}
