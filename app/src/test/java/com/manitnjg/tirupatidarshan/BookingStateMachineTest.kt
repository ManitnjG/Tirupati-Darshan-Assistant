package com.manitnjg.tirupatidarshan
import com.manitnjg.tirupatidarshan.domain.*
import org.junit.Assert.*
import org.junit.Test
class BookingStateMachineTest{
 @Test fun paymentCannotBecomeConfirmedDirectly(){assertFalse(BookingStateMachine.canTransition(BookingState.PAYMENT_PENDING,BookingState.CONFIRMED))}
 @Test fun confirmationPendingCanBecomeConfirmed(){assertTrue(BookingStateMachine.canTransition(BookingState.CONFIRMATION_PENDING,BookingState.CONFIRMED))}
}