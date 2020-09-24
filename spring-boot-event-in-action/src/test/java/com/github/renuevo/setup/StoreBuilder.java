package com.github.renuevo.setup;

import com.github.renuevo.repo.store.Store;

public class StoreBuilder {

    public static Store storeBuilder(){
        return Store.builder()
                .id(null)
                .name("Test Store")
                .stock(100)
                .build();
    }
}
