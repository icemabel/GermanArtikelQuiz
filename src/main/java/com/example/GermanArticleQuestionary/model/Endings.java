package com.example.GermanArticleQuestionary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "endings", nullable = false)
    private String endings;

    @Column(name = "artikel", nullable = false)
    private String artikel;

    @Column(name = "example", nullable = false)
    private String example;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endings endings1 = (Endings) o;
        return Objects.equals(endings, endings1.endings) &&
                Objects.equals(artikel, endings1.artikel) &&
                Objects.equals(example, endings1.example);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endings, artikel, example);
    }

}
