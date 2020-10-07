package com.viaplay.android.tvsample.core.utils.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}