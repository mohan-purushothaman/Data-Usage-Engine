///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.verizon.du.beans;
//
//import javax.sql.DataSource;
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.cloud.CloudFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Profile;
//
//
///**
// *
// * @author Mohan Purushothaman
// */
//public class Configuration {
//    @org.springframework.context.annotation.Configuration
//    @Profile("cloud")
//    static class CloudConfiguration {
//
//        
//        @Bean
//        public DataSource dataSource() {
//            //return new CloudFactory().getCloud().getSingletonServiceConnector(DataSource.class, null);
//        }
//    }
//
//  @org.springframework.context.annotation.Configuration
//    @Profile("default")
//    static class LocalConfiguration {
//
//     @Bean
//           public DataSource dataSource() {
//              
//         }
//     }
//}
