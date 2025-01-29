package com.example.GermanArticleQuestionary.controller;

import com.example.GermanArticleQuestionary.model.Endings;
import com.example.GermanArticleQuestionary.service.EndingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class EndingsController {

    private final EndingsService endingsService;

    @GetMapping("/endings")
    public List<Endings> getAllEndings(){
        return endingsService.getAllEndings();
    }

    @GetMapping("/endings/{id}")
    public ResponseEntity<Endings> getEndingsById(@PathVariable int id){
        return ResponseEntity.ok().body(endingsService.getEndingsById(id));
    }

    @PostMapping("/endings")
    public ResponseEntity<Endings> createEndings(@RequestBody Endings endings){
        return ResponseEntity.ok().body(this.endingsService.createEndings(endings));
    }

    @PutMapping("/endings/{id}")
    public ResponseEntity<Endings> updateEndings(@PathVariable int id, @RequestBody Endings endings){
        endings.setId(id);
        return ResponseEntity.ok().body(this.endingsService.updateEndings(endings));
    }

    @DeleteMapping("/endings/{id}")
    public HttpStatus deleteEndings(@PathVariable int id){
        this.endingsService.deleteEndings(id);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/endings/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Integer> uploadEndings(@RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(endingsService.uploadEndings(file));
    }



}
