package com.example.testapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.testapp.ui.theme.TestAppTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.util.Locale


class MainActivity : ComponentActivity() {

    private val sharedPref = MySharedPref(this)
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    private val networkResult = mutableStateOf("")

    private fun checkIsEmu(): Boolean {
        if (BuildConfig.DEBUG) return false
        val phoneModel = Build.MODEL
        val buildProduct = Build.PRODUCT
        val buildHardware = Build.HARDWARE
        var result = (
                Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware == "goldfish"
                || Build.BRAND.contains("google")
                || buildHardware == "vbox86"
                || buildProduct == "sdk"
                || buildProduct == "google_sdk"
                || buildProduct == "sdk_x86"
                || buildProduct == "vbox86p"
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
                || buildHardware.lowercase(Locale.getDefault()).contains("nox")
                || buildProduct.lowercase(Locale.getDefault()).contains("nox")
                )
        if (result) return true
        result = result or (Build.BRAND.startsWith("generic") &&
                Build.DEVICE.startsWith("generic"))
        if (result) return true
        result = ("google_sdk" == buildProduct.lowercase())
        return result
    }

    private fun getValueFromFirebaseConfig(){
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(this){
            if(it.isSuccessful){
                networkResult.value = firebaseRemoteConfig.getString("url")
            }
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val netResultCompose = rememberSaveable{ networkResult }
                    if (sharedPref.getURL() == "") {
                        Log.d("LOL", "Локальной ссылки нет")

                        firebaseRemoteConfig = Firebase.remoteConfig
                        val configSettings = remoteConfigSettings {
                            minimumFetchIntervalInSeconds = 60
                        }
                        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
                        firebaseRemoteConfig.setDefaultsAsync(R.xml.url)

                        getValueFromFirebaseConfig()

                        when(netResultCompose.value == "" || checkIsEmu()){
                           true -> {
                               Log.d("LOL", "Проверка не пройдена: ресультат: ${netResultCompose.value == ""}. Emu: ${checkIsEmu()}")
                               NewsListSample(items = News)}
                           false -> {
                               Log.d("LOL", "Проверка пройдена")
                               sharedPref.saveURL(networkResult.value)
                               MyWebView(
                                   //savedInstanceState = savedInstanceState,
                                   url = sharedPref.getURL())
                           }
                        }
                    }
                    else{
                        Log.d("LOL", "Локальная ссылка есть ${sharedPref.getURL()}")
                        if (checkForInternet(this)){
                            Log.d("LOL", "Интернет есть")
                            MyWebView(
                                url = sharedPref.getURL())
                        }
                        else{
                            Log.d("LOL", "Интернета нет")
                            ErrorScreenSample()
                        }
                    }
                }
            }
        }
    }
}