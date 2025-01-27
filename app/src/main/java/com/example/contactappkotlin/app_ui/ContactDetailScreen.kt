package com.example.contactappkotlin.app_ui

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
fun ContactDetailScreen(contact: Contact, viewModel: ContactViewModel, navController: NavController) {
    val context = LocalContext.current.applicationContext

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
                        Text(text = "Contact Details", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Contact Details",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.contactdetails),
                            contentDescription = null
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GreenJc,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(containerColor = GreenJc, onClick = { navController.navigate("editContact/${contact.id}")}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    ) { padingValue ->

        Column(Modifier.fillMaxWidth().padding(padingValue)
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Card(Modifier.fillMaxWidth()
                .padding(16.dp),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Column (Modifier.fillMaxWidth().padding(16.dp)
                , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){

                    Image(painter = rememberAsyncImagePainter(contact.image), contentDescription = contact.name, Modifier.size(128.dp).clip(CircleShape)
                    , contentScale = ContentScale.Crop)

                    Spacer(Modifier.height(16.dp))

                    // for name
                    detailLayout("Name",contact.name)

                    detailLayout("Phone", contact.phoneNumber)

                    detailLayout("Email", contact.email)

                    Spacer(Modifier.height(16.dp))

                    Button(colors = ButtonDefaults.buttonColors(Color.Red),
                        onClick = {
                            viewModel.deleteContact(contact)
                            navController.navigate("contactList"){
                                popUpTo(0)
                            }
                        }) {

                        Text("Delete Contact")
                    }

//                    Card(Modifier.fillMaxWidth().padding(8.dp), colors = CardDefaults.cardColors(Color.White),
//                        shape = RoundedCornerShape(16.dp),
//                        elevation = CardDefaults.cardElevation(8.dp)
//                    ) {
//                        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                            Text("Name: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                            Spacer(Modifier.width(8.dp))
//                            Text(contact.name, fontSize = 16.sp)
//                        }
//                    }
                }
            }
        }
    }
}


@Composable
fun detailLayout(key:String, value: String){

    Card(Modifier.fillMaxWidth().padding(8.dp), colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("${key}: ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            Text(value, fontSize = 16.sp)
        }
    }
}