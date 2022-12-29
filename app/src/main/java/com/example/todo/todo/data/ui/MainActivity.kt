package com.example.todo.todo.data.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.todo.todo.data.ToDo
import com.example.todo.ui.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val toDoViewModel:ToDoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    AddToolbar()

                }
            }
        }

    }
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun AddToolbar() {
        Scaffold(
            topBar = {
                TopAppBar(title={ Text(text = "To Do App")})
            },
            floatingActionButton = {
                val openDialog= remember{mutableStateOf(false)}
                FloatingActionButton(onClick = {openDialog.value=true}){
                    AddDialogBox(openDialog = openDialog )
                Icon(Icons.Default.Add, contentDescription = "Add")
            }}
        ) {
            RecyclerView(toDoViewModel = toDoViewModel)
        }
    }
    @Composable
    fun AddDialogBox(openDialog: MutableState<Boolean>){
        val title= remember{ mutableStateOf("")}
        val description= remember{ mutableStateOf("")}
       if(openDialog.value){
           AlertDialog(onDismissRequest = {openDialog.value=false}
               , title = { Text(text="ToDo")}
               , text={
                   Column(modifier= Modifier
                       .padding(12.dp)
                       .fillMaxWidth()){
                       OutlinedTextField(value = title.value, onValueChange = {title.value = it},
                           placeholder = {
                               Text(text = "Enter Title")
                           },
                           label={
                               Text(text = "Enter Title")
                           }
                           , modifier = Modifier.padding(10.dp)
                       )


                       OutlinedTextField(value = description.value, onValueChange = {description.value = it},
                           placeholder = {
                               Text(text = "Enter description")
                           },
                           label={
                               Text(text = "Enter description")
                           }
                           , modifier = Modifier.padding(10.dp)
                       )
                   }

               }, confirmButton = {
                   OutlinedButton(onClick = {insert(title,description)
                   openDialog.value=false
                   }) {
                       Text(text = "Save")
                   }
               }
           )
       }
    }
    private fun insert(title:MutableState<String>,description:MutableState<String>){
        lifecycleScope.launchWhenStarted {
            if(!TextUtils.isEmpty(title.value)&& !TextUtils.isEmpty(description.value))
            {
                toDoViewModel.insert(
                    ToDo( title.value,description.value)
                )
                Toast.makeText(this@MainActivity,"Inserted",Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this@MainActivity,"Empty",Toast.LENGTH_SHORT).show()
            }
        }
    }
    @Composable
    fun EachRow(toDo: ToDo){
        
        Card(modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            ,elevation=4.dp
        , shape = RoundedCornerShape(5.dp)

            ) {
            Column(
                modifier=Modifier.padding(10.dp)
            ){
                Text(text = toDo.title, fontWeight = FontWeight.ExtraBold)
                Text(text = toDo.description, fontStyle = FontStyle.Italic)
            }
        }
    }
    @Composable
    fun RecyclerView(toDoViewModel: ToDoViewModel){
        LazyColumn{
            items(toDoViewModel.response.value){toDo->
                EachRow(toDo = toDo)
            }
        }
    }
}