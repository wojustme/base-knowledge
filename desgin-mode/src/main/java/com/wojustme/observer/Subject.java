package com.wojustme.observer;

/**
 * 需要订阅的一个主题接口
 * Created by wojustme on 2017/6/10.
 */
public interface Subject {
  void registerObserver(Observer o);
  void removeObserver(Observer o);
  void notifyObservers();
}
