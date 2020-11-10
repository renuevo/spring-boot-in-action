package com.github.renuevo.querydsl.options;

import com.github.renuevo.querydsl.expression.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

@Getter
@Slf4j
public class QuerydslNoOffsetStringOptions<T> extends QuerydslNoOffsetOptions<T> {

    private String currentId;
    private String lastId;

    private final StringPath field;

    public QuerydslNoOffsetStringOptions(@Nonnull StringPath field,
                                         @Nonnull Expression expression) {
        super(field, expression);
        this.field = field;
    }

    @Override
    public void initKeys(JPAQuery<T> query, int page) {
        if(page == 0) {
            initFirstId(query);
            initLastId(query);

            if (log.isDebugEnabled()) {
                log.debug("First Key= "+currentId+", Last Key= "+ lastId);
            }
        }
    }

    @Override
    protected void initFirstId(JPAQuery<T> query) {
        currentId = query.clone()
                .select(field)
                .orderBy(expression.isAsc()? field.asc() : field.desc())
                .fetchFirst();
    }

    @Override
    protected void initLastId(JPAQuery<T> query) {
        lastId = query.clone()
                .select(field)
                .orderBy(expression.isAsc()? field.desc() : field.asc())
                .fetchFirst();
    }

    @Override
    public JPAQuery<T> createQuery(JPAQuery<T> query, int page) {
        if (currentId == null) {
            return query;
        }

        return query
                .where(whereExpression(page))
                .orderBy(orderExpression());
    }

    private BooleanExpression whereExpression(int page) {
        return expression.where(field, page, currentId);
    }

    private OrderSpecifier<String> orderExpression() {
        return expression.order(field);
    }

    @Override
    public void resetCurrentId(T item) {
        currentId = (String) getFiledValue(item);
        if (log.isDebugEnabled()) {
            log.debug("Current Select Key= " + currentId);
        }
    }
}
