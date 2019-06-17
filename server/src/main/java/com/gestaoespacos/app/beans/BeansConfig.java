package com.gestaoespacos.app.beans;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeansConfig {

    @Bean
    @Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
    public VisitanteBean visitante(){
        return new VisitanteBean();
    }

    @Bean
    @Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
    public UtilizadorBean utilizador(){
        return new UtilizadorBean();
    }

    @Bean
    @Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ResponsavelBean responsavel(){
        return new ResponsavelBean();
    }
}
