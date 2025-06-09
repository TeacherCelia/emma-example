package com.example.emma_test_android

import android.app.Application
import androidx.core.content.ContextCompat
import io.emma.android.EMMA
import io.emma.android.model.EMMAPushOptions


class ApplicationTest : Application() {
    override fun onCreate() {
        super.onCreate()

        val configuration = EMMA.Configuration.Builder(this)
            .setSessionKey("3DBF55A0B7BC550874edfbac6d5dc49f8")
            .setDebugActive(BuildConfig.DEBUG)
            .build()
        // añadir .trackScreenEvents(false) para desactivar el envío de pantallas
        // añadir .setShortPowlinkDomains si se usa un tracker que no es dominio de emma
        // añadir .setPowlinkDomains ...

        EMMA.getInstance().startSession(configuration)

        // --- inicio del sistema de PUSH
        val pushOpt = EMMAPushOptions.Builder(MainActivity::class.java, R.drawable.notification_icon) // lanzar la activity que se quiera abrir desde el push
            .setNotificationColor(ContextCompat.getColor(this, R.color.yellow))
            .setNotificationChannelName("Mi custom channel")
            .build()

        EMMA.getInstance().startPushSystem(pushOpt);

    }
}