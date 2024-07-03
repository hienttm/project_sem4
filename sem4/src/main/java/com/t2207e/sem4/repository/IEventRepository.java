package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEventRepository extends JpaRepository<Event, Integer> {
    Boolean existsByCode(String code);
    List<Event> getEventsByAreaAndStatus(Integer area, Integer status);
    Optional<Event> getEventByCode(String code);
}
