package com.testingpractice.duoclonebackend.catalog.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "course")
class Course(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "title")
    var title: String? = null,

    @Column(name = "image_src")
    var imgSrc: String? = null
)