package com.github.renuevo.scope;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScopeWrapper {

    @Getter
    Single single;

    @Getter
    Proto proto;

    @Getter
    ProtoProxy protoProxy;

}
