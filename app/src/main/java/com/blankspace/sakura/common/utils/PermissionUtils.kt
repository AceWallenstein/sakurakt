package com.blankspace.sakura.common.utils

import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first


suspend fun ActivityResultCaller.permission(permission: String): Boolean {
    return callbackFlow {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            this.trySend(isGranted)
            close()
        }.launch(permission)
        awaitClose()
    }.first()
}

