package com.github.renuevo.process;


import com.github.renuevo.entity.Pay2;
import com.github.renuevo.entity.Tax;
import org.springframework.batch.item.ItemProcessor;

public class JpaChainingProcessSecond implements ItemProcessor<Pay2, Tax> {
    @Override
    public Tax process(Pay2 pay2) throws Exception {
        return new Tax(pay2.getId(), (long) (pay2.getAmount() * 0.1), "location");
    }
}
