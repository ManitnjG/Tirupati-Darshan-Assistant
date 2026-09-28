package com.manitnjg.tirupatidarshan.domain
object BookingStateMachine {
 private val allowed=mapOf(
  BookingState.PREPARING to setOf(BookingState.PREDICTED,BookingState.WATCHING,BookingState.CANCELLED),
  BookingState.PREDICTED to setOf(BookingState.WATCHING,BookingState.OFFICIALLY_ANNOUNCED,BookingState.CANCELLED),
  BookingState.WATCHING to setOf(BookingState.OFFICIALLY_ANNOUNCED,BookingState.OFFICIALLY_OPEN,BookingState.CANCELLED),
  BookingState.OFFICIALLY_ANNOUNCED to setOf(BookingState.OFFICIALLY_OPEN,BookingState.WATCHING),
  BookingState.OFFICIALLY_OPEN to setOf(BookingState.USER_BOOKING,BookingState.CANCELLED),
  BookingState.USER_BOOKING to setOf(BookingState.PAYMENT_PENDING,BookingState.FAILED,BookingState.CANCELLED),
  BookingState.PAYMENT_PENDING to setOf(BookingState.CONFIRMATION_PENDING,BookingState.FAILED),
  BookingState.CONFIRMATION_PENDING to setOf(BookingState.CONFIRMED,BookingState.FAILED),
  BookingState.FAILED to setOf(BookingState.USER_BOOKING,BookingState.CANCELLED)
 )
 fun canTransition(from:BookingState,to:BookingState)=allowed[from]?.contains(to)==true
}