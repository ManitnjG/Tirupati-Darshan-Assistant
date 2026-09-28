package com.manitnjg.tirupatidarshan.watch
import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
class ReleaseWatchWorker(appContext:Context,params:WorkerParameters):CoroutineWorker(appContext,params){
 override suspend fun doWork():Result {
  // Network collector is intentionally not fabricated. When a permitted official
  // source is configured, verify provenance before generating an OFFICIAL alert.
  return Result.success()
 }
 companion object{
  fun schedule(context:Context){
   val constraints=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
   val request=PeriodicWorkRequestBuilder<ReleaseWatchWorker>(1,TimeUnit.HOURS).setConstraints(constraints).setBackoffCriteria(BackoffPolicy.EXPONENTIAL,15,TimeUnit.MINUTES).build()
   WorkManager.getInstance(context).enqueueUniquePeriodicWork("ttd-release-watch",ExistingPeriodicWorkPolicy.UPDATE,request)
  }
 }
}