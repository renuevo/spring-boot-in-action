package com.github.renuevo.proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class ProtoInterFaceBean {

    @Bean(name = "ProtoInterfaceProxy")
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ProtoInterface getProtoInterfaceProxyBean() {
        return new ProtoInterfaceImpl();
    }

    @Bean(name = "ProtoInterface")
    @Scope(value = "prototype")
    public ProtoInterface getProtoInterfaceBean() {
        return new ProtoInterfaceImpl();
    }

    @Bean(name = "ProtoInterfaceProxySafe")
    @Scope(value = "prototype")
    public ProtoInterfaceImpl getProtoInterfaceProxySafeBean() {
        return new ProtoInterfaceImpl("Safe Bean");
    }
}
