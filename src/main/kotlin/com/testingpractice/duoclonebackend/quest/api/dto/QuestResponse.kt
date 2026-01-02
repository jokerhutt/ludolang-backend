package com.testingpractice.duoclonebackend.quest.api.dto

data class QuestResponse(
    val code: String,
    val progress: Int,
    val total: Int,
    val active: Boolean
)