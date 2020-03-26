package com.raj.workmanager


import android.os.Bundle

import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = Data.Builder()
            .putString(KEY_TASK_DESC, "Hey I am sending the work data")
            .build()
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()
        val request = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(request)



        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {

                    val data = workInfo.outputData

                   // displayMessage("Work finished!")
                }
            })

    }

    companion object {
        const val KEY_TASK_DESC = "key_task_desc"
    }
}