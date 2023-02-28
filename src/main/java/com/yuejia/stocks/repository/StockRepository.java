package com.yuejia.stocks.repository;


import com.yuejia.stocks.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long>{
    List<StockEntity> findAllByStock(String stock);
}
