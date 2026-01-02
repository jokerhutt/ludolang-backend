package com.testingpractice.duoclonebackend.progress.domain.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "exercise_attempts")
class ExerciseAttempt(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "exercise_id")
    var exerciseId: Int? = null,

    @Column(name = "user_id")
    var userId: Int? = null,

    @Column(name = "is_checked", nullable = false)
    var isChecked: Boolean = false,

    @Column(name = "submitted_at")
    var submittedAt: Timestamp? = null,

    @Column(name = "option_id")
    var optionId: Int? = null,

    @Column(name = "score")
    var score: Int? = null
)