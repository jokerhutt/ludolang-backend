package com.testingpractice.duoclonebackend.progress.domain.entity.embeddable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class LessonCompletionId(

    @Column(name = "user_id")
    var userId: Int? = null,

    @Column(name = "lesson_id")
    var lessonId: Int? = null

) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LessonCompletionId) return false
        return userId == other.userId && lessonId == other.lessonId
    }

    override fun hashCode(): Int {
        return 31 * (userId ?: 0) + (lessonId ?: 0)
    }
}