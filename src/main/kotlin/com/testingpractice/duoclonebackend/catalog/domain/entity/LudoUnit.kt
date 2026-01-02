package com.testingpractice.duoclonebackend.catalog.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "units")
class LudoUnit(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "animation_path")
    var animationPath: String? = null,

    @Column(name = "color")
    var color: String? = null,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "order_index")
    var orderIndex: Int? = null,

    @Column(name = "course_id")
    var courseId: Int? = null,

    @Column(name = "section_id")
    var sectionId: Int? = null
)