package com.example.GermanArticleQuestionary.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndingsCSVRecord {
    @CsvBindByName(column = "endings")
    private String endings;

    @CsvBindByName(column = "artikel")
    private String artikel;

    @CsvBindByName(column = "example")
    private String example;

}
