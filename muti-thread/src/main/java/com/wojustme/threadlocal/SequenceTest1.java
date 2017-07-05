package com.wojustme.threadlocal;

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
public class SequenceTest1 implements Sequence {

  private static int number = 0;

  @Override
  public int getNumber() {
    number += 1;
    return number;
  }

  public static void main(String[] args) {
    Sequence sequence = new SequenceTest1();

    ClientThread thread1 = new ClientThread(sequence);
    ClientThread thread2 = new ClientThread(sequence);
    ClientThread thread3 = new ClientThread(sequence);
    ClientThread thread4 = new ClientThread(sequence);
    ClientThread thread5 = new ClientThread(sequence);

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    thread5.start();



  }
}
