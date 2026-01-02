package com.testingpractice.duoclonebackend.catalog.infra.repository

import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository :
    JpaRepository<Course, Int>