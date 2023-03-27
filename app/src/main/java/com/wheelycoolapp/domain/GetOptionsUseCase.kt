package com.wheelycoolapp.domain

import android.content.SharedPreferences
import com.wheelycoolapp.data.SharedPreferencesConsts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class GetOptionsUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    suspend operator fun invoke(): Result<List<String>> {
        return withContext(Dispatchers.IO) {
            val dataSet =
                sharedPreferences.getStringSet(SharedPreferencesConsts.OPTIONS, emptySet())
            when (dataSet.isNullOrEmpty()) {
                true -> Result.failure(Throwable("no data"))

                false -> Result.success(dataSet.toList())
            }
        }
    }
}