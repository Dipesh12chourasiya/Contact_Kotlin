package com.example.contactappkotlin.app_ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.contactappkotlin.Contact
import com.example.contactappkotlin.ContactViewModel
import com.example.contactappkotlin.ui.theme.GreenJc
import com.example.thecontactsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContatScreen(contact: Contact, viewModel: ContactViewModel, navController: NavController) {
    val context = LocalContext.current.applicationContext

    var imageUri by remember {
        mutableStateOf(contact.image)
    }
    var name by remember {
        mutableStateOf(contact.name)
    }
    var phonenumber by remember {
        mutableStateOf(contact.phoneNumber)
    }
    var email by remember {
        mutableStateOf(contact.email)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { newUri ->
                val internalpath = copyUriToInternalStorage(context, newUri, "$name.jpg")
                internalpath?.let { path -> imageUri = path }
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(48.dp),
                title = {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(Alignment.CenterVertically)
                    ) {
                        Text(text = "Add Contact", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Edit Contact",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.editcontact),
                            contentDescription = null
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GreenJc,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(GreenJc)
            ) {
                Text(text = "Choose Image")
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = phonenumber,
                onValueChange = { phonenumber = it },
                label = { Text(text = "Phone Number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val updateContact = contact.copy(image = imageUri, name = name, phoneNumber = phonenumber, email = email)
                viewModel.updateContact(updateContact)
                navController.navigate("contactList"){
                    popUpTo(0)
                }
            }, colors = ButtonDefaults.buttonColors(GreenJc)) {
                Text("Update Contact")
            }
        }
    }

}