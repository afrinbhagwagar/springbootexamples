package com.payconiq.stocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payconiq.stocks.entity.Stock;

/**
 * Repository class.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

}
