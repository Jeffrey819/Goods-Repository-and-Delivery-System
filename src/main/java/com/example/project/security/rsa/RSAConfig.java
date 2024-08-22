package com.example.project.security.rsa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RSAConfig {
    @Bean(name = "serverRsaManager")
    public RSAManager serverRsaManager(){
        RSAManager rsaManager = new RSAManager();
        rsaManager.init();
        System.out.println("Complete init for serverRsaManager");
        return rsaManager;
    }

    @Bean(name = "clientRsaManager")
    public RSAManager clientRsaManager(){
        RSAManager rsaManager = new RSAManager();
        rsaManager.init();
        System.out.println("Complete init for clientRsaManager");
        return rsaManager;
    }

}
