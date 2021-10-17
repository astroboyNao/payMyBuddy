package com.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.repository.entity.Connection;
import com.paymybuddy.repository.entity.FinancialTransaction;

@Repository
public interface FinancialTransactionRepository extends CrudRepository<FinancialTransaction, Long> {

}
