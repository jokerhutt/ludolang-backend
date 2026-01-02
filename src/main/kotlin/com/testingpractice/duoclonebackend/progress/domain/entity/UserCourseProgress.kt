package com.testingpractice.duoclonebackend.progress.domain.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "user_course_progress")
class UserCourseProgress(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    @Column(name = "user_id")
    var userId: Int? = null,

    @Column(name = "course_id")
    var courseId: Int? = null,

    @Column(name = "is_complete")
    var isComplete: Boolean? = null,

    @Column(name = "current_lesson_id")
    var currentLessonId: Int? = null,

    @Column(
        name = "updated_at",
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    var updatedAt: Timestamp? = null
)