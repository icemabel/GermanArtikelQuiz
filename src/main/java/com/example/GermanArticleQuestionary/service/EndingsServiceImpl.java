package com.example.GermanArticleQuestionary.service;

import com.example.GermanArticleQuestionary.exception.ResourceNotFoundException;
import com.example.GermanArticleQuestionary.model.Endings;
import com.example.GermanArticleQuestionary.model.EndingsCSVRecord;
import com.example.GermanArticleQuestionary.repository.EndingsRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;


@Service
@Transactional
@Slf4j
public class EndingsServiceImpl implements EndingsService {
    @Autowired
    private EndingsRepository endingsRepository;


    @Override
    public Endings createEndings (Endings endings) {
        return endingsRepository.save(endings);
    }

    @Override
    public Endings updateEndings(Endings endings) {
        Optional<Endings> productDb = this.endingsRepository.findById(endings.getId());

        if(productDb.isPresent()) {
            Endings productUpdate = productDb.get();
            productUpdate.setId(endings.getId());
            productUpdate.setEndings(endings.getEndings());
            productUpdate.setArtikel(endings.getArtikel());
            endingsRepository.save(productUpdate);
            return productUpdate;
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + endings.getId());
        }
    }

    @Override
    public List<Endings> getAllEndings() {
        return this.endingsRepository.findAll();
    }

    @Override
    public Endings getEndingsById(int endingsId) {

        Optional<Endings> endingsDb = this.endingsRepository.findById(endingsId);

        if(endingsDb.isPresent()) {
            return endingsDb.get();
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + endingsId);
        }
    }

    @Override
    public void deleteEndings(int endingsId) {
        Optional<Endings> endingsDb = this.endingsRepository.findById(endingsId);

        if(endingsDb.isPresent()) {
            this.endingsRepository.delete(endingsDb.get());
        }else {
            throw new ResourceNotFoundException("Record not found with id : " + endingsId);
        }

    }

    /*
    @Override
    public List<EndingsCSVRecord> convertCSV(File csvFile) {
        try (FileReader fileReader = new FileReader(csvFile)) {
            List<EndingsCSVRecord> endingsCSVRecords = new CsvToBeanBuilder<EndingsCSVRecord>(fileReader)
                    .withType(EndingsCSVRecord.class)
                    .build()
                    .parse();

            // Log the parsed records for debugging
            log.info("Parsed Records: {}", endingsCSVRecords);

            System.out.println("Parsed Records: " + endingsCSVRecords);
            return endingsCSVRecords;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("CSV file not found: " + csvFile.getAbsolutePath(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV file: " + csvFile.getAbsolutePath(), e);
        }
    } */

    public Integer uploadEndings(MultipartFile file) {
        Set<Endings> endingsSet = null;
        try {
            endingsSet = parseCsv(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Check for existing endings and filter them out
        List<String> existingEndings = endingsRepository.findAllEndings();
        endingsSet = endingsSet.stream()
                .filter(ending -> !existingEndings.contains(ending.getEndings()))
                .collect(Collectors.toSet());

        endingsRepository.saveAll(endingsSet);
        return endingsSet.size();
    }


    private Set<Endings> parseCsv(MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<EndingsCSVRecord> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(EndingsCSVRecord.class);

            CsvToBean<EndingsCSVRecord> csvToBean = new CsvToBeanBuilder<EndingsCSVRecord>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // Filter duplicates by using a HashSet to track seen endings
            Set<String> seenEndings = new HashSet<>();
            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> Endings.builder()
                            .endings(csvLine.getEndings())
                            .artikel(csvLine.getArtikel())
                            .example(csvLine.getExample())
                            .build())
                    .filter(ending -> seenEndings.add(ending.getEndings())) // Add only unique endings
                    .collect(Collectors.toSet());
        }
    }
}
