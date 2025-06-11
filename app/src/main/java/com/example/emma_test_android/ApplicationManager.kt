package com.example.emma_test_android

import android.content.Context
import io.emma.android.EMMA

object ApplicationManager {

    // --- estado ---

    //Session
    var sessionStarted = false
    fun startSession(context: Context) {
        val configuration = EMMA.Configuration.Builder(context)
            .setSessionKey("3DBF55A0B7BC550874edfbac6d5dc49f8")
            .setDebugActive(BuildConfig.DEBUG)
            .build()
        // añadir .trackScreenEvents(false) para desactivar el envío de pantallas
        // añadir .setShortPowlinkDomains si se usa un tracker que no es dominio de emma
        // añadir .setPowlinkDomains ...

        EMMA.getInstance().startSession(configuration)
        sessionStarted = true
    }



    // deeplink
    //TODO: configurar textos del deeplink


    // --- permisos



    // --- pedidos



    // --- tracking
}