package com.example.GermanArticleQuestionary.service;

import com.example.GermanArticleQuestionary.model.Endings;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EndingsService {
    Endings createEndings(Endings endings);

    Endings updateEndings(Endings endings);

    List<Endings> getAllEndings();

    Endings getEndingsById(int endingsId);

    void deleteEndings(int id);

    //List<EndingsCSVRecord> convertCSV(File csvFile);

    Integer uploadEndings(MultipartFile File);

    //Set<Endings> parseCsv(MultipartFile File);

    Endings getRandomEndings();

    Endings findByEnding(String ending);
}
