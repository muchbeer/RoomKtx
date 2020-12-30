package raum.muchbeer.roomktx.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import raum.muchbeer.roomktx.AlbumActivity
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FilterWorker(context: Context, params : WorkerParameters) : Worker(context, params) {

    companion object {
        const val KEY_UPLOAD_DATA = "data_out"
    }

    override fun doWork(): Result {

     //   val count = inputData.getInt(AlbumActivity.KEY_WORKER_DATA, 0)
        try {
            for (i in 0 until 300) {
                Log.d("UploadWorkerForLoop", "Looping for is: ${i}")
            }


            return Result.success()
        }catch (e: Exception) {
            return Result.failure()
        }
    }
}