package com.example.slothlang_be.entity;

import com.example.slothlang_be.enums.AttachmentType;
import com.example.slothlang_be.enums.Skill;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "attachments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private Question question;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    AttachmentType type;

    @Column(name = "url", nullable = false)
    String url;





}
