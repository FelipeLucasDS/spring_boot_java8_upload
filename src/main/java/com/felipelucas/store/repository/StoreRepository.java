package com.felipelucas.store.repository;

import com.felipelucas.store.domain.Store;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT sum(s.revenue) FROM Store s")
    BigDecimal sumRevenue();

    @Query("SELECT s " +
            "FROM Store s " +
            "ORDER BY ((:latitude - latitude)*(:latitude - latitude)) " +
            "+ ((:longitude - longitude)*(:longitude - longitude)) ASC ")
    List<Store> getNearbyStore(@Param("latitude") Double latitude, @Param("longitude") Double longitude);

}
