package com.example.slothlang_be.entity;


import com.example.slothlang_be.enums.ChoiceLabel;
import com.example.slothlang_be.enums.Skill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "choices")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Choice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "label", nullable = false)
    @Enumerated(EnumType.STRING)
    ChoiceLabel label;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    Question question;

    @Column(name = "content" ,columnDefinition = "TEXT", nullable = true)
    String content;

    @Column(name = "is_correct" , nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean isCorrect;

}
