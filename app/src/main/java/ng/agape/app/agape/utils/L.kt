package ng.agape.app.agape.utils

import android.util.Log

public class L {

    companion object {

        val TAG = "Agape"
        fun fine(message: String) {
            Log.d(TAG, message)
        }
        fun error(e: Throwable) {
            Log.wtf(TAG, e)
        }
    }
}