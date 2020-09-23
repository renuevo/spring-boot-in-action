package com.github.renuevo.repo.order;

import com.github.renuevo.domain.order.OrderDataModel;
import com.github.renuevo.repo.store.StoreMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(imports = {StoreMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @IterableMapping(qualifiedByName  = "orderToOrderDataModel")
    List<OrderDataModel> collectionOrderToOrderDataModelList(Collection<OrderHistory> orderHistoryCollection);

    @Named("orderToOrderDataModel")
    @Mapping(target = "storeDataModel" ,expression = "java(StoreMapper.INSTANCE.sotreToStoreDataModel(orderHistory.getStore()))")
    OrderDataModel orderToOrderDataModel(OrderHistory orderHistory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "store", expression = "java(StoreMapper.INSTANCE.storeDataModelToStore(orderDataModel.getStoreDataModel()))")
    OrderHistory orderDataModelToOrder(OrderDataModel orderDataModel);
}
