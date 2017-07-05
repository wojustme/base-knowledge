package com.wojustme.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
 * wojustme于2017/6/29祈祷...
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class MyHttpServlet extends HttpServlet {

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("this service do");
    System.out.println(req);
    try {
      Thread.sleep(1000 * 20);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    PrintWriter writer = resp.getWriter();
    writer.write("return get ok");
    writer.close();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("hello get");
    System.out.println(req);
//    try {
//      Thread.sleep(1000 * 20);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    PrintWriter writer = resp.getWriter();
    writer.write("return get ok");
    writer.close();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("hello post");
    PrintWriter writer = resp.getWriter();
    writer.write("return post ok");
    writer.close();
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("hello put");
    PrintWriter writer = resp.getWriter();
    writer.write("return put ok");
    writer.close();
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    System.out.println("hello del");
    PrintWriter writer = resp.getWriter();
    writer.write("return del ok");
    writer.close();
  }
}
