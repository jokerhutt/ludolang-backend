package com.testingpractice.duoclonebackend.progress.domain.entity

import com.testingpractice.duoclonebackend.progress.domain.entity.embeddable.LessonCompletionId
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "lesson_completions")
class LessonCompletion(

    @EmbeddedId
    var id: LessonCompletionId? = null,

    @Column(name = "course_id")
    var courseId: Int? = null,

    @Column(name = "score")
    var score: Int? = null,

    @Column(name = "completed_at")
    var completedAt: Timestamp? = null
) {

    fun getUserId(): Int? =
        id?.userId

    fun setUserId(userId: Int?) {
        if (id == null) {
            id = LessonCompletionId()
        }
        id?.userId = userId
    }

    fun getLessonId(): Int? =
        id?.lessonId

    fun setLessonId(lessonId: Int?) {
        if (id == null) {
            id = LessonCompletionId()
        }
        id?.lessonId = lessonId
    }
}