package com.jpmorgan.restdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView listAll(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("books", bookService.findAll());
        return modelAndView;
    }

    @PostMapping("/")
    public ModelAndView processForm(@RequestParam(name="title") String title,
                                    @RequestParam(name="author") String author,
                                    @RequestParam(name="blurb") String blurb){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        // process form
        try{
            bookService.save(new Book(title, author, blurb));
        } catch (IllegalArgumentException e){
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.addObject("books", bookService.findAll());
        return modelAndView;
    }
}
