package com.example.todo.todo.data.di

import android.app.Application
import androidx.room.Room

import com.example.todo.todo.data.ToDoDao
import com.example.todo.todo.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(application: Application):ToDoDatabase=
        Room.databaseBuilder(application, ToDoDatabase::class.java,"ToDoDatabase")
            .fallbackToDestructiveMigration()
            .build()
    @Provides
    @Singleton
    fun providesDao(db:ToDoDatabase):ToDoDao=db.getDao()
    }
