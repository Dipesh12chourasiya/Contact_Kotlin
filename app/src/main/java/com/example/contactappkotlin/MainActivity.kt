package com.example.contactappkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.contactappkotlin.app_ui.AddContactScreen
import com.example.contactappkotlin.app_ui.ContactDetailScreen
import com.example.contactappkotlin.app_ui.ContactListScreen
import com.example.contactappkotlin.app_ui.EditContatScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contact_database"
        ).build()

        val repository = ContactRepository(database.contactDao())

        val viewModel: ContactViewModel by viewModels { ContactViewModelFactory(repository) }

        setContent {
            val navController = rememberNavController()
            NavHost(navController, "contactList") {
                composable("contactList"){
                    ContactListScreen(viewModel, navController)
                }
                composable("addContact"){
                    AddContactScreen(viewModel, navController)
                }
                composable("contactDetail/{contactId}"){ backStackEntry ->
                    val contactId = backStackEntry.arguments?.getString("contactId")?.toInt()
                    val contact = viewModel.allContacts.observeAsState(initial = emptyList()).value.find { it.id == contactId }
                    contact?.let {
                        ContactDetailScreen(contact = it, viewModel,navController)
                    }
                }
                composable("editContact/{contactId}"){ backStackEntry ->
                    val contactId = backStackEntry.arguments?.getString("contactId")?.toInt()
                    val contact = viewModel.allContacts.observeAsState(initial = emptyList()).value.find { it.id == contactId }
                    contact?.let {
                        EditContatScreen(contact = it, viewModel,navController)
                    }
                }
            }
        }
    }
}



