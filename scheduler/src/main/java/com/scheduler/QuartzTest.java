package com.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

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
 * wojustme于2017/6/26祈祷...
 */
public class QuartzTest {

  public static void main(String[] args) {
    try {
      // 打印当前的时间，格式为2017-01-01 00:00:00
      Date date = new Date();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      System.out.println("Current Time Is : " + sf.format(date));
      // 创建一个JobDetail实例，将该实例与HelloJob Class绑定
      JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
          .withIdentity("myJob").build();
      CronTrigger trigger = (CronTrigger) TriggerBuilder
          .newTrigger()
          .withIdentity("myTrigger", "group1")
          .withSchedule(
              CronScheduleBuilder.cronSchedule("* * * * * ?"))
          .build();
      // 创建Scheduler实例
      SchedulerFactory sfact = new StdSchedulerFactory();
      Scheduler scheduler = sfact.getScheduler();
      scheduler.start();
      System.out.println("scheduled time is :"
          + sf.format(scheduler.scheduleJob(jobDetail, trigger)));
      //scheduler执行两秒后挂起
      Thread.sleep(2000L);
      //shutdown(true)表示等待所有正在执行的job执行完毕之后，再关闭scheduler
      //shutdown(false)即shutdown()表示直接关闭scheduler
      scheduler.shutdown(false);
      System.out.println("scheduler is shut down? " + scheduler.isShutdown());

    } catch (SchedulerException se) {
      se.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
