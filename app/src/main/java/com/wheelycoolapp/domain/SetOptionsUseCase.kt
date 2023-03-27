package com.wheelycoolapp.domain

import android.content.SharedPreferences
import com.wheelycoolapp.data.SharedPreferencesConsts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SetOptionsUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    suspend operator fun invoke(options: List<String>): Result<Any> {
        return withContext(Dispatchers.IO) {
            with(sharedPreferences.edit()) {
                putStringSet(SharedPreferencesConsts.OPTIONS, options.toSet())
                apply()
            }
            Result.success("")
        }
    }
}