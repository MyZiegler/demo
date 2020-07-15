package com.wpw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.env.Environment;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})us
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
//		SpringApplication app = new SpringApplication(DemoApplication.class);
//		Environment env = app.run(args).getEnvironment();
//		System.out.println("启动成功！！");
//		System.out.println("根地址: \t\thttp://localhost:" + env.getProperty("server.port"));
//		System.out.println("登录接口: \thttp://localhost:" + env.getProperty("server.port") );
	}

}
