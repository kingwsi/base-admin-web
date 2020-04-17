package com.kingwsi.bs.common.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final Environment environment;

    public ApplicationRunnerImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\t--------");
        System.out.println("\t启动成功\n\t文档：http://localhost:" + environment.getProperty("local.server.port") + "/swagger-ui.html");
        System.out.println("\t配置："+environment.getProperty("spring.profiles.active"));
    }
}
