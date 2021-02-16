package top.dongxibao.erp.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * knife4j的接口配置
 *
 * @author Dongxibao
 * @date 2020-11-27
 */
@EnableKnife4j
@Configuration
public class SwaggerConfig {

    /** 是否开启swagger */
    @Value("${knife4j.enable}")
    private boolean enable;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enable)
                //分组名称
                .groupName("small-erp")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("top.dongxibao.erp.controller"))
                // 扫描所有有注解的api
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("small-erp")
                .description("small-erp RESTful APIs")
                .termsOfServiceUrl("http:localhost/small-erp/#/login")
                .contact(new Contact("Dongxibao", "", "dbao1128@163.com"))
                .version("1.0")
                .build();
    }
}
