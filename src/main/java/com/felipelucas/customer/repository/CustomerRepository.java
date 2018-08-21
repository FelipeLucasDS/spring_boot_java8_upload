package com.felipelucas.customer.repository;

import com.felipelucas.customer.domain.Customer;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT count(0) FROM Customer c " +
            "WHERE c.store.id = :storeID")
    BigDecimal countCustomersPerStore(Long storeID);

    @Query("SELECT count(c) FROM Customer c")
    BigDecimal countCustomers();

}
