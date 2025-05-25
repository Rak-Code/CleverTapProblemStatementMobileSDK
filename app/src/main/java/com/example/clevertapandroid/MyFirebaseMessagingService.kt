import android.os.Bundle
import android.util.Log
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Message received from: ${remoteMessage.from}")
        Log.d("FCM", "Message data: ${remoteMessage.data}")

        CleverTapAPI.getDefaultInstance(applicationContext)
            ?.pushEvent("FCM Message Received", remoteMessage.data as Map<String, Any>)

        try {
            val bundleData = remoteMessage.data.toBundle()
            CleverTapAPI.createNotification(applicationContext, bundleData)
        } catch (e: Exception) {
            Log.e("FCM", "Error creating CleverTap notification", e)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")

        CleverTapAPI.getDefaultInstance(applicationContext)
            ?.pushFcmRegistrationId(token, true)
    }
}

// Extension function to convert Map<String, String> to Bundle
fun Map<String, String>.toBundle(): Bundle {
    val bundle = Bundle()
    for ((key, value) in this) {
        bundle.putString(key, value)
    }
    return bundle
}
