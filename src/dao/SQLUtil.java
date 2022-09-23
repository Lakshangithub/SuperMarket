package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {

    private static PreparedStatement execute(String sql,Object...params) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement stm = connection.prepareStatement(sql);

        for (int i= 0;i< params.length;i++){
            stm.setObject(i+1,params[i]);
        }
        return stm;
    }

    public static boolean executeUpdate(String sql,Object...params) throws SQLException, ClassNotFoundException {
        return execute(sql, params).executeUpdate()>0;
    }

    public static ResultSet executeQuery(String sql,Object...params) throws SQLException, ClassNotFoundException {
        return execute(sql, params).executeQuery();
    }
    
}
