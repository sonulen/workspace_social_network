package com.redmadrobot.basetest

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor

@SuppressLint("RestrictedApi")
object TestLiveDataExecutionController {
    fun enableTestMode() {
        ArchTaskExecutor.getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) {
                    runnable.run()
                }

                override fun postToMainThread(runnable: Runnable) {
                    runnable.run()
                }

                override fun isMainThread(): Boolean = true
            })
    }
    fun disableTestMode() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}
