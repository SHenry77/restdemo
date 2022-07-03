package com.jpmorgan.restdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository){
        String cogman ="Genevieve Cogman";
        Book[] books = new Book[]{
                new Book("The Invisible Library", cogman, "Irene is a professional spy for the mysterious Library, which harvests fiction from different realities."),
                new Book("The Masked City", cogman, "Irene is working undercover in an alternative Victorian London."),
                new Book( "Trollslayer", "William King", "Gotrek and Felix's first adventures."),
                new Book("Altered Carbon", "Richard Morgan", "Takeshi Kovacs investigates a 'suicide' case under duress.")};
        return args -> {
            for(Book book: books){
                log.info("Preloading "+ bookRepository.save(book));
            }
        };
    }

}
