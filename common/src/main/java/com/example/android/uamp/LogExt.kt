package com.example.android.uamp

import android.util.Log
import com.example.android.uamp.media.BuildConfig

const val TAG = "uamp_log"

var logEnable = BuildConfig.DEBUG

private enum class LEVEL {
    V, D, I, W, E
}

fun String.logv(tag: String = TAG) =
    log(LEVEL.V, tag, this)

fun String.logd(tag: String = TAG) =
    log(LEVEL.D, tag, this)

fun String.logi(tag: String = TAG) =
    log(LEVEL.I, tag, this)

fun String.logw(tag: String = TAG) =
    log(LEVEL.W, tag, this)

fun String.loge(tag: String = TAG) =
    log(LEVEL.E, tag, this)

private fun log(level: LEVEL, tag: String, msg: String) {
    if (!logEnable) return
    val message = createMessage(msg)
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}

private fun createMessage(msg: String): String {
    val functionName = getFunctionName()
    return if (functionName == null) msg else "$functionName -> $msg"
}

private fun getFunctionName(): String? {
    val stackTraceArray = Thread.currentThread().stackTrace ?: return null
    for (stackTrace in stackTraceArray) {
        if (stackTrace.isNativeMethod) {
            continue
        }
        if (stackTrace.className == Thread::class.java.name || stackTrace.className.contains("LogExt")) {
            continue
        }
        return "[${Thread.currentThread().name}(${Thread.currentThread().id}): ${stackTrace.fileName}:${stackTrace.lineNumber}]"
    }
    return null
}