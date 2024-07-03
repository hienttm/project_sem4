package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Event;
import com.t2207e.sem4.repository.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService{
    private final IEventRepository eventRepository;

    @Autowired
    public EventService(IEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    @Override
    public void add(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Boolean existsByCode(String code) {
        return eventRepository.existsByCode(code);
    }

    @Override
    public List<Event> getEventsByAreaAndStatus(Integer area, Integer status) {
        return eventRepository.getEventsByAreaAndStatus(area, status);
    }

    @Override
    public Optional<Event> getEventByCode(String code) {
        return eventRepository.getEventByCode(code);
    }
}
