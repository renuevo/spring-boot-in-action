package com.github.renuevo.scope;

import com.github.renuevo.proxy.ProtoInterface;
import com.github.renuevo.proxy.ProtoInterfaceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScopeService {

    private final ScopeWrapper scopeWrapper;
    private final Single single;
    private final Proto proto;
    private final ProtoInterface protoInterface;
    private final ProtoInterface protoInterfaceProxy;
    private final ProtoInterface protoInterfaceProxySafe;

    private final ApplicationContext ctx;

    public ScopeService(ScopeWrapper scopeWrapper,
                        Single single, Proto proto,
                        @Qualifier("ProtoInterface") ProtoInterface protoInterface,
                        @Qualifier("ProtoInterfaceProxy") ProtoInterface protoInterfaceProxy,
                        @Qualifier("ProtoInterfaceProxySafe") ProtoInterface protoInterfaceProxySafe,
                        ApplicationContext ctx) {
        this.scopeWrapper = scopeWrapper;
        this.single = single;
        this.proto = proto;
        this.protoInterface = protoInterface;
        this.protoInterfaceProxy = protoInterfaceProxy;
        this.protoInterfaceProxySafe = protoInterfaceProxySafe;
        this.ctx = ctx;
    }

    public void scopeTest() {

        System.out.println();
        log.info("[============== Singleton Bean ==============]");
        log.info("Scope Service Single : " + single);
        log.info("Scope Wrapper Single : " + scopeWrapper.getSingle());
        System.out.println();

        log.info("[============== Prototype Bean ==============]");
        log.info("Scope Service Proto : " + proto);
        log.info("Scope Wrapper Proto : " + scopeWrapper.getProto());
        System.out.println();

        log.info("[============== Prototype getBean ==============]");
        log.info("getBean Case 1 : " + ctx.getBean("proto"));
        log.info("getBean Case 2 : " + ctx.getBean("proto"));
        System.out.println();

        log.info("[============== Singleton getBean And getPrototype ==============]");
        log.info("ScopeWrapper getBean Proto Case 1 : " + ctx.getBean(ScopeWrapper.class).getProto());
        log.info("ScopeWrapper getBean Proto Case 2 : " + ctx.getBean(ScopeWrapper.class).getProto());
        System.out.println();

        log.info("[============== Singleton getBean And getProxyPrototype ==============]");
        log.info("ScopeWrapper getBean Proxy Proto Case 1 : " + ctx.getBean(ScopeWrapper.class).getProtoProxy());
        log.info("ScopeWrapper getBean Proxy Proto Case 2 : " + ctx.getBean(ScopeWrapper.class).getProtoProxy());
        System.out.println();

        log.info("[============== ProtoInterfaceImpl Prototype Bean Return ProtoInterface ==============]");
        log.info("Bean Is ProtoInterface Instranceof : " + (protoInterface instanceof ProtoInterface));
        log.info("Bean Is ProtoInterfaceImpl Instranceof : " + (protoInterface instanceof ProtoInterfaceImpl));
        System.out.println();

        log.info("[============== ProtoInterfaceImpl Prototype Proxy Bean Return ProtoInterface ==============]");
        log.info("Proxy Bean Is ProtoInterface Instranceof : " + (protoInterfaceProxy instanceof ProtoInterface));
        log.info("Proxy Bean Is ProtoInterfaceImpl Instranceof : " + (protoInterfaceProxy instanceof ProtoInterfaceImpl));
        System.out.println();

        log.info("[============== ProtoInterfaceImpl Prototype ProxySafe Bean Return ProtoInterface ==============]");
        log.info("ProxySafe Bean Is ProtoInterface Instranceof : " + (protoInterfaceProxySafe instanceof ProtoInterface));
        log.info("ProxySafe Bean Is ProtoInterfaceImpl Instranceof : " + (protoInterfaceProxySafe instanceof ProtoInterfaceImpl));
        log.info("ProxySafe Bean : " + ((ProtoInterfaceImpl)protoInterfaceProxySafe).getName());
        System.out.println();
    }

}
