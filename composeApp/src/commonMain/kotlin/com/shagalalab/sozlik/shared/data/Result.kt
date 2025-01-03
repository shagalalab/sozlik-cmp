package com.shagalalab.sozlik.shared.data

/**
 * Wraps a return type in [Result].
 *
 * Any [Exception] thrown is converted to [Result.Failure].
 */
@Suppress("TooGenericExceptionCaught")
internal suspend fun <T : Any> asResult(lambda: suspend () -> T): Result<T> = try {
    Result.success(lambda())
} catch (throwable: Throwable) {
    Result.failure(throwable)
}
