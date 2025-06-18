package com.example.emma_test_android.managers

import android.util.Log
import io.emma.android.EMMA
import io.emma.android.enums.CommunicationTypes
import io.emma.android.interfaces.EMMABatchNativeAdInterface
import io.emma.android.interfaces.EMMADeviceIdListener
import io.emma.android.interfaces.EMMAInAppMessageInterface
import io.emma.android.interfaces.EMMANativeAdInterface
import io.emma.android.interfaces.EMMANotificationInterface
import io.emma.android.model.EMMACampaign
import io.emma.android.model.EMMANativeAd
import io.emma.android.model.EMMAPushCampaign

/**
 * Clase que hará de Callback manager de las diferentes interfaces de EMMA con MainActivity
 */

class EmmaCallbackManager:
    EMMADeviceIdListener,
    EMMAInAppMessageInterface,
    EMMANativeAdInterface,
    EMMABatchNativeAdInterface {

    // --- EMMADeviceIdListener ---
    override fun onObtained(deviceId: String?) {
        Log.d("EMMA_DEVICE", "DeviceId: $deviceId")
    }

    // --- EMMAInAppMessageInterface ---
    override fun onShown(campaign: EMMACampaign?) {
        Log.d("EMMA_InApp", "Mensaje mostrado")
    }

    override fun onHide(campaign: EMMACampaign?) {
        Log.d("EMMA_InApp", "Mensaje ocultado")
    }

    override fun onClose(campaign: EMMACampaign?) {
        Log.d("EMMA_InApp", "Mensaje cerrado")
    }

    // --- EMMANativeAdInterface ---
    override fun onReceived(nativeAd: EMMANativeAd) {
        val content = nativeAd.nativeAdContent
        val title = content["Title"]?.fieldValue
        Log.d("EMMA_NativeAd", "Anuncio recibido: $title")
        EMMA.getInstance().sendInAppImpression(CommunicationTypes.NATIVE_AD, nativeAd)
    }

    // --- EMMABatchNativeAdInterface ---
    override fun onBatchReceived(nativeAds: MutableList<EMMANativeAd>) {
        Log.d("EMMA_NativeAdBatch", "Recibidos ${nativeAds.size} anuncios")
        nativeAds.forEach {
            EMMA.getInstance().sendInAppImpression(CommunicationTypes.NATIVE_AD, it)
        }
    }
}