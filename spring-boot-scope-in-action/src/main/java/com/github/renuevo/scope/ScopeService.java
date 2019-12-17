package com.github.renuevo.scope;

import com.github.renuevo.proxy.ProtoInterface;
import com.github.renuevo.proxy.ProtoInterfaceImpl;
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

    private final ApplicationContext ctx;

    public ScopeService(ScopeWrapper scopeWrapper, Single single, Proto proto, @Qualifier("ProtoInterface") ProtoInterface protoInterface, @Qualifier("ProtoInterfaceProxy") ProtoInterface protoInterfaceProxy, ApplicationContext ctx) {
        this.scopeWrapper = scopeWrapper;
        this.single = single;
        this.proto = proto;
        this.protoInterface = protoInterface;
        this.protoInterfaceProxy = protoInterfaceProxy;
        this.ctx = ctx;
    }

    public void scopeTest() {
        log.info("Scope Service Single : " + single);
        log.info("Scope Wrapper Single : " + scopeWrapper.getSingle());

        log.info("Scope Service Proto : " + proto);
        log.info("Scope Wrapper Proto : " + scopeWrapper.getProto());

        log.info("getBean Case 1 : " + ctx.getBean("proto"));
        log.info("getBean Case 2 : " + ctx.getBean("proto"));

        log.info("ScopeWrapper getBean Proto Case 1 : " + ctx.getBean(ScopeWrapper.class).getProto());
        log.info("ScopeWrapper getBean Proto Case 2 : " + ctx.getBean(ScopeWrapper.class).getProto());

        log.info("ScopeWrapper getBean Proxy Proto Case 1 : " + ctx.getBean(ScopeWrapper.class).getProtoProxy());
        log.info("ScopeWrapper getBean Proxy Proto Case 2 : " + ctx.getBean(ScopeWrapper.class).getProtoProxy());

        log.info("Bean Is ProtoInterface Instranceof : " + (protoInterface instanceof ProtoInterface));
        log.info("Bean Is ProtoInterfaceImpl Instranceof : " + (protoInterface instanceof ProtoInterfaceImpl));

        log.info("Proxy Bean Is ProtoInterface Instranceof : " + (protoInterfaceProxy instanceof ProtoInterface));
        log.info("Proxy Bean Is ProtoInterfaceImpl Instranceof : " + (protoInterfaceProxy instanceof ProtoInterfaceImpl));
        //log.info("Proxy Bean ProtoInterfaceImpl getName" + ((ProtoInterfaceImpl) protoInterfaceProxy).getName());
    }

}
