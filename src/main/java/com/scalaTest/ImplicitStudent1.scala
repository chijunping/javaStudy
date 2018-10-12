package com.scalaTest

/**
  * 摘要：
  * 通过隐式转换，程序员可以在编写Scala程序时故意漏掉一些信息，让编译器去尝试在编译期间自动推导出这些信息来，这种特性可以极大的减少代码量，忽略那些冗长，过于细节的代码。
  * *
  * 使用方式：
  *1.将方法或变量标记为implicit
  *2.将方法的参数列表标记为implicit
  *3.将类标记为implicit
  * *
  * Scala支持两种形式的隐式转换：
  * 隐式值：用于给方法提供参数
  * 隐式视图：用于类型间转换或使针对某类型的方法能调用成功
  */


/**
  * 隐式转换之——隐式值
  * implicit val name="jack"
  */
object ImplicitStudent1 {

  def student(implicit name: String) = {
    println(name)
  }

  def main(args: Array[String]): Unit = {
    //正常传值，没问题
    //student(name = "jack")
    //不传参时，需要有一个隐式值
    //    implicit val jack = "jack"
    //    student
    //定义两个类型一样的隐式值会报错，隐式值必须满足类型单一原则
//    implicit val jack = "jack"
//    implicit val rose = "jack"
//    student
  }
}
