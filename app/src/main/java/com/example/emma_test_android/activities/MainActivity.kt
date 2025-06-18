package com.example.emma_test_android.activities

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import io.emma.android.EMMA
import android.Manifest
import android.content.Intent
import android.util.ArrayMap
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.example.emma_test_android.R
import com.example.emma_test_android.application.EmmaTestApplication
import com.example.emma_test_android.managers.EmmaCallbackManager
import com.example.emma_test_android.managers.EmmaEventManager
import io.emma.android.interfaces.EMMANotificationInterface
import io.emma.android.interfaces.EMMAUserInfoInterface
import io.emma.android.model.EMMAPushCampaign
import org.json.JSONObject


/**
 * Activity principal de la app.
 * Gestiona botones, configuracion inicial, etc...
 */

class MainActivity :
    AppCompatActivity(),
    EMMANotificationInterface,
    EMMAUserInfoInterface{

    // region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Solo si es la primera vez que se carga la actividad (no tras rotación, etc)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
    // endregion

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        EMMA.getInstance().onNewNotification(intent, true)
        // Si quieres notificar al fragment, puedes buscarlo y llamarle a un método
    }

    //EMMANotificationInterface
    override fun onPushOpen(pushCampaign: EMMAPushCampaign) {
        Log.d("EMMA_noti", "Notificación recibida")
    }

    //EMMAGetUserInfoInterface
    override fun OnGetUserInfo(userInfo: JSONObject?) {
        userInfo?.let {
            Log.d("USER_DATA", "User data: $it")
        }
    }

    override fun OnGetUserID(id: Int) {
        Log.d("USER_ID", "User ID: $id")
    }
}