package com.example.SecurityExample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DBTest {
    @Autowired
    private DataSource dataSource;
    @Test
    public void textConnection(){
        try{
            Connection con = dataSource.getConnection();
        } catch (SQLException throwables) {
            System.out.println("오류");
            throwables.printStackTrace();
        }
    }
}
