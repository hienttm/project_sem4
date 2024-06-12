package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
}
