package com.felipelucas.customer.repository;

import com.felipelucas.customer.domain.Customer;
import com.felipelucas.customer.service.dto.CustomerStoreDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT count(0) FROM Customer c " +
            "WHERE c.store.id = :storeID")
    BigDecimal countCustomersPerStore(Long storeID);

    @Query("select new com.felipelucas.customer.service.dto.CustomerStoreDTO(c.store.id, count(c.id)) " +
            "FROM Customer c " +
            "group by c.store.id")
    List<CustomerStoreDTO> countCustomersPerStore();

    @Query("SELECT count(c) FROM Customer c")
    BigDecimal countCustomers();

}
