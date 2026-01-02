package com.testingpractice.duoclonebackend.commons.mapper

import org.springframework.stereotype.Component

@Component
class BasicMapper {
    fun <S, T> one(source: S, convert: (S) -> T): T =
        convert(source)

    fun <S, T> list(sourceList: List<S>, convert: (S) -> T): List<T> =
        sourceList.map { convert(it) }
}