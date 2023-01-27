package com.ssafy.campinity.data.remote.datasource.base

interface DataToDomainMapper<T> {
    fun toDomainModel(): T
}