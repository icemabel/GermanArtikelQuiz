package com.example.GermanArticleQuestionary.controller;

import com.example.GermanArticleQuestionary.model.Endings;
import com.example.GermanArticleQuestionary.service.EndingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class QuizController {

    private final EndingsService endingsService;
    private Random random = new Random();


    @GetMapping("/showExampleForm")
    public String showForm(Model theModel) {

        // create a student object
        Endings createExampleWord = new Endings();

        // add student object to the model
        theModel.addAttribute("example", createExampleWord);

        return "example-word-form";
    }

    @PostMapping("/processExampleForm")
    public String processForm(@ModelAttribute("example") Endings createExampleWord) {

        // log the input data
        System.out.println("the Example word: " + createExampleWord.getExample());

        return "example-confirmation";
    }

    @GetMapping
    public String showQuiz(Model model) {
        Endings wordEnding = endingsService.getRandomEndings();
        model.addAttribute("wordEnding", wordEnding);
        return "quiz";
    }

    @PostMapping("/check-article")
    public String checkArticle(@RequestParam String artikel,
                               @RequestParam String endings,
                               Model model) {
        Endings wordEnding = endingsService.findByEnding(endings);

        if (wordEnding != null && wordEnding.getArtikel().equalsIgnoreCase(artikel)) {
            model.addAttribute("message", "Correct!");
            model.addAttribute("example", "Example: " + wordEnding.getExample());
        } else {
            model.addAttribute("message", "Not true, try again.");
        }

        model.addAttribute("wordEnding", endingsService.getRandomEndings()); // Load new word
        return "quiz";
    }
}
