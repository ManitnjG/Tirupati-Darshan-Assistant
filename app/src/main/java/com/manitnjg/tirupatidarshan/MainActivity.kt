package com.manitnjg.tirupatidarshan
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity:ComponentActivity(){
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{MaterialTheme{App{openOfficial()}}}}
 private fun openOfficial(){val uri=Uri.parse("https://tirupatibalaji.ap.gov.in/");runCatching{CustomTabsIntent.Builder().setShowTitle(true).build().launchUrl(this,uri)}.onFailure{startActivity(Intent(Intent.ACTION_VIEW,uri))}}
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun App(openOfficial:()->Unit){
 var tab by remember{mutableIntStateOf(0)}
 Scaffold(topBar={TopAppBar(title={Text("Tirupati Darshan Assistant",fontWeight=FontWeight.Bold)})},bottomBar={NavigationBar{
  listOf("Home" to Icons.Default.Home,"Watch" to Icons.Default.Notifications,"Bookings" to Icons.Default.ConfirmationNumber,"Profile" to Icons.Default.Person).forEachIndexed{i,p->NavigationBarItem(selected=tab==i,onClick={tab=i},icon={Icon(p.second,null)},label={Text(p.first)})}
 }}){pad->Box(Modifier.padding(pad)){when(tab){0->Home(openOfficial);1->Simple("Release Watch","Watch preferred dates and receive clearly-labelled prediction/official alerts.");2->Simple("My Bookings","Confirmed bookings appear here only after official confirmation.");else->Simple("Pilgrim Profiles","Secure local pilgrim profiles and family groups.")}}}
}
@Composable fun Home(openOfficial:()->Unit){Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)){
 Text("Upcoming Booking Releases",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold)
 ElevatedCard(Modifier.fillMaxWidth()){Column(Modifier.padding(20.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){
  Text("₹300 Special Entry Darshan",style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold)
  Text("Select a preferred Darshan date to predict from verified historical observations.")
  AssistChip(onClick={},label={Text("PREDICTION • NOT OFFICIAL")})
  Text("No verified history loaded yet",fontWeight=FontWeight.SemiBold)
  Text("The app will not invent a release date when evidence is insufficient.")
  Button(onClick={},modifier=Modifier.fillMaxWidth()){Icon(Icons.Default.Notifications,null);Spacer(Modifier.width(8.dp));Text("Watch Release")}
  OutlinedButton(onClick=openOfficial,modifier=Modifier.fillMaxWidth()){Text("Open Official TTD Booking")}
 }}
 Text("Booking preparation",style=MaterialTheme.typography.titleMedium,fontWeight=FontWeight.Bold)
 ListItem(headlineContent={Text("Pilgrims")},supportingContent={Text("Add and validate pilgrim details locally")},leadingContent={Icon(Icons.Default.Groups,null)})
 ListItem(headlineContent={Text("Release Calendar")},supportingContent={Text("Predicted and official states stay separate")},leadingContent={Icon(Icons.Default.CalendarMonth,null)})
 ListItem(headlineContent={Text("Accommodation & Travel")},supportingContent={Text("Plan separately from Darshan status")},leadingContent={Icon(Icons.Default.Luggage,null)})
 Text("Independent booking assistant. Final booking, CAPTCHA/OTP, payment and confirmation are provided by TTD.",style=MaterialTheme.typography.bodySmall)
}}
@Composable fun Simple(title:String,body:String){Column(Modifier.padding(24.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){Text(title,style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold);Text(body)}}