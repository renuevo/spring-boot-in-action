package com.github.renuevo.process;

import com.github.renuevo.entity.Pay;
import com.github.renuevo.entity.Pay2;
import org.springframework.batch.item.ItemProcessor;

public class JpaChainingProcessFirst implements ItemProcessor<Pay, Pay2> {
    @Override
    public Pay2 process(Pay pay) throws Exception {
        return new Pay2(pay.getId(), pay.getAmount(), pay.getTxName(), pay.getTxDateTime());
    }
}
