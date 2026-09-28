package com.manitnjg.tirupatidarshan
import com.manitnjg.tirupatidarshan.domain.*
import org.junit.Assert.*
import org.junit.Test
class ReleasePredictorTest{
 @Test fun refusesToInventWithInsufficientHistory(){val p=ReleasePredictor.predict(listOf(ReleaseObservation(24,10,90,true)));assertNull(p.dayOfMonth);assertEquals(Confidence.LOW,p.confidence)}
 @Test fun ignoresUnverifiedObservations(){val p=ReleasePredictor.predict(listOf(ReleaseObservation(24,10,90,false),ReleaseObservation(25,10,90,false),ReleaseObservation(26,10,90,false)));assertNull(p.dayOfMonth)}
}