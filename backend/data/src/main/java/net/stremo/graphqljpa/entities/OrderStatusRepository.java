package net.stremo.graphqljpa.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderStatusRepository extends PagingAndSortingRepository<OrderStatus, Long> {
}
