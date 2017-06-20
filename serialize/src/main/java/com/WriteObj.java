package com;

import com.bean.Teacher;
import com.util.SayHello;

import java.io.*;

/**
 * Created by wojustme on 2017/6/15.
 */
public class WriteObj {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    Person person = new Person("xu", 12, new Teacher("yuwen"));

    FileOutputStream fos = new FileOutputStream("t.tmp");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(person);
    oos.close();
    fos.close();


    // read

    FileInputStream fis = new FileInputStream("t.tmp");
    ObjectInputStream  ois = new ObjectInputStream(fis);

    Person rPerson = (Person)ois.readObject();
    rPerson.sayHello();
    rPerson.sayMyTeacher();
    ois.close();
    fis.close();


  }
}

class Person implements Serializable {
  private String name;
  private int age;
  private Teacher myTeacher;

  public Person(String name, int age, Teacher myTeacher) {
    this.name = name;
    this.age = age;
    this.myTeacher = myTeacher;
  }

  public void sayHello() {
    System.out.println("I am " + name);
    SayHello.hello(name);
  }

  public void sayMyTeacher() {
    System.out.println("My teacher " + myTeacher.getName());
  }

}
