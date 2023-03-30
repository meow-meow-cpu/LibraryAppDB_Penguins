package com.officeHoursPractice;

import com.library.utility.ConfigurationReader;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestJDBC {
    // the purpose is to understand how we came up with DB_Util CLass and Cucumber Framework Structure for DB
    @Test
    public void TestJDBC_API() throws SQLException {
// Connection String
        String url      = ConfigurationReader.getProperty("library2.db.url") ;
        String username = ConfigurationReader.getProperty("library2.db.username") ;
        String password = ConfigurationReader.getProperty("library2.db.password") ;

        Connection connection = DriverManager.getConnection(url,username,password); // step 1
        Statement statement = connection.createStatement(); // step 2
        ResultSet resultSet = statement.executeQuery("select name, isbn, year, author from books"); // step 3

        // we have a lot of JDBC methods to manipulate data
       // resultSet.next();  // handles going to next row of the result
       // System.out.println(resultSet.getString(2)); // necessary reading data in a column

        ResultSetMetaData rsmd = resultSet.getMetaData();

        // rsmd gives us two important info: column name and column count
        int columnCount = rsmd.getColumnCount();
        System.out.println("columnCount = " + columnCount);
        String columnName = rsmd.getColumnName(2);
        System.out.println("columnName = " + columnName);

        List<Map<String,Object>> rowList = new ArrayList<>();  // this List holds all the info of my query

        // create a loop to get all the row and column data and store inside the list
        while (resultSet.next()){
            Map<String,Object> columnValueMap = new HashMap<>();
            // inside loop to take care of columns
            for(int i = 1 ; i < columnCount ; i++){
                columnValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
            }
            rowList.add(columnValueMap);
        }

        System.out.println("rowList = " + rowList);

        resultSet.close();
        statement.close();
        connection.close();



    }

}
