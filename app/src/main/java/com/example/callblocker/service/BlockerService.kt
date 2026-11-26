package com.example.callblocker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.core.app.NotificationCompat
import com.example.callblocker.models.PrefixRepository
import kotlin.jvm.java

class BlockerService : CallScreeningService() {
    override fun onScreenCall(callDetails: Call.Details) {
        val repository = PrefixRepository(this)
        val number = callDetails.handle?.schemeSpecificPart


        val shouldBlock = callDetails.callDirection == Call.Details.DIRECTION_INCOMING &&
                repository.isEnabled &&
                number != null &&
                repository.isBlocked(number)

        respondToCall(callDetails, buildResponse(shouldBlock))
        if (shouldBlock) BlockedCallNotifier(this).notify(number)
    }

    private fun buildResponse(block: Boolean): CallResponse =
        CallResponse.Builder().apply {
            if (block) {
                setDisallowCall(true)
                setRejectCall(true)
                setSkipNotification(true)
            }
        }.build()

    private companion object {
        const val TAG = "CallBlocker"
    }
}