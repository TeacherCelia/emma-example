package com.example.emma_test_android

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
import io.emma.android.model.EMMAEventRequest
import io.emma.android.interfaces.EMMARequestListener
import io.emma.android.interfaces.EMMAUserInfoInterface
import org.json.JSONObject

class MainActivity : AppCompatActivity(), EMMAUserInfoInterface {

    // --- declaración de componentes ---
    //Session
    private lateinit var btnSession: Button
    private lateinit var txtSession: TextView

    //Notification permission
    private lateinit var btnNotificationPermission: Button
    private lateinit var txtNotificationPermission: TextView

    //Register user
    private lateinit var btnRegisterUser: Button
    private lateinit var txtInfoRegisterUser: TextView

    //Login user
    private lateinit var btnLoginUser: Button
    private lateinit var txtInfoLogin: TextView

    //Events and extras
    private lateinit var btnTrackEvent: Button
    private lateinit var btnAddUserTag: Button

    //Dialog de dos editText
    private lateinit var edtxtUser: EditText
    private lateinit var edtxtEmail: EditText

    //Orders and products (start, add y track order)
    private lateinit var txtInfoOrders: TextView
    private lateinit var btnStartOrder: Button
    private lateinit var btnAddProduct: Button
    private lateinit var btnTrackOrder: Button
    private lateinit var btnCancelOrder: Button

    //Language
    private lateinit var spnLanguage: Spinner
    private lateinit var btnLanguage: Button

    //Other EMMA Methods
    private lateinit var btnGetUserInfo: Button
    private lateinit var btnGetUserID: Button
    private lateinit var btnGetInstallAttribution: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("EMMA_SESSION", "Session active: ${EMMA.getInstance().isSdkStarted}")

        //--- Session
        btnSession = findViewById(R.id.btn_startSession)
        txtSession = findViewById(R.id.txt_dch_session)
        btnSession.setOnClickListener{
            ApplicationManager.startSession(this)
        }

        //--- Notifications permission
        txtNotificationPermission = findViewById(R.id.txt_2NotificationsPermission)
        btnNotificationPermission = findViewById(R.id.btn_RequestNotificationsPermission)
        btnNotificationPermission.setOnClickListener{
            EMMA.getInstance().requestNotificationPermission()
        }

        //--- Register user (muestra dialogo para registrar usuario)
        txtInfoRegisterUser = findViewById(R.id.txt_dch_RegisterUser)
        btnRegisterUser = findViewById(R.id.btn_RegisterUser)
        btnRegisterUser.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_twoedittext, null)
            edtxtUser = dialogView.findViewById(R.id.edtxt_user)
            edtxtEmail = dialogView.findViewById(R.id.edtxt_email)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Register User")
                .setView(dialogView)
                .setPositiveButton("Register") { dialog, _ ->
                    val userId = edtxtUser.text.toString()
                    val email = edtxtEmail.text.toString()

                    //EMMARegisterUser
                    EMMA.getInstance().registerUser(userId, email)

                    txtInfoRegisterUser.text = "User registered"
                    btnRegisterUser.isEnabled = false

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        //--- LogIn user
        btnLoginUser = findViewById(R.id.btn_LogInUser)
        txtInfoLogin = findViewById(R.id.txt_dch_LogInUser)
        btnLoginUser.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.dialog_twoedittext, null)

            val editTextUser = dialogView.findViewById<EditText>(R.id.edtxt_user)
            val editTextEmail = dialogView.findViewById<EditText>(R.id.edtxt_email)

            val builder = AlertDialog.Builder(this)
            builder.setTitle("LogIn User")
                .setView(dialogView)
                .setPositiveButton("LogIn") { dialog, _ ->
                    val userId = editTextUser.text.toString()
                    val email = editTextEmail.text.toString()


                    //EMMALoginUser
                    EMMA.getInstance().loginUser(userId, email)

                    txtInfoLogin.text = "User logged in"
                    btnLoginUser.isEnabled = false

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        //--- Events and extras
        btnTrackEvent = findViewById(R.id.btn_TrackEvent)
        btnAddUserTag = findViewById(R.id.btn_AddUserTagTag)

        btnTrackEvent.setOnClickListener{
            val eventRequest = EMMAEventRequest("4137f3a184bb5de977da43f90546b1f8") // token de evento_prueba
            //Optional: custom attributes
            eventRequest.attributes = mapOf("attribute_example" to "attribute_valor")
            //Optional: request status listener
            val requestListener = object : EMMARequestListener {
                override fun onStarted(requestId: String) {
                    Log.d("EMMA_request", "Request started with id: $requestId")
                }

                override fun onSuccess(requestId: String, result: Boolean) {
                    Log.d("EMMA_request", "Request $requestId succeeded: $result")
                }

                override fun onFailed(requestId: String) {
                    Log.e("EMMA_request", "Request $requestId failed")
                }
            }
            eventRequest.requestListener = requestListener
            //Optional: cumtom id for request delegate
            eventRequest.customId = "customID"

            EMMA.getInstance().trackEvent(eventRequest)
        }

        btnAddUserTag.setOnClickListener{
            val tags = ArrayMap<String, String>()
            tags["MY_CUSTOM_TAG"] = "MY_CUSTOM_VALUE"
            EMMA.getInstance().trackExtraUserInfo(tags)
        }

        //--- Orders and products
        txtInfoOrders = findViewById(R.id.txt_dch_OrdersAndProducts)
        btnStartOrder = findViewById(R.id.btn_StartOrder)
        btnAddProduct = findViewById(R.id.btn_AddProduct)
        btnTrackOrder = findViewById(R.id.btn_TrackOrder)
        btnCancelOrder = findViewById(R.id.btn_CancelOrder)

        btnAddProduct.isEnabled = false
        btnTrackOrder.isEnabled = false
        btnCancelOrder.isEnabled = false

        val extras = emptyMap<String, String>() // para el ejemplo creamos una variable vacía
        btnStartOrder.setOnClickListener{
            EMMA.getInstance().startOrder(
                "<ORDER_ID>",
                "<CUSTOMER_ID>",
                10.0F,
                extras
            )
            txtInfoOrders.text = "Order started"
            btnStartOrder.isEnabled = false
            btnAddProduct.isEnabled = true
        }

        btnAddProduct.setOnClickListener{
            EMMA.getInstance().addProduct(
                "PRODUCT_ID",
                "PRODUCT_NAME",
                1.0F,
                10.0F
            )
            txtInfoOrders.text = "Product added"
            btnAddProduct.isEnabled = false
            btnTrackOrder.isEnabled = true
        }

        btnTrackOrder.setOnClickListener{
            EMMA.getInstance().trackOrder()
            txtInfoOrders.text = "Tracking order"
            btnTrackOrder.isEnabled = false
            btnCancelOrder.isEnabled = true
        }

        btnCancelOrder.setOnClickListener{
            EMMA.getInstance().cancelOrder("<ORDER_ID>")

            txtInfoOrders.text = ""
            btnStartOrder.isEnabled = true
            btnCancelOrder.isEnabled = false
        }

        //--- Language
        spnLanguage = findViewById(R.id.spn_language)
        btnLanguage = findViewById(R.id.btn_SetLanguage)
        //Config del spinner
        val languages = resources.getStringArray(R.array.language_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnLanguage.adapter = adapter
        //Clic del boton
        btnLanguage.setOnClickListener {
            val selectedLanguage = spnLanguage.selectedItem.toString()
            val languageCode = when (selectedLanguage) {
                "Spanish" -> "es"
                "English" -> "en"
                "French"  -> "fr"
                "German"  -> "de"
                else -> "en"
            }

            //!!!! No existe 'setUserLanguage' !!!!
            //EMMA.getInstance().setUserLanguage(languageCode)
        }

        //--- Otras comprobaciones

        //Para comprobar cuando se abre la app desde la notificación
        EMMA.getInstance().checkForRichPushUrl()

        //Para obtener la info del perfil de usuario en la app (getUserInfo)
        btnGetUserInfo = findViewById(R.id.btn_getUserInfo)
        btnGetUserInfo.setOnClickListener{
            EMMA.getInstance().getUserInfo()
        }

        //Para obtener el ID de usuario (getUserID)
        btnGetUserID = findViewById(R.id.btn_getUserID)
        btnGetUserID.setOnClickListener{
            EMMA.getInstance().getUserID()
        }

        //Para obtener info de atribución de la instalación (getInstallAttributionInfo)
        btnGetInstallAttribution = findViewById(R.id.btn_getInstallAttributionInfo)
        btnGetInstallAttribution.setOnClickListener{
            EMMA.getInstance().getInstallAttributionInfo { attribution ->
                if (attribution != null) {
                    Log.d("EMMA_Attribution", "Attribution Info: ${attribution}")
                } else {
                    Log.d("EMMA_Attribution", "No attribution data available.")
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

        //Session
        if (EMMA.getInstance().isSdkStarted){
            btnSession.isEnabled = false
            txtSession.text = "Session Started"
        } else {
            btnSession.isEnabled = true
            txtSession.text = "No session"
        }

        //NotificationPermission: cambio de texto
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED) {
            txtNotificationPermission.text = "Permission status: GRANTED"
        } else {
            txtNotificationPermission.text = "Permission status: DENIED"
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        EMMA.getInstance().onNewNotification(intent,true)
    }

    //metodos de EMMAUserInfoInterface
    override fun OnGetUserInfo(userInfo: JSONObject?) {
        userInfo?.let {
            Log.d("USER_DATA", "User data: $it")

        }
    }

    override fun OnGetUserID(id: Int) {
        Log.d("USER_ID", "User ID: $id")
    }
}