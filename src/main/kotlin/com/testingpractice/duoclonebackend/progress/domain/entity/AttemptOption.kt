package com.testingpractice.duoclonebackend.progress.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "exercise_attempt_option")
class AttemptOption(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "attempt_id")
    var attemptId: Int? = null,

    @Column(name = "option_id")
    var optionId: Int? = null,

    @Column(name = "position")
    var position: Int? = null
)