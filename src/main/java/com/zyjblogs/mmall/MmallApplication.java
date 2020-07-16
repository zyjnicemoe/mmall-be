package com.zyjblogs.mmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

/*
 这里是由于 SpringBoot 加载 GsonAutoConfiguration @Configuration 类时，试图调用 GsonBuilder 的 setLenient（）方法。
但是 pom.xml 文件中直接或间接依赖的 gson jar 包的版本，并没有setLenient()方法,所以导致报错！
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
//@SpringBootApplication
public class MmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MmallApplication.class, args);
    }

}
