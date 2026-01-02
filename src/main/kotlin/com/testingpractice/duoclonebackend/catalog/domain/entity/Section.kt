package com.testingpractice.duoclonebackend.catalog.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "sections")
class Section(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "course_id")
    var courseId: Int? = null,

    @Column(name = "order_index")
    var orderIndex: Int? = null
)