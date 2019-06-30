package com.zm.zhidan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//public class ZhidanApplication extends SpringBootServletInitializer {
public class ZhidanApplication {

    /**
     *  项目启动前加载文件用
     * @param application
     * @return
     */
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {


        return application.sources(ZhidanApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(ZhidanApplication.class, args);
    }

}
