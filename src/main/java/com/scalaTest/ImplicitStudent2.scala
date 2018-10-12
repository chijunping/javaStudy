package com.scalaTest

/**
  * 隐式转换——隐式转换函数
  * 隐式转换为目标类型：把一种类型自动转换到另一种类型
  */
object ImplicitStudent2 {
  implicit def int2Float(num: Int): Float = num.toFloat


  def main(args: Array[String]): Unit = {
    val fl: Float = int2Float(2)
    println(fl)
  }
}


