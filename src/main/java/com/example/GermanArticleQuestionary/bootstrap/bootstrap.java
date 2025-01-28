package com.example.GermanArticleQuestionary.bootstrap;

/*
@Component
@RequiredArgsConstructor
public class bootstrap implements CommandLineRunner {
    private final EndingsRepository endingsRepository;
    private final EndingsService endingsService;

    @Override
    public void run(String... args) throws Exception {

    }

    private void loadCsvData() throws FileNotFoundException {
        if (endingsRepository.count() < 10){
            File file = ResourceUtils.getFile("classpath:Processed_Artikel_Endungen.csv");

            List<EndingsCSVRecord> recs = endingsService.convertCSV(file);

            recs.forEach(endingsCSVRecord -> {
                Endings endings = Endings.builder()
                        .endings(StringUtils.abbreviate(endingsCSVRecord.getEndings(), 50))
                        .artikel(StringUtils.abbreviate(endingsCSVRecord.getArtikel(), 50))
                        .example(StringUtils.abbreviate(endingsCSVRecord.getExample(), 50))
                        .build();
                endingsRepository.save(endings);
            });
        }
    }
}
*/