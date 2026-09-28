package com.manitnjg.tirupatidarshan.data
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
class PilgrimStore(context:Context){
 private val prefs=context.getSharedPreferences("pilgrim_profiles",Context.MODE_PRIVATE)
 fun all():List<Pilgrim>{val a=JSONArray(prefs.getString("items","[]"));return (0 until a.length()).map{a.getJSONObject(it)}.map{Pilgrim(it.getLong("id"),it.getString("name"),it.getInt("age"),it.getString("gender"),it.getString("idType"),it.getString("idNumber"),it.optString("mobile"))}}
 fun save(p:Pilgrim){val items=all().filterNot{it.id==p.id}+p;val a=JSONArray();items.forEach{a.put(JSONObject().put("id",it.id).put("name",it.name).put("age",it.age).put("gender",it.gender).put("idType",it.idType).put("idNumber",it.idNumber).put("mobile",it.mobile))};prefs.edit().putString("items",a.toString()).apply()}
 fun delete(id:Long){val items=all().filterNot{it.id==id};val a=JSONArray();items.forEach{a.put(JSONObject().put("id",it.id).put("name",it.name).put("age",it.age).put("gender",it.gender).put("idType",it.idType).put("idNumber",it.idNumber).put("mobile",it.mobile))};prefs.edit().putString("items",a.toString()).apply()}
}