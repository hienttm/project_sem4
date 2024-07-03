package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.CategorySale;
import com.t2207e.sem4.entity.Event;
import com.t2207e.sem4.service.CategorySaleService;
import com.t2207e.sem4.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin/event")
public class EventAdminController {
    private final EventService eventService;
    private final CategorySaleService categorySaleService;

    public EventAdminController(EventService eventService, CategorySaleService categorySaleService) {
        this.eventService = eventService;
        this.categorySaleService = categorySaleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("list")
    public String list(Model model){
        List<Event> events = eventService.getAllEvent();
        model.addAttribute("events", events);
        return "/admin/events/index";
    }

    @GetMapping("add")
    public String add(Model model){
        Event event = new Event();
        model.addAttribute("event", event);

        List<CategorySale> categorySales = categorySaleService.getAllCategorySale();
        model.addAttribute("categorySales", categorySales);
        return "admin/events/add";
    }

    @PostMapping("add")
    public String save(@Valid @ModelAttribute Event event, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<CategorySale> categorySales = categorySaleService.getAllCategorySale();
            model.addAttribute("categorySales", categorySales);
            return "admin/events/add";
        }
        if(eventService.existsByCode(event.getCode())){
            String exception = "Code has been existed";
            model.addAttribute("exception", exception);
            List<CategorySale> categorySales = categorySaleService.getAllCategorySale();
            model.addAttribute("categorySales", categorySales);
            return "admin/events/add";
        }
        eventService.add(event);
        return "redirect:/admin/event/list";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Optional<Event> eventOptional = eventService.getEventById(id);
        if(eventOptional.isPresent()){
            Event event = eventOptional.get();
            model.addAttribute("event", event);
            List<CategorySale> categorySales = categorySaleService.getAllCategorySale();
            model.addAttribute("categorySales", categorySales);
            return "admin/events/edit";
        }
        return "redirect:/admin/event/list";
    }

    @PostMapping("edit")
    public String edit(@Valid @ModelAttribute Event event, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<CategorySale> categorySales = categorySaleService.getAllCategorySale();
            model.addAttribute("categorySales", categorySales);
            return "admin/events/edit";
        }
        if(eventService.existsByCode(event.getCode()) && !Objects.equals(event.getCode(), eventService.getEventById(event.getEventId()).get().getCode())){
            String exception = "Code has been existed";
            model.addAttribute("exception", exception);
            List<CategorySale> categorySales = categorySaleService.getAllCategorySale();
            model.addAttribute("categorySales", categorySales);
            return "admin/events/add";
        }
        eventService.add(event);
        return "redirect:/admin/event/list";
    }

    @GetMapping("hidden/{id}")
    public String hidden(@PathVariable int id){
        Optional<Event> eventOptional = eventService.getEventById(id);
        if(eventOptional.isPresent()){
            Event event = eventOptional.get();
            if(event.getStatus() == 1){
                event.setStatus(2);
            }
            else {
                event.setStatus(1);
            }
            eventService.add(event);
        }
        return "redirect:/admin/event/list";
    }
}
