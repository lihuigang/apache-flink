package com.meituan.waimai.scaladir.flinkTest

import com.meituan.waimai.scaladir.dao._
import com.meituan.waimai.scaladir.entity.HttpApi

import scala.beans.BeanProperty



object Test  {

  def main(args:Array[String]): Unit ={
    var testDao  = new Testt()
    val httpapi = new HttpApi()
    print(testDao.save(httpapi))
  }
}

class Testt extends TestDao[HttpApi] {
  def save(t: HttpApi): HttpApi = {
    return t
  }

}

