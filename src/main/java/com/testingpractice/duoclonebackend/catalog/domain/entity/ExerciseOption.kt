package com.testingpractice.duoclonebackend.catalog.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "exercise_options")
class ExerciseOption(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "exercise_id")
    var exerciseId: Int? = null,

    @Column(name = "content")
    var content: String? = null,

    @Column(name = "image_url")
    var imageUrl: String? = null,

    @Column(name = "is_correct")
    var isCorrect: Boolean? = null,

    @Column(name = "answer_order")
    var answerOrder: Int? = null
)