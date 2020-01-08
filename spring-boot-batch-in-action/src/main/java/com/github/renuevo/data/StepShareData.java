package com.github.renuevo.data;


import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <pre>
 * @className : StepShareData
 * @author : Deokhwa.Kim
 * @since : 2020-01-08
 * </pre>
 */
@Component
public class StepShareData {

    private final Map<Long, Object> shareDataMap = Maps.newConcurrentMap();

    public void putData(Long key, Object value) {
        this.shareDataMap.put(key, value);
    }

    public Object get(Long key) {
        return this.shareDataMap.getOrDefault(key, null);
    }

    public void clear() {
        this.shareDataMap.clear();
    }

}
