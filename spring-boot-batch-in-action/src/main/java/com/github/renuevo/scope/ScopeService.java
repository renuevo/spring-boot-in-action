package com.github.renuevo.scope;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ScopeService {

    private final ScopeWrapper scopeWrapper;
    private final Single single;
    private final Proto proto;
    private final ApplicationContext ctx;

    public void scopeTest() {
        log.info("Scope Service Single : " + single);
        log.info("Scope Wrapper Single : " + scopeWrapper.getSingle());

        log.info("Scope Service Proto : " + proto);
        log.info("Scope Wrapper Proto : " + scopeWrapper.getProto());

        log.info("getBean Case 1 : " + ctx.getBean("proto"));
        log.info("getBean Case 2 : " + ctx.getBean("proto"));

        log.info("ScopeWrapper getBean Proto Case 1 : " + ctx.getBean(ScopeWrapper.class).getProto());
        log.info("ScopeWrapper getBean Proto Case 2 : " +  ctx.getBean(ScopeWrapper.class).getProto());

        log.info("ScopeWrapper getBean Proxy Proto Case 1 : " + ctx.getBean(ScopeWrapper.class).getProtoProxy());
        log.info("ScopeWrapper getBean Proxy Proto Case 2 : " +  ctx.getBean(ScopeWrapper.class).getProtoProxy());

    }

}
