package com.meituan.waimai.scaladir.dao

import java.util.List

import com.meituan.waimai.scaladir.entity._
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

import scala.language.implicitConversions

trait TestDao[T] {


  def save(t: T): T


}