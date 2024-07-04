package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPolicyRepository extends JpaRepository<Policy, Integer> {
}
