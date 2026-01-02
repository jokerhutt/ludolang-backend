package com.testingpractice.duoclonebackend.catalog.api.controller

import com.testingpractice.duoclonebackend.auth.app.service.JwtService
import com.testingpractice.duoclonebackend.catalog.app.service.CourseService
import com.testingpractice.duoclonebackend.catalog.app.service.SectionService
import com.testingpractice.duoclonebackend.catalog.domain.entity.Course
import com.testingpractice.duoclonebackend.commons.constants.pathConstants
import com.testingpractice.duoclonebackend.progress.api.dto.ChangeCourseDto
import com.testingpractice.duoclonebackend.progress.api.dto.NewCourseRequest
import com.testingpractice.duoclonebackend.progress.app.service.CourseProgressService
import com.testingpractice.duoclonebackend.progress.app.service.UnitService

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(pathConstants.COURSES)
class CourseController(
    private val unitService: UnitService,
    private val sectionService: SectionService,
    private val courseService: CourseService,
    private val jwtService: JwtService,
    private val courseProgressService: CourseProgressService
) {

    @GetMapping(pathConstants.GET_SECTION_IDS_BY_COURSE)
    fun getSectionIdsByCourse(@PathVariable courseId: Int): List<Int> {
        return sectionService.getSectionIdsByCourse(courseId)
    }

    @GetMapping(pathConstants.GET_ALL_COURSES)
    fun getCourses(): List<Course> {
        return courseService.getAllCourses()
    }

    @PostMapping(pathConstants.CHANGE_COURSE)
    fun changeUserCourse(
        @RequestBody req: NewCourseRequest,
        @AuthenticationPrincipal(expression = "id") userId: Int
    ): ChangeCourseDto {
        return courseService.changeUserCourse(userId, req.newCourse!!)
    }

    @GetMapping(pathConstants.GET_USER_COURSES)
    fun getUserCourses(@PathVariable userId: Int): List<Course> {
        return courseProgressService.getUserCourses(userId)
    }
}