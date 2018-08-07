package com.meituan.waimai.scaladir.flinkTest

import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

/**
  * 可以直接本地运行
  */
object WordCount {

  def main(args: Array[String]) {
    val env = ExecutionEnvironment.createLocalEnvironment(1)

    //从本地读取文件
    val text = env.readTextFile("/Users/lihuigang/app/flink-1.4.2/README.txt")

    //单词统计
    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
      .map { (_, 1) }
      .groupBy(0)
      .sum(1)

    //输出结果
    counts.print()

    //保存结果到txt文件
    counts.writeAsText("/Users/lihuigang/lhg/output.txt", WriteMode.OVERWRITE)
    env.execute("Scala WordCount Example")

  }
}
