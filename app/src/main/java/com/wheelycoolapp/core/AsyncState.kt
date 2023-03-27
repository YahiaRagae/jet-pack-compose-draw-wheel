package com.wheelycoolapp.core

import kotlinx.coroutines.flow.MutableStateFlow

sealed class AsyncState<out T>(open val value: T?) {
    class None<out T>(override val value: T? = null) : AsyncState<T>(value)
    class Loading<out T>(override val value: T? = null) : AsyncState<T>(value)
    class Error<out T>(override val value: T? = null, val exception: Throwable) :
        AsyncState<T>(value)

    class Success<out T>(override val value: T) : AsyncState<T>(value)
}

fun <T> AsyncState<T>.none(): AsyncState.None<T> {
    return AsyncState.None(value)
}

fun <T> AsyncState<T>.loading(): AsyncState.Loading<T> {
    return AsyncState.Loading(value)
}

fun <T> AsyncState<T>.error(exception: Throwable): AsyncState.Error<T> {
    return AsyncState.Error(value, exception)
}

fun <T> AsyncState<T>.success(newValue: T): AsyncState.Success<T> {
    return AsyncState.Success(newValue)
}

fun <T> MutableStateFlow<AsyncState<T>>.none() {
    value = value.none()
}

fun <T> MutableStateFlow<AsyncState<T>>.loading() {
    value = value.loading()
}

fun <T> MutableStateFlow<AsyncState<T>>.error(exception: Throwable) {
    value = value.error(exception)
}

fun <T> MutableStateFlow<AsyncState<T>>.success(newValue: T) {
    value = value.success(newValue)
}

fun <T> AsyncState<T>.updateState(content: (value: T?) -> T): AsyncState<T> {
    val newValue = content(value)
    return when (this) {
        is AsyncState.Success -> AsyncState.Success(newValue)
        is AsyncState.Error -> AsyncState.Error(newValue, this.exception)
        is AsyncState.Loading -> AsyncState.Loading(newValue)
        is AsyncState.None -> AsyncState.None(newValue)
    }
}

fun <T> MutableStateFlow<AsyncState<T>>.updateState(content: (value: T?) -> T) {
    value = value.updateState(content)
}

fun <T> AsyncState<T>.updateIntoSuccess(content: (value: T?) -> T): AsyncState<T> {
    val newValue = content(value)
    return AsyncState.Success(newValue)
}

fun <T> MutableStateFlow<AsyncState<T>>.updateIntoSuccess(content: (value: T?) -> T) {
    value = value.updateIntoSuccess(content)
}

fun <T> AsyncState<T>.updateSuccessfulState(content: (value: T) -> T): AsyncState<T> {
    if (this is AsyncState.Success) {
        val newValue = content(value)
        return AsyncState.Success(newValue)
    }
    return this
}

fun <T> MutableStateFlow<AsyncState<T>>.updateSuccessfulState(content: (value: T) -> T) {
    value = value.updateSuccessfulState(content)
}

fun <T> AsyncState<T>.withState(content: (value: T?) -> Unit) {
    content(value)
}

fun <T> MutableStateFlow<AsyncState<T>>.withState(content: (value: T?) -> Unit) {
    value.withState(content)
}

fun <T> AsyncState<T>.withSuccessfulState(content: (value: T) -> Unit) {
    if (this is AsyncState.Success) {
        content(value)
    }
}

fun <T> MutableStateFlow<AsyncState<T>>.withSuccessfulState(content: (value: T) -> Unit) {
    value.withSuccessfulState(content)
}

inline fun <T> AsyncState<T>.onLoading(content: () -> Unit): AsyncState<T> {
    if (this is AsyncState.Loading) {
        content()
    }
    return this
}

inline fun <T> AsyncState<T>.onError(content: (exception: Throwable) -> Unit): AsyncState<T> {
    if (this is AsyncState.Error) {
        content(exception)
    }
    return this
}

inline fun <T> AsyncState<T>.onSuccess(content: (value: T) -> Unit): AsyncState<T> {
    if (this is AsyncState.Success) {
        content(value)
    }
    return this
}

fun <T> mutableAsyncStateFlowOf(): MutableStateFlow<AsyncState<T>> =
    MutableStateFlow(AsyncState.None())
