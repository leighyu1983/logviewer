package com.ley;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://www.cnblogs.com/kevinZhu/p/9931317.html
 *
 * java 正则遍历文件行
 *
 *   try（InputStream is = Files.newInputStream（path，StandardOpenOption.READ））{
 *       InputStreamReader reader = new InputStreamReader（is，fileEncoding）;
 *       BufferedReader lineReader = new BufferedReader（reader）;
 *
 *       while（（line = lineReader.readLine（））！= null）{
 *         //行内容内容在变量行中。
 *       }
 *    }
 *
 *
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
