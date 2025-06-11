package com.example.emma_test_android

import android.app.Application
import androidx.core.content.ContextCompat
import io.emma.android.EMMA
import io.emma.android.model.EMMAPushOptions


class ApplicationTest : Application() {
    override fun onCreate() {
        super.onCreate()

        // --- llamada al startSession
        ApplicationManager.startSession(this)

        // --- inicio del sistema de PUSH
        val pushOpt = EMMAPushOptions.Builder(MainActivity::class.java, R.drawable.notification_icon) // lanzar la activity que se quiera abrir desde el push
            .setNotificationColor(ContextCompat.getColor(this, R.color.yellow))
            .setNotificationChannelName("Mi custom channel")
            .build()

        EMMA.getInstance().startPushSystem(pushOpt);

    }
}