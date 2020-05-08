package cn.ekgc.itrip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <b>爱旅行-主业务生产者启动类</b>
 * @author Wang
 * @version 2.0.0
 * @since 2020-05-03
 */
@MapperScan("cn.ekgc.itrip.dao")
@EnableEurekaClient
@SpringBootApplication
public class BizProviderStarter {
	public static void main(String[] args) {
		SpringApplication.run(BizProviderStarter.class, args);
	}
}
