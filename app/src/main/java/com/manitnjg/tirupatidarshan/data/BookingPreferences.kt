package com.manitnjg.tirupatidarshan.data
import android.content.Context
class BookingPreferences(context:Context){
 private val prefs=context.getSharedPreferences("booking_prep",Context.MODE_PRIVATE)
 var preferredDate:String
  get()=prefs.getString("preferred_date","") ?: ""
  set(value){prefs.edit().putString("preferred_date",value).apply()}
 var selectedPilgrimIds:Set<String>
  get()=prefs.getStringSet("pilgrims",emptySet()) ?: emptySet()
  set(value){prefs.edit().putStringSet("pilgrims",value).apply()}
}
