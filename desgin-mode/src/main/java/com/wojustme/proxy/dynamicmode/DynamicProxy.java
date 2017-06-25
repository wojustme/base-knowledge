package com.wojustme.proxy.dynamicmode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //             佛祖保佑       永无BUG     永不修改                   //
 * ////////////////////////////////////////////////////////////////////
 * <p>
 * wojustme于2017/6/25祈祷...
 */
public class DynamicProxy implements InvocationHandler {

  // 需要被代理的对象
  private Object target;

  public DynamicProxy(Object target) {
    this.target = target;
  }


  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    before();
    Object result = method.invoke(target, args);
    after();
    return result;

  }

  private void before() {
    System.out.println("动态代理before...");
  }
  private void after() {
    System.out.println("动态代理after...");
  }


}
