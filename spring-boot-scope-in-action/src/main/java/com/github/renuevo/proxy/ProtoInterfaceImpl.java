package com.github.renuevo.proxy;

import lombok.Getter;

public class ProtoInterfaceImpl implements ProtoInterface {
    @Getter
    String name;

    public ProtoInterfaceImpl() {
    }

    public ProtoInterfaceImpl(String name) {
        this.name = name;
    }
}
