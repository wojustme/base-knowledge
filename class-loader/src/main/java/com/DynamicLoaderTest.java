package com;

import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by wojustme on 2017/6/21.
 */
public class DynamicLoaderTest {
  public static void main(String[] args) throws Exception {

    // 动态加载jar，解析类
    File file = new File("/Users/wojustme/Downloads/log4j-1.2.17.jar");
    URL url= file.toURI().toURL();
    URLClassLoader urlClassLoader=new URLClassLoader(new URL[] {url});
    Class clazz = urlClassLoader.loadClass("org.apache.log4j.Logger");

    // 利用反射执行
    Method method = clazz.getMethod("getLogger", Class.class);

    // 只用到maven中Logger的定义，相当于定义了一个借口而已
    Logger invoke = (Logger)method.invoke(null, DynamicLoaderTest.class);

    invoke.info("反射调用...");

  }
}
