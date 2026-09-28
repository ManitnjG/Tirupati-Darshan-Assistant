package com.manitnjg.tirupatidarshan.data
import android.content.Context
class BookingPreferences(context:Context){
 private val prefs=context.getSharedPreferences("booking_prep",Context.MODE_PRIVATE)
 var preferredDate:String get()=prefs.getString("preferred_date","")?:"" set(v){prefs.edit().putString("preferred_date",v).apply()}
 var selectedPilgrimIds:Set<String> get()=prefs.getStringSet("pilgrims",emptySet())?:emptySet() set(v){prefs.edit().putStringSet("pilgrims",v).apply()}
}