package com.github.renuevo.querydsl.options;

import com.github.renuevo.querydsl.expression.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

@Slf4j
@Getter
public class QuerydslNoOffsetNumberOptions<T, N extends Number & Comparable<?>> extends QuerydslNoOffsetOptions <T> {

    private N currentId;
    private N lastId;

    private final NumberPath<N> field;

    public QuerydslNoOffsetNumberOptions(@Nonnull NumberPath<N> field,
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
        if(currentId == null) {
            return query;
        }

        return query
                .where(whereExpression(page))
                .orderBy(orderExpression());
    }

    private BooleanExpression whereExpression(int page) {
        return expression.where(field, page, currentId);
    }

    private OrderSpecifier<N> orderExpression() {
        return expression.order(field);
    }

    @Override
    public void resetCurrentId(T item) {
        //noinspection unchecked
        currentId = (N) getFiledValue(item);

        if (log.isDebugEnabled()) {
            log.debug("Current Select Key= " + currentId);
        }
    }

}
