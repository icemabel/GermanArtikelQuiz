package com.example.GermanArticleQuestionary.controller;

import com.example.GermanArticleQuestionary.model.Endings;
import com.example.GermanArticleQuestionary.service.EndingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
@SessionAttributes("userAnswers")
public class QuizController {

    private final EndingsService endingsService;
    private Random random = new Random();

    @ModelAttribute("userAnswers") // ✅ Initialize the list if it doesn't exist
    public List<String> userAnswers() {
        return new ArrayList<>();
    }

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
    public String checkArticle(@ModelAttribute("wordEnding") Endings userInput,
                               @ModelAttribute("userAnswers") List<String> userAnswers,
                               Model model) {

        Endings wordEnding = endingsService.findByEnding(userInput.getEndings());
        String resultMessage;

        String correctArticle = wordEnding.getArtikel().toLowerCase();
        String userArticle = userInput.getArtikel().toLowerCase();

        //log.info("wordEnding: " + wordEnding + ", userInput: " + userInput + ", userArticle: " + userArticle);

        if (correctArticle.equals(userArticle)) {
            resultMessage = "✅ Correct! " + wordEnding.getEndings() + " → " + wordEnding.getExample();
        } else {
            resultMessage = "❌ Not true! " + wordEnding.getEndings() + " → " + wordEnding.getExample() +
                    " (Your answer: " + userArticle + ")";
        }
        // ✅ Add result to the list and display it on the screen
        userAnswers.add(resultMessage);
        model.addAttribute("userAnswers", userAnswers);

        //model.addAttribute("wordEnding", endingsService.getRandomEndings()); // Load new word
        return "quiz";
    }
}
