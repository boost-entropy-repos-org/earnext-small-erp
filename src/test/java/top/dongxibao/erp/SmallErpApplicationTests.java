package top.dongxibao.erp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.dongxibao.erp.security.SecurityUtils;

@SpringBootTest
class SmallErpApplicationTests {

    @Test
    void contextLoads() {
        String admin = SecurityUtils.encryptPassword("admin");
        System.out.println(admin);
    }

}
