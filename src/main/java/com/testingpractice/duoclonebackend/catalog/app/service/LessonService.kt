package com.testingpractice.duoclonebackend.catalog.app.service

import com.testingpractice.duoclonebackend.catalog.api.dto.LessonDto
import com.testingpractice.duoclonebackend.catalog.app.mapper.LessonMapper
import com.testingpractice.duoclonebackend.catalog.infra.repository.LessonRepository
import com.testingpractice.duoclonebackend.progress.infra.repository.LessonCompletionRepository
import org.springframework.stereotype.Service
import java.util.Collection

@Service
class LessonService(
    private val lessonRepository: LessonRepository,
    private val lessonMapper: LessonMapper,
    private val lessonCompletionRepository: LessonCompletionRepository
) {

    fun getLessonsByUnit(unitId: Int, userId: Int): List<LessonDto> {
        val lessons = lessonRepository.findAllByUnitId(unitId)
        val lessonIds = lessons.mapNotNull { it.id }
        return lessonMapper.toDtoList(
            lessons,
            completedSetFor(userId, lessonIds)
        )
    }

    fun getLessonsByIds(lessonIds: List<Int>, userId: Int): List<LessonDto> {
        val lessons = lessonRepository.findAllById(lessonIds)
        val ids = lessons.mapNotNull { it.id }
        return lessonMapper.toDtoList(
            lessons,
            completedSetFor(userId, ids)
        )
    }

    fun getLessonIdsByUnit(unitId: Int): List<Int> {
        return lessonRepository.findAllLessonIdsByUnitId(unitId)
    }

    fun completedSetFor(userId: Int, lessonIds: List<Int>): Set<Int> {
        if (lessonIds.isEmpty()) return emptySet()
        return lessonCompletionRepository
            .findCompletedLessonIdsIn(userId, lessonIds as Collection<Int>)
            .toSet()
    }
}