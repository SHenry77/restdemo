package com.jpmorgan.restdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @Autowired
    private final BookService bookService;

    HomeController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ModelAndView listAll(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        modelAndView.addObject("books", bookService.findAll());
        return modelAndView;
    }
}
