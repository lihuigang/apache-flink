package com.meituan.waimai.scaladir.flinkTest

import java.util.Properties

import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import com.meituan.waimai.scaladir.flinkTest.MySQLExec._
/**
  * Created by https://www.iteblog.com on 2016/5/3.
  */
object FlinkFafkaTest {
  def main(args: Array[String]) {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.enableCheckpointing(5000)
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    // only required for Kafka 0.8
    properties.setProperty("zookeeper.connect", "localhost:2181")
    properties.setProperty("group.id", "mytopic")

    val stream = env.addSource(new FlinkKafkaConsumer011[String]("mytopic",
      new SimpleStringSchema(), properties))
    var value = stream.setParallelism(4) //writeAsText("/tmp/data")
    value.addSink(x => getData(x))
    env.execute("IteblogFlinkKafkaStreaming")
  }

  def getData(data:String) : Unit ={
    var dataSplit = data.split(":")
    var sql = "replace into orders(id,no,price) values("+dataSplit(0)+",\""+dataSplit(1)+"\","+dataSplit(2)+")"
    println(sql)
    execSql(sql,"dev")
  }
}


