package cn.itxinbing.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBHelper {
    private static String URL;
    private static String DRIVER;
    private static String USER;
    private static String PASSWORD;
    private static Connection connection = null;
    private static PreparedStatement preStatement = null;
    private static ResultSet rs = null;
    private Statement s = null;

    /**
     * 加载驱动
     */
    static {
        try {
            InputStream is = new FileInputStream("data.properties");
            Properties p = new Properties();
            p.load(is);
            DRIVER = p.getProperty("driver");
            URL = p.getProperty("url");
            USER = p.getProperty("user");
            PASSWORD = p.getProperty("password");
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 创建连接
     *
     * @throws SQLException
     */
    private void getConnection() throws SQLException {
        // 先要判断是否存在连接，要是存在的话，就不重复创建
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    /**
     * 连接preparedstatement对象 实现sql方法
     *
     * @throws SQLException
     */
    public int executeUpdate(String sql, Object... objs) throws SQLException {
        getConnection();
        preStatement = connection.prepareStatement(sql);
        setObject(objs);
        int i = preStatement.executeUpdate();
        return i;
    }

    /**
     * @throws SQLException
     */
    public ResultSet executeQuery(String sql, Object... objs) throws SQLException {
        getConnection();
        preStatement = connection.prepareStatement(sql);
        setObject(objs);
        rs = preStatement.executeQuery();
        return rs;
    }

    /**
     * 查询全部信息
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        getConnection();
        s = connection.createStatement();
        rs = s.executeQuery(sql);
        return rs;
    }

    /**
     * 根据条件查询
     *
     * @throws SQLException
     */
    public int getGeneratedKey(String sql, Object... objs) throws SQLException {
        getConnection();
        s = connection.createStatement();
        s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        rs = s.getGeneratedKeys();
        // 首先，自增张主键是int类型的id，所以key值是int类型的
        int key = 0;
        while (rs.next()) {
            // getInt方法是获取数据库中数据，括号里面的参数是数据库中存储主键的列，例如：id存在第 1 列
            key = rs.getInt(1);
        }
        s.close();
        return key;
    }

    /**
     * setObject方法，防止sql注入
     *
     * @throws SQLException
     */
    public void setObject(Object... objs) throws SQLException {
        if (objs != null && objs.length > 0) {
            for (int i = 0; i < objs.length; i++) {
                preStatement.setObject((i + 1), objs[i]);
            }
        }
    }

    /**
     * 关闭资源
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (rs != null && !rs.isClosed()) {
            rs.close();
            rs = null;
        }
        if (preStatement != null && !preStatement.isClosed()) {
            preStatement.close();
            preStatement = null;
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }
}
