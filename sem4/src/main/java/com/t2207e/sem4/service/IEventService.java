package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<Event> getAllEvent();
    Optional<Event> getEventById(Integer id);
    void add(Event event);
    void deleteById(Integer id);
    Boolean existsByCode(String code);
    List<Event> getEventsByAreaAndStatus(Integer area, Integer status);
    Optional<Event> getEventByCode(String code);

}
