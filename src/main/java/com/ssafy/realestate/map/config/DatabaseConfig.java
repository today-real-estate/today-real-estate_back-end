package com.ssafy.realestate.map.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
@Configuration
@MapperScan(
		basePackages = "com.ssafy.realestate.map.model.mapper"
)
public class DatabaseConfig {}