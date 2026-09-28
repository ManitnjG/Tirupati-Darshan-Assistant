package com.manitnjg.tirupatidarshan.domain
enum class DataType { OFFICIAL, DERIVED, PREDICTED }
enum class Confidence { LOW, MEDIUM, HIGH }
enum class Demand { LOW, MODERATE, HIGH, VERY_HIGH }
enum class BookingState { PREPARING,PREDICTED,OFFICIALLY_ANNOUNCED,WATCHING,OFFICIALLY_OPEN,USER_BOOKING,PAYMENT_PENDING,CONFIRMATION_PENDING,CONFIRMED,FAILED,CANCELLED }
data class ReleaseObservation(val dayOfMonth:Int,val hour:Int,val daysBeforeDarshan:Int,val verified:Boolean)
data class ReleasePrediction(val dayOfMonth:Int?,val hour:Int?,val confidence:Confidence,val demand:Demand,val explanation:String)