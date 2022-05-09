package com.example.emos.wx.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2

//public class SwaggerConfig {
//
//    @Bean
//    public Docket createRestApi() {
//        Docket docket = new Docket(DocumentationType.SWAGGER_2);
//
//        // ApiInfoBuilder 用于在Swagger界面上添加各种信息
//        ApiInfoBuilder builder = new ApiInfoBuilder();
//        builder.title("Pumpkin的系统");
//        ApiInfo apiInfo = builder.build();
//        docket.apiInfo(apiInfo);
//
//        // ApiSelectorBuilder 用来设置哪些类中的方法会生成到REST API中
//        ApiSelectorBuilder selectorBuilder = docket.select();
//        selectorBuilder.paths(PathSelectors.any()); //所有包下的类
//        //使用@ApiOperation的方法会被提取到REST API中
//        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
//        docket = selectorBuilder.build();
//        /*
//         * 下面的语句是开启对JWT的支持，当用户用Swagger调用受JWT认证保护的方法，
//         * 必须要先提交参数（例如令牌）
//         */
//        //存储用户必须提交的参数
//        List<ApiKey> apikey = new ArrayList();
//        //规定用户需要输入什么参数
//        apikey.add(new ApiKey("token", "token", "header"));
//        docket.securitySchemes(apikey);
//
//        //如果用户JWT认证通过，则在Swagger中全局有效
//        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] scopeArray = {scope};
//        //存储令牌和作用域
//        SecurityReference reference = new SecurityReference("token", scopeArray);
//        List refList = new ArrayList();
//        refList.add(reference);
//        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();
//        List cxtList = new ArrayList();
//        cxtList.add(context);
//        docket.securityContexts(cxtList);
//
//        return docket;
//    }
//}


public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        //创建对象
        // !!! 需要添加docket的apiInfo（）添加用户参数 - API接口
        // !!! 添加docket的securitySchemes（）把API接口添加到安全计划表中，限制JWT API接口的作用域
        // !!! 添加docket的securityContexts（）把API接口添加到安全内容中，限制令牌内容的作用域
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        // 配置基本信息
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("Pickles-EMOS在线办公系统");
        // 将builder的信息info封装在docket中
        ApiInfo info = builder.build();
        docket.apiInfo(info);

        // 选择哪些类的哪些方法需要测试
        ApiSelectorBuilder selectorBuilder = docket.select();
        // 所有路径下的包都可以测试 -- 但只有注解是ApiOperation的才需要
        selectorBuilder.paths(PathSelectors.any());
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));
        // 更新docket对象 - 更新了要测试什么内容
        docket = selectorBuilder.build();

        // 开启JWT功能
        // 定义令牌字符串 -- 将令牌字符串添加到docket的安全计划表中
        ApiKey apiKey = new ApiKey("token","token","header");
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(apiKey);
        docket.securitySchemes(apiKeyList);

        // 设定令牌的作用域 -- 用数组封装作用域，再封装到reference中
        AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] scopes = {scope};
        SecurityReference reference = new SecurityReference("token",scopes);

        // 把参考字典添加到数组中
        List refList = new ArrayList();
        refList.add(reference);

        // 把参考字典封装到安全内容里面，才有保障
        // 无法new，调用类中的静态工厂方法
        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();
        List cxtList = new ArrayList();
        cxtList.add(context);

        // 更新docket对象 - 更新了有安全保障的参考字典
        docket.securityContexts(cxtList);

        return docket;
    }
}
