package com.testingpractice.duoclonebackend.catalog.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "exercises")
class Exercise(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "prompt")
    var prompt: String? = null,

    @Column(name = "lesson_id")
    var lessonId: Int? = null,

    @Column(name = "type")
    var type: String? = null,

    @Column(name = "order_index")
    var orderIndex: Int? = null
)