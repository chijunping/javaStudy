package com.scalaTest

import org.springframework.util.DigestUtils

/**
  * 隐式转换——隐式转换类
  */
object Implicit3_StringUtils {

  implicit class stringUtils(val str: String) { //隐式类
    def increment = str.map(x => (x + 1).toChar)

    def printName = println(s"str=$str")

    def string2Int = Integer.valueOf(str)

    def toMD5 = DigestUtils.md5DigestAsHex(str.getBytes())

    def concatNameAndAge(age: Int) = str + age

  }

}

object test {
  def main(args: Array[String]): Unit = {
    import com.scalaTest.Implicit3_StringUtils._
    println("mobin".increment)
    println("mobin".printName)
    println("23".string2Int)
    println("sss".toMD5)
    println("jack".concatNameAndAge(12))
  }
}