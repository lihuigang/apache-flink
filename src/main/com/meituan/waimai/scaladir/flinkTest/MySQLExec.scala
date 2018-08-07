package com.meituan.waimai.scaladir.flinkTest

import java.sql.{Connection, DriverManager}

import scala.collection.mutable.ArrayBuffer

class MySQLExec(stage:String) {

  val driver = "com.mysql.jdbc.Driver"


  def getUrl(): String ={
    if (stage == "dev") {
      return "jdbc:mysql://10.4.231.27:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false"
    } else{
      return "jdbc:mysql://gh-waimai-mysql-flowdata01:5002/test?useUnicode=true&characterEncoding=utf8"
    }
  }

  def getUser(): String ={
    if (stage == "dev") {
      return "root"
    } else{
      return "flowdata"
    }
  }

  def getPasswd(): String ={
    if (stage == "dev") {
      return "12345678"
    } else{
      return "ZjfIsYXMvIKdel"
    }
  }

  def getTableData(tableName:String,columns:Array[String]) :Array[String]={

    var connection:Connection = null
    var columnsArray =  new ArrayBuffer[String]()
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(getUrl(), getUser(), getPasswd())

      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("select event_id, event_name from datahub_flow_event")
      while ( resultSet.next() ) {
        val eventId = resultSet.getString("event_id")
        val eventName = resultSet.getString("event_name")
        println("name, password = " + eventId + ", " + eventName)
      }
    } catch {
      //case e => e.printStackTrace
      case e: Throwable => println("ERROR")
    }
    connection.close()
    return columnsArray.toArray
  }

  def getTableColumns(tableName:String):Array[String]= {

    var connection:Connection = null
    var columnsArray =  new ArrayBuffer[String]()
    try {

      Class.forName(driver)
      connection = DriverManager.getConnection(getUrl(), getUser(), getPasswd())
      var statement = connection.getMetaData
      val resultSet = statement.getColumns( null,"%", tableName, "%")
      while ( resultSet.next() ) {
        columnsArray += resultSet.getString(4)
        //println(columnsArray.toSeq)
      }
    } catch {
      case e: Throwable => println("ERROR")
    }
    connection.close()
    return columnsArray.toArray
  }

  def excUpdateSql(sql:String):Unit={

    var connection:Connection = null
    var columnsArray =  new ArrayBuffer[String]()
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(getUrl(), getUser(), getPasswd())

      val statement = connection.createStatement()
      val resultSet = statement.execute(sql)
    } catch {
      //case e => e.printStackTrace
      case e: Throwable => println(e)
    }
    //connection.close()
  }
}

object MySQLExec {
  def execSql(sql:String,stage:String){
    var mysql = new MySQLExec(stage)
    mysql.excUpdateSql(sql)
  }
}
