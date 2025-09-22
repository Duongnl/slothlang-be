package com.example.slothlang_be.entity;

import com.example.slothlang_be.enums.ChoiceLabel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_answers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAnswer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    UserSession userSession;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    Question question;

    @Column(name = "chosen_label", nullable = false)
    @Enumerated(EnumType.STRING)
    ChoiceLabel chosenLabel;

    @Column(name = "is_correct" , nullable = true)
    Boolean isCorrect;

    @Column(name = "answered_at", nullable = false)
    LocalDateTime answeredAt;
}
