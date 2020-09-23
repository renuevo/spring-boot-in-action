package com.github.renuevo.repo.store;

import com.github.renuevo.domain.store.StoreDataModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    StoreDataModel sotreToStoreDataModel(Store store);

    Store storeDataModelToStore(StoreDataModel storeDataModel);
}
