package net.stremo.graphqljpa.entities;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository  extends PagingAndSortingRepository<Product, Long> {
}
