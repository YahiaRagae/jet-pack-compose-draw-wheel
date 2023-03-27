package com.wheelycoolapp.di

import android.content.Context
import android.content.SharedPreferences
import com.wheelycoolapp.data.SharedPreferencesConsts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    //TODO: Move from using SharedPreferences to use a LocalStorage Interface

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            SharedPreferencesConsts.SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
}