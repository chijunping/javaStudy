package com.web.controller

import java.util

import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

@RestController
class ScalaController {

  @RequestMapping(Array("testGetJSon"))
  def testGetJSon(@RequestParam(value = "url") url: String)={
    val returnMap = new util.HashMap[String,Object]()
    returnMap.put("aa","aa")
    returnMap.put("bb","bb")
  }
}
