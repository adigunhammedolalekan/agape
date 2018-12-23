package ng.agape.app.agape

import android.app.Application
import android.content.pm.PackageManager
import android.util.Base64
import com.facebook.FacebookSdk
import ng.agape.app.agape.utils.L
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class AgapeApplication : Application() {

    companion object {

        public var application: AgapeApplication? = null
    }
    override fun onCreate() {
        super.onCreate()

        application = this
        initFonts()
    }

    private fun initFonts() {

        CalligraphyConfig.initDefault(
                CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Lato-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build())
    }

    private fun printHashKey() {
        try {
            val info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))

                L.fine("Hash => $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: Exception) {
        }

    }
}