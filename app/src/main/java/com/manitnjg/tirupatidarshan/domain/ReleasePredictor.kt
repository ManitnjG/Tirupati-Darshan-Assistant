package com.manitnjg.tirupatidarshan.domain
object ReleasePredictor {
 fun predict(history:List<ReleaseObservation>,weekend:Boolean=false,specialEvent:Boolean=false):ReleasePrediction {
  val verified=history.filter{it.verified}
  if(verified.size<3)return ReleasePrediction(null,null,Confidence.LOW,if(weekend)Demand.HIGH else Demand.MODERATE,"Not enough reliable data to predict.")
  val day=verified.map{it.dayOfMonth}.average().toInt().coerceIn(1,31)
  val hour=verified.map{it.hour}.average().toInt().coerceIn(0,23)
  val spread=verified.maxOf{it.dayOfMonth}-verified.minOf{it.dayOfMonth}
  val confidence=when{verified.size>=8&&spread<=2->Confidence.HIGH;verified.size>=5&&spread<=5->Confidence.MEDIUM;else->Confidence.LOW}
  val demand=when{specialEvent->Demand.VERY_HIGH;weekend->Demand.HIGH;else->Demand.MODERATE}
  return ReleasePrediction(day,hour,confidence,demand,"Derived from verified historical release observations.")
 }
}