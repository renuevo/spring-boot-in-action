package com.github.renuevo.repo.store;

import com.github.renuevo.domain.order.OrderDataModel;
import com.github.renuevo.domain.store.StoreDataModel;
import com.github.renuevo.domain.store.StoreRepository;
import com.github.renuevo.repo.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreRdbRepository implements StoreRepository {

    private final JpaStoreRepository jpaStoreRepository;

    @Override
    public StoreDataModel saveStore(StoreDataModel storeDataModel) {
        return StoreMapper.INSTANCE
                .sotreToStoreDataModel(
                        jpaStoreRepository.save(StoreMapper.INSTANCE.storeDataModelToStore(storeDataModel))
                );
    }

    @Override
    public StoreDataModel orderStore(OrderDataModel orderDataModel) {
        return jpaStoreRepository.findById(orderDataModel.getStoreDataModel().getId())
                .filter(store -> store.orderCount(OrderMapper.INSTANCE.orderDataModelToOrder(orderDataModel)))
                .map(jpaStoreRepository::save)
                .map(StoreMapper.INSTANCE::sotreToStoreDataModel)
                .orElseThrow(() -> new RuntimeException("스토어 재고가 부족 합니다."));
    }
}
