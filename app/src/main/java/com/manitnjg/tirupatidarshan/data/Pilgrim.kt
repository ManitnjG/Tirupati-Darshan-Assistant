package com.manitnjg.tirupatidarshan.data
data class Pilgrim(val id:Long=System.currentTimeMillis(),val name:String,val age:Int,val gender:String,val idType:String,val idNumber:String,val mobile:String=""){
 val ready:Boolean get()=name.isNotBlank()&&age in 1..120&&gender.isNotBlank()&&idType.isNotBlank()&&idNumber.length>=4
}