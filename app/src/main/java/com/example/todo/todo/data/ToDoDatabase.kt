package com.example.todo.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [ToDo:: class],version=1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
 abstract fun getDao(): ToDoDao

}