package com.github.renuevo.writer;

import com.google.common.collect.Lists;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

//List로 기존 JpaItemWriter를 오버라이딩해서 등록
public class JpaItemListWriter<T> extends JpaItemWriter<List<T>> {

    private JpaItemWriter<T> jpaItemWriter;

    public JpaItemListWriter(JpaItemWriter<T> jpaItemWriter){
        this.jpaItemWriter = jpaItemWriter;
    }

    @Override
    public void write(List<? extends List<T>> items) {  //처음 List는 chunkSize이고 안에 List가 실제 Item List
        List<T> list = Lists.newArrayList();

        for(List<T> subList : items){
            list.addAll(subList);       //전체 List를 1개의 List를 변경해서 return
        }

        jpaItemWriter.write(list);
    }
}