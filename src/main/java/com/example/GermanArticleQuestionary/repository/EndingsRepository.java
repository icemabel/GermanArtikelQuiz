package com.example.GermanArticleQuestionary.repository;

import com.example.GermanArticleQuestionary.model.Endings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EndingsRepository extends JpaRepository<Endings, Integer> {
    @Query("SELECT e.endings FROM Endings e")
    List<String> findAllEndings();
}
