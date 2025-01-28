package com.caring.user_service.domain.manager.entity;

import com.caring.user_service.domain.auditing.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "submission")
public class Submission extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long id;

    @Column(unique = true)
    private String submissionUuid;

    private String name;

    private String password;

    private String shelterUuid;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;
}
