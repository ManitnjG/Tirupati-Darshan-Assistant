package com.manitnjg.tirupatidarshan.security
object SensitiveData {
 fun maskId(value:String):String {
  val clean=value.filter{it.isLetterOrDigit()}
  if(clean.length<=4)return "••••"
  return "•".repeat(clean.length-4)+clean.takeLast(4)
 }
 fun safeForLog(value:String?)=if(value.isNullOrBlank()) "empty" else "[REDACTED]"
}