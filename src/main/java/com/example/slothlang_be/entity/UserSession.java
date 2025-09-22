package com.example.slothlang_be.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user_sessions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSession {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    User user;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    Exam exam;

    @Column(name = "started_at", nullable = false)
    LocalDateTime startedAt;

    @Column(name = "finished_at", nullable = true)
    LocalDateTime finishedAt;

    @Column(name = "score", nullable = true)
    Integer score;

    @OneToMany(mappedBy = "userSession", cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<UserAnswer> userAnswers;




}
