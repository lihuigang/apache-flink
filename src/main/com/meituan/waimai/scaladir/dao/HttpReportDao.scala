package com.meituan.waimai.scaladir.dao

import java.util.List

import com.meituan.waimai.scaladir.entity._
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

trait HttpReportDao extends CrudRepository[HttpReport, Integer] {
  @Query(value = "select * from `http_report`", nativeQuery = true)
  def findAll(): List[HttpReport] // JavaConversions

  def save(t: HttpReport): HttpReport

  def findOne(id: Integer): HttpReport

}
