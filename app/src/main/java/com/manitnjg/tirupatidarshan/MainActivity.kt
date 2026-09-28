package com.manitnjg.tirupatidarshan
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.manitnjg.tirupatidarshan.ui.*
import com.manitnjg.tirupatidarshan.data.*
import com.manitnjg.tirupatidarshan.security.SensitiveData

class MainActivity:ComponentActivity(){
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{TirupatiTheme(content={App{openOfficial()}})}}
 private fun openOfficial(){val uri=Uri.parse("https://tirupatibalaji.ap.gov.in/");runCatching{CustomTabsIntent.Builder().setShowTitle(true).build().launchUrl(this,uri)}.onFailure{startActivity(Intent(Intent.ACTION_VIEW,uri))}}
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun App(openOfficial:()->Unit){
 val context=androidx.compose.ui.platform.LocalContext.current
 val store=remember{PilgrimStore(context)}
 val prefs=remember{BookingPreferences(context)}
 var pilgrims by remember{mutableStateOf(store.all())}
 var tab by remember{mutableIntStateOf(0)}
 Scaffold(containerColor=MaterialTheme.colorScheme.background,topBar={TopAppBar(colors=TopAppBarDefaults.topAppBarColors(containerColor=Navy,titleContentColor=Color.White),title={Column{Text("Tirupati Darshan",fontWeight=FontWeight.Bold);Text("Smart Assistant",style=MaterialTheme.typography.labelSmall,color=Gold)}})},bottomBar={NavigationBar(containerColor=Navy){
  listOf("Home" to Icons.Default.Home,"Prepare" to Icons.Default.CheckCircle,"Bookings" to Icons.Default.ConfirmationNumber,"Profile" to Icons.Default.Person).forEachIndexed{i,p->NavigationBarItem(selected=tab==i,onClick={tab=i},colors=NavigationBarItemDefaults.colors(selectedIconColor=Navy,selectedTextColor=Gold,indicatorColor=Gold,unselectedIconColor=Color.White,unselectedTextColor=Color.White),icon={Icon(p.second,null)},label={Text(p.first)})}
 }}){pad->Box(Modifier.padding(pad)){when(tab){
  0->Home(pilgrims,prefs,openOfficial,{tab=1},{tab=3})
  1->Preparation(pilgrims,prefs,openOfficial)
  2->Simple("My Bookings","Only bookings with reliable official confirmation will be shown as confirmed.")
  else->Profiles(pilgrims,{p->store.save(p);pilgrims=store.all()},{id->store.delete(id);pilgrims=store.all()})
 }}}}
@Composable fun Home(pilgrims:List<Pilgrim>,prefs:BookingPreferences,openOfficial:()->Unit,prepare:()->Unit,profiles:()->Unit){
 val selected=pilgrims.filter{prefs.selectedPilgrimIds.contains(it.id.toString())}
 val ready=prefs.preferredDate.isNotBlank()&&selected.isNotEmpty()&&selected.all{it.ready}
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(16.dp)){
  Text("Upcoming Booking Release",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,color=Navy)
  ElevatedCard(Modifier.fillMaxWidth(),colors=CardDefaults.elevatedCardColors(containerColor=Color.White),shape=RoundedCornerShape(22.dp)){Column(Modifier.padding(20.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){
   Text("₹300 Special Entry Darshan",style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold)
   AssistChip(onClick={},colors=AssistChipDefaults.assistChipColors(containerColor=GoldSoft,labelColor=Navy),label={Text("PREDICTION • NOT YET RELEASED",fontWeight=FontWeight.Bold)})
   Text("No verified history loaded yet",fontWeight=FontWeight.SemiBold)
   Text("No release date will be invented without sufficient verified evidence.")
   Text(if(ready)"BOOKING PREPARATION READY" else "Preparation incomplete",fontWeight=FontWeight.Bold)
   Button(onClick=prepare,modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(14.dp)){Icon(Icons.Default.Bolt,null);Spacer(Modifier.width(8.dp));Text("Prepare Booking",fontWeight=FontWeight.Bold)}
   OutlinedButton(onClick=openOfficial,modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(14.dp)){Icon(Icons.Default.OpenInBrowser,null);Spacer(Modifier.width(8.dp));Text("Open Official TTD")}
  }}
  ListItem(headlineContent={Text("Pilgrim Profiles")},supportingContent={Text(pilgrims.size.toString()+" saved")},leadingContent={Icon(Icons.Default.Groups,null)},modifier=Modifier.fillMaxWidth())
  Button(onClick=profiles,modifier=Modifier.fillMaxWidth()){Text("Manage Pilgrims")}
  Text("Independent booking assistant. CAPTCHA, OTP, queues, payment and final confirmation remain controlled by TTD.",style=MaterialTheme.typography.bodySmall)
 }}
@Composable fun Profiles(pilgrims:List<Pilgrim>,save:(Pilgrim)->Unit,delete:(Long)->Unit){
 var showAdd by remember{mutableStateOf(false)}
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){
  Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Text("Pilgrim Profiles",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,color=Navy);Button(onClick={showAdd=true},shape=RoundedCornerShape(50)){Icon(Icons.Default.PersonAdd,null);Spacer(Modifier.width(6.dp));Text("Add")}}
  if(pilgrims.isEmpty())Text("No pilgrims saved yet.")
  pilgrims.forEach{p->ElevatedCard(Modifier.fillMaxWidth(),shape=RoundedCornerShape(18.dp),colors=CardDefaults.elevatedCardColors(containerColor=Color.White)){Column(Modifier.padding(16.dp)){Text(p.name,fontWeight=FontWeight.Bold);Text(p.age.toString()+" years • "+p.gender);Text(p.idType+": "+SensitiveData.maskId(p.idNumber));Text(if(p.ready)"Ready ✓" else "Incomplete");TextButton(onClick={delete(p.id)}){Text("Delete")}}}}
 }
 if(showAdd)AddPilgrimDialog(onDismiss={showAdd=false},onSave={save(it);showAdd=false})
}
@Composable fun AddPilgrimDialog(onDismiss:()->Unit,onSave:(Pilgrim)->Unit){
 var name by remember{mutableStateOf("")};var age by remember{mutableStateOf("")};var gender by remember{mutableStateOf("")};var idType by remember{mutableStateOf("Aadhaar")};var idNo by remember{mutableStateOf("")};var mobile by remember{mutableStateOf("")}
 AlertDialog(onDismissRequest=onDismiss,title={Text("Add Pilgrim")},text={Column(Modifier.verticalScroll(rememberScrollState()),verticalArrangement=Arrangement.spacedBy(8.dp)){
  OutlinedTextField(name,{name=it},label={Text("Full name")});OutlinedTextField(age,{age=it.filter(Char::isDigit)},label={Text("Age")},keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number));OutlinedTextField(gender,{gender=it},label={Text("Gender")});OutlinedTextField(idType,{idType=it},label={Text("ID type")});OutlinedTextField(idNo,{idNo=it},label={Text("ID number")});OutlinedTextField(mobile,{mobile=it.filter(Char::isDigit)},label={Text("Mobile")})
 }},confirmButton={Button(enabled=name.isNotBlank()&&(age.toIntOrNull()?:0)>0&&idNo.length>=4,onClick={onSave(Pilgrim(name=name,age=age.toInt(),gender=gender,idType=idType,idNumber=idNo,mobile=mobile))}){Text("Save")}},dismissButton={TextButton(onClick=onDismiss){Text("Cancel")}})
}
@Composable fun Preparation(pilgrims:List<Pilgrim>,prefs:BookingPreferences,openOfficial:()->Unit){
 var date by remember{mutableStateOf(prefs.preferredDate)}
 var selected by remember{mutableStateOf(prefs.selectedPilgrimIds)}
 val chosen=pilgrims.filter{selected.contains(it.id.toString())}
 val ready=date.isNotBlank()&&chosen.isNotEmpty()&&chosen.all{it.ready}
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),verticalArrangement=Arrangement.spacedBy(14.dp)){
  Text("Booking Preparation",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold,color=Navy)
  AssistChip(onClick={},colors=AssistChipDefaults.assistChipColors(containerColor=GoldSoft),label={Text("₹300 SPECIAL ENTRY DARSHAN",fontWeight=FontWeight.Bold)})
  Text("This prepares information only; it does not indicate availability.")
  OutlinedTextField(date,{date=it;prefs.preferredDate=it},modifier=Modifier.fillMaxWidth(),label={Text("Preferred Darshan date (DD-MM-YYYY)")},leadingIcon={Icon(Icons.Default.CalendarMonth,null)})
  Text("Select pilgrims",fontWeight=FontWeight.Bold)
  if(pilgrims.isEmpty())Text("Add pilgrim profiles first.")
  pilgrims.forEach{p->Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){Column{Text(p.name,fontWeight=FontWeight.SemiBold);Text(if(p.ready)"Details ready" else "Incomplete",style=MaterialTheme.typography.bodySmall)};Checkbox(checked=selected.contains(p.id.toString()),onCheckedChange={checked->selected=if(checked)selected+p.id.toString() else selected-p.id.toString();prefs.selectedPilgrimIds=selected})}}
  HorizontalDivider()
  ElevatedCard(Modifier.fillMaxWidth(),colors=CardDefaults.elevatedCardColors(containerColor=if(ready) Color(0xFFE8F5E9) else GoldSoft),shape=RoundedCornerShape(18.dp)){Row(Modifier.padding(16.dp)){Icon(if(ready) Icons.Default.CheckCircle else Icons.Default.Info,null,tint=if(ready) Success else Warning);Spacer(Modifier.width(10.dp));Text(if(ready)"BOOKING READY • All required details prepared" else "Complete date and valid pilgrim details",fontWeight=FontWeight.Bold)}}
  Button(enabled=ready,onClick=openOfficial,modifier=Modifier.fillMaxWidth(),shape=RoundedCornerShape(14.dp)){Icon(Icons.Default.OpenInBrowser,null);Spacer(Modifier.width(8.dp));Text("Open Official TTD Booking",fontWeight=FontWeight.Bold)}
  Text("You will complete CAPTCHA/OTP, queue and payment on the official TTD flow.",style=MaterialTheme.typography.bodySmall)
 }}
@Composable fun Simple(title:String,body:String){Column(Modifier.padding(24.dp),verticalArrangement=Arrangement.spacedBy(12.dp)){Text(title,style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold);Text(body)}}