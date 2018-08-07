package com.meituan.waimai.javadir.flinkTest;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;


public class MySQLSink extends
        RichSinkFunction<Tuple3<Integer, String, Integer>> {

    private static final long serialVersionUID = 1L;
    private Connection connection;
    private PreparedStatement preparedStatement;
    String username = "root";
    String password = "12345678";
    String drivername = "com.mysql.jdbc.Driver";
    String dburl = "jdbc:mysql://10.4.231.27:3306/test?useUnicode=true&characterEncoding=utf8";

    @Override
    public void invoke(Tuple3<Integer, String, Integer> value) throws Exception {
        Class.forName(drivername);
        connection = DriverManager.getConnection(dburl, username, password);
        String sql = "replace into orders(id,no,price) values(?,?,?)";
        System.out.println(sql);
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, value.f0);
        preparedStatement.setString(2, value.f1);
        preparedStatement.setInt(3, value.f2);
        preparedStatement.executeUpdate();
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }

    }

}