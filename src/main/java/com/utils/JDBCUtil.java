package com.utils;

import org.apache.phoenix.jdbc.PhoenixDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class JDBCUtil {
    private static Logger logger = LoggerFactory.getLogger(JDBCUtil.class);
    private static Properties props = null;
    private static String phoenixURL = "jdbc:phoenix:hb-proxy-pub-bp1987l1fy04etj46-002.hbase.rds.aliyuncs.com,hb-proxy-pub-bp1987l1fy04etj46-001.hbase.rds.aliyuncs.com,hb-proxy-pub-bp1987l1fy04etj46-003.hbase.rds.aliyuncs.com:2181";

    static {
        props = new Properties();
        props.setProperty("phoenix.query.timeoutMs", "60000000");
        props.setProperty("phoenix.query.threadPoolSize", "128");
        props.setProperty("phoenix.query.queueSize", "5000");
        try {
            Class.forName(PhoenixDriver.class.getName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Failed loading Phoenix JDBC driver", e);
        }
    }

    /**
     * 创建实例
     *
     * @return
     */
    public static Connection createConnection() {
        Connection conn = null;
        try {
            if (props != null) {
                conn = DriverManager.getConnection(phoenixURL, props);
            } else {
                conn = DriverManager.getConnection(phoenixURL);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return conn;
    }


    /**
     * 查询并返回 List 结果集
     *
     * @param bindArgs
     * @param sql
     * @return list集合
     */
    public static List<Map<String, Object>> queryForList(String sql, Object[] bindArgs) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = createConnection();
            pstmt = conn.prepareStatement(sql);
            if (bindArgs != null) {
                /**设置sql占位符中的值**/
                for (int i = 0; i < bindArgs.length; i++) {
                    pstmt.setObject(i + 1, bindArgs[i]);
                }
            }
            //System.out.println(getExecSQL(sql, bindArgs));
            /**执行sql语句，获取结果集**/
            rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    //String columnName = metaData.getColumnName(i + 1);//取字段名
                    String columnLabel = metaData.getColumnLabel(i + 1);//取字段别名
                    resultMap.put(columnLabel, rs.getObject(columnLabel));
                }
                resultList.add(resultMap);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
           closeResource(conn,pstmt,rs);
        }
        return resultList;
    }

    /**
     * 查询并返回 Object 对象
     *
     * @param conn
     * @param sql
     * @param clazz
     * @param <T>
     * @return Object 对象
     */
    public <T> T queryForObject(Connection conn, String sql, Object[] binArgs, Class<T> clazz) {
        List<Map<String, Object>> mapList = queryForList(sql, binArgs);
        Map<String, Object> objectMap = mapList.get(0);
        T bean = BeanUtil.map2Bean(objectMap, clazz);
        return bean;
    }


    /**
     * 执行数据库插入操作
     *
     * @param valueMap  插入数据表中key为列名和value为列对应的值的Map对象
     * @param tableName 要插入的数据库的表名
     * @return 影响的行数
     * @throws SQLException SQL异常
     */
    public static int insert(Connection conn, String tableName, Map<String, Object> valueMap) throws SQLException {

        /**获取数据库插入的Map的键值对的值**/
        Set<String> keySet = valueMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        /**要插入的字段sql，其实就是用key拼起来的**/
        StringBuilder columnSql = new StringBuilder();
        /**要插入的字段值，其实就是？**/
        StringBuilder unknownMarkSql = new StringBuilder();
        Object[] bindArgs = new Object[valueMap.size()];
        int i = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            columnSql.append(i == 0 ? "" : ",");
            columnSql.append(key);

            unknownMarkSql.append(i == 0 ? "" : ",");
            unknownMarkSql.append("?");
            bindArgs[i] = valueMap.get(key);
            i++;
        }
        /**开始拼插入的sql语句**/
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        sql.append(" (");
        sql.append(columnSql);
        sql.append(" )  VALUES (");
        sql.append(unknownMarkSql);
        sql.append(" )");
        return executeUpdate(conn, sql.toString(), bindArgs);
    }

    /**
     * 执行数据库插入操作
     *
     * @return 影响的行数
     * @throws SQLException SQL异常
     */
    public static int insertBatch(String sql, List<Object[]> paramList, Integer batchSize) throws Exception {
        int affectRowCount = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = createConnection();
            ps = conn.prepareStatement(sql);
            ParameterMetaData parameterMetaData = ps.getParameterMetaData();
            int columnCount = parameterMetaData.getParameterCount();
            for (int i = 0; i < paramList.size(); i++) {
                for (int j = 0; j < columnCount; j++) {
                    ps.setObject(j + 1, paramList.get(i)[j]);
                }
                ps.addBatch();
                // Commit when batch size is reached
                if (batchSize != null && (++i % batchSize == 0)) {
                    ps.executeBatch();
                    conn.commit();
                }
            }
            int[] arr = ps.executeBatch();
            affectRowCount = arr.length;
            conn.commit();
        } catch (Exception e) {
            rollBack(conn);
            logger.error(e.getMessage(), e);
        } finally {
            closeResource(conn, ps, rs);
        }
        return affectRowCount;
    }

    /**
     * 可以执行新增，修改，删除
     *
     * @param sql      sql语句
     * @param bindArgs 绑定参数
     * @return 影响的行数
     * @throws SQLException SQL异常
     */
    public static int executeUpdate(Connection conn, String sql, Object[] bindArgs) throws SQLException {
        /**影响的行数**/
        int affectRowCount = -1;
        PreparedStatement preparedStatement = null;
        try {
            /**执行SQL预编译**/
            preparedStatement = conn.prepareStatement(sql);
            /**设置不自动提交，以便于在出现异常的时候数据库回滚**/
            conn.setAutoCommit(false);
            System.out.println(getExecSQL(sql, bindArgs));
            if (bindArgs != null) {
                /**绑定参数设置sql占位符中的值**/
                for (int i = 0; i < bindArgs.length; i++) {
                    preparedStatement.setObject(i + 1, bindArgs[i]);
                }
            }
            /**执行sql**/
            affectRowCount = preparedStatement.executeUpdate();
            conn.commit();
            String operate;
            if (sql.toUpperCase().indexOf("DELETE FROM") != -1) {
                operate = "删除";
            } else if (sql.toUpperCase().indexOf("INSERT INTO") != -1) {
                operate = "新增";
            } else {
                operate = "修改";
            }
            System.out.println("成功" + operate + "了" + affectRowCount + "行");
            System.out.println();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return affectRowCount;
    }

    private static String getExecSQL(String sql, Object[] bindArgs) {
        StringBuilder sb = new StringBuilder(sql);
        if (bindArgs != null && bindArgs.length > 0) {
            int index = 0;
            for (int i = 0; i < bindArgs.length; i++) {
                index = sb.indexOf("?", index);
                sb.replace(index, index + 1, String.valueOf(bindArgs[i]));
            }
        }
        return sb.toString();
    }

    private static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException se) {
            logger.error(se.getMessage(), se);
        }
    }

    private static void closeStatement(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se) {
            logger.error(se.getMessage(), se);
        }
    }

    private static void closeConnection(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            logger.error(se.getMessage(), se);
        }
    }

    private static void closeResource(Connection conn, Statement sm, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(sm);
        closeConnection(conn);
    }

    private static void rollBack(Connection conn) {
        try {
            if (conn != null)
                conn.rollback();
        } catch (SQLException se) {
            logger.error(se.getMessage(), se);
        }
    }


}