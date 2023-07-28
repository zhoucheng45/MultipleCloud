package com.benny.multiple.cloud.after;//package com.benny.multiple.cloud;
//
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Properties;
//
////@Configuration
//public class SSHDataSourceConfig {
//
//    @Value("${ssh.url}")
//    private String sshUrl;
//
//    @Value("${ssh.port}")
//    private Integer sshPort;
//
//    @Value("${ssh.user}")
//    private String sshUser;
//
//    @Value("${ssh.password}")
//    private String sshPassword;
//
//    @Value("${mysql.host}")
//    private String mysqlHost;
//
//    @Value("${mysql.port}")
//    private Integer mysqlPort;
//
//    @Value("${local.port}")
//    private Integer localPort;
//
//
//    @Bean
//    public Session sshSession() throws JSchException {
//        JSch jsch = new JSch();
//        Session session = jsch.getSession(sshUser, sshUrl, sshPort);
//        session.setPassword(sshPassword);
//
//        Properties config = new Properties();
//        config.put("StrictHostKeyChecking", "no");
//        session.setConfig(config);
//        session.connect();
//        session.setPortForwardingL(localPort, mysqlHost, mysqlPort);
//        return session;
//    }
//
//}