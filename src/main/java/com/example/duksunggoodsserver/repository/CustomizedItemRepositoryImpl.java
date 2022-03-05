package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.QBuy;
import com.example.duksunggoodsserver.model.entity.QItem;
import com.example.duksunggoodsserver.model.entity.QItemLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CustomizedItemRepositoryImpl implements CustomizedItemRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> findSuccessImminentItem() {
        return jpaQueryFactory.select(QItem.item)
                .from(QBuy.buy)
                .leftJoin(QItem.item)
                .on(QBuy.buy.item.id.eq(QItem.item.id))
                .where(QItem.item.endDate.after(LocalDate.now()))
                .groupBy(QBuy.buy.item.id)
                .having(QBuy.buy.count.sum().lt(QItem.item.minNumber))
                .orderBy((QBuy.buy.count.sum().divide(QItem.item.minNumber)).desc())
                .limit(2)
                .fetch();
    }

    @Override
    public List<Item> findManyLikeItem() {
        return jpaQueryFactory.select(QItem.item)
                .from(QItemLike.itemLike)
                .leftJoin(QItem.item)
                .on(QItemLike.itemLike.item.id.eq(QItem.item.id))
                .where(QItem.item.endDate.after(LocalDate.now()))
                .groupBy(QItemLike.itemLike.item.id)
                .orderBy(QItemLike.itemLike.item.id.count().desc(), QItemLike.itemLike.item.id.desc())
                .limit(2)
                .fetch();
    }

    @Override
    public List<Item> findNewItem() {
        return jpaQueryFactory.selectFrom(QItem.item)
                .where(QItem.item.endDate.after(LocalDate.now()))
                .orderBy(QItem.item.createdAt.desc(), QItem.item.id.desc())
                .limit(2)
                .fetch();
    }
}
