package com.example.clevertapandroid

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI

class MainActivity : AppCompatActivity() {

    private var cleverTapDefaultInstance: CleverTapAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityLifecycleCallback.register(this.application)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG)
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(applicationContext)

        // Raise “Product Viewed” event
        val eventProps = hashMapOf<String, Any>(
            "Product Name" to "Demo Product",
            "Category" to "Test Category"
        )
        cleverTapDefaultInstance?.pushEvent("Product Viewed", eventProps)

        val nameField = findViewById<EditText>(R.id.name_input)
        val emailField = findViewById<EditText>(R.id.email_input)
        val loginButton = findViewById<Button>(R.id.login_button)
        val testEventButton = findViewById<Button>(R.id.test_event_button)

        loginButton.setOnClickListener {
            val name = nameField.text.toString()
            val email = emailField.text.toString()

            val profileUpdate = hashMapOf<String, Any>(
                "Name" to name,
                "Email" to email,
                "Identity" to email
            )
            cleverTapDefaultInstance?.onUserLogin(profileUpdate)
        }

        testEventButton.setOnClickListener {
            cleverTapDefaultInstance?.pushEvent("TEST")
        }
    }
}
