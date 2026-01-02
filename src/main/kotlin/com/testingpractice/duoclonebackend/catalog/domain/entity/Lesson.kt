package com.testingpractice.duoclonebackend.catalog.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "lessons")
class Lesson(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "unit_id")
    var unitId: Int? = null,

    @Column(name = "lesson_type")
    var lessonType: String? = null,

    @Column(name = "order_index")
    var orderIndex: Int? = null
)