package com.ssafy.campinity.di

import android.content.Context
import androidx.room.Room
import com.ssafy.campinity.data.local.AreaDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AreaDataBase::class.java, "area_db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideAreaDao(appDataBase: AreaDataBase) = appDataBase.areaDao()
}