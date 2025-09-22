package com.example.slothlang_be.entity;


import com.example.slothlang_be.enums.Part;
import com.example.slothlang_be.enums.Skill;
import com.example.slothlang_be.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "exam_questions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExamQuestion {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "ord", nullable = false)
    Integer ord;

    @Column(name = "part", nullable = false)
    @Enumerated(EnumType.STRING)
    Part part;

    @Column(name = "skill", nullable = false)
    @Enumerated(EnumType.STRING)
    Skill skill;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    Exam exam;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    Question question;




}
