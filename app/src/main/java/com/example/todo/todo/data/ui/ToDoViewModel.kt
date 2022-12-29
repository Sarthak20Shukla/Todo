package com.example.todo.todo.data.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.todo.data.ToDo
import com.example.todo.todo.data.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel
@Inject
constructor(private val toDoRepository: ToDoRepository):ViewModel() {
  val response:MutableState<List<ToDo>> = mutableStateOf(listOf())
    fun insert(toDo: ToDo) =viewModelScope.launch{
  toDoRepository.insert(toDo)
 }
    init{
        getAllTodos()
    }
    fun getAllTodos()=viewModelScope.launch {
        toDoRepository.getAllTodos()
        .catch{ e->Log.d("main","Exception: ${e.message}")}
        .collect{ response.value=it}
}}