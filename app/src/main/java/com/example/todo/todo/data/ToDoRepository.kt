package com.example.todo.todo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToDoRepository
    @Inject
    constructor(private val dao: ToDoDao){
        suspend fun insert(toDo: ToDo)= withContext(Dispatchers.IO){
            dao.insert(toDo)
        }
    fun getAllTodos():Flow<List<ToDo>> = dao.getAllToDos()
    }