package com.example.GermanArticleQuestionary.repository;

import com.example.GermanArticleQuestionary.model.Endings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EndingsRepository extends JpaRepository<Endings, Integer> {
    @Query("SELECT e.endings FROM Endings e")
    List<String> findAllEndings();

    //Optional<Endings> findByEndings(String ending);

    @Query("SELECT e FROM Endings e WHERE e.endings = :endings")
    Optional<Endings> findByEndings(@Param("endings") String endings);
}
