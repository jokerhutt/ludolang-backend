package com.testingpractice.duoclonebackend.catalog.infra.projection

interface FlatUnitLessonRowProjection {

    fun getUnitId(): Int?
    fun getUnitOrder(): Int?
    fun getLessonId(): Int?
    fun getLessonOrder(): Int?
}