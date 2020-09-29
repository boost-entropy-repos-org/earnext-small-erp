package top.dongxibao.erp;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author Dongxibao
 * @date 2020-06-07
 */
@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SmallErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallErpApplication.class, args);
    }

}
