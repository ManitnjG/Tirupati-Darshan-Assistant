package com.manitnjg.tirupatidarshan.domain
import java.time.Instant
data class Provenance(val sourceUrl:String,val retrievedAt:Instant,val verifiedAt:Instant?,val dataType:DataType)
data class OfficialStatus(val open:Boolean,val provenance:Provenance)
object Reliability {
 fun isOfficial(status:OfficialStatus)=status.provenance.dataType==DataType.OFFICIAL && status.provenance.verifiedAt!=null
 fun canShowConfirmed(reference:String?,state:BookingState)=state==BookingState.CONFIRMED&&!reference.isNullOrBlank()
}