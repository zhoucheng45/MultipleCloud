//package com.benny.multiple.cloud;
//
//
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;
//
//public class DatabaseConnector {
//    private static final int LOCAL_PORT = 8888; // 本地端口
//    private static final String MYSQL_REMOTE_SERVER = "152.67.210.191"; // 远程MySQL服务器
//    private static final int REMOTE_PORT = 33060; // 远程MySQL服务器端口
//    private static final String SSH_REMOTE_SERVER = "43.154.107.17"; // 远程SSH服务器
//    private static final String SSH_USER = "root"; // SSH登录用户名
//    private static final String SSH_PASSWORD = "Z00c9892>/"; // SSH登录密码
//    private static final String DB_USER = "root"; // 数据库用户名
//    private static final String DB_PASSWORD = "123456"; // 数据库密码
//    private static final String DATABASE = "test"; // 数据库名
//    JSch jsch;
//    Session session;
//    public Session connect() {
//        try {
//            jsch = new JSch();
//            Session session = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, 22);
//            session.setPassword(SSH_PASSWORD);
//
//            // Avoid asking for key confirmation
//            java.util.Properties config = new java.util.Properties();
//            config.put("StrictHostKeyChecking", "no");
//            session.setConfig(config);
//
//            session.connect();
//            session.setPortForwardingL(LOCAL_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);
//
//            // Connect to the database
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:" + LOCAL_PORT + "/" + DATABASE, DB_USER, DB_PASSWORD);
//
//            // Do something with the connection here
//
////            con.close();
//            return session;
//
//        } catch (Exception e) {
//           throw new RuntimeException(e);
//        }
//    }
//}