package com.felipelucas.store.repository;

import com.felipelucas.store.domain.Store;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT sum(s.revenue) FROM Store s")
    BigDecimal sumRevenue();

}
