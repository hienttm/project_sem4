package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface IPaymentMethodService {
    List<PaymentMethod> getAllPaymentMethod();
    Optional<PaymentMethod> getPaymentMethodById(Integer id);
}
