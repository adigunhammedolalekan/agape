package ng.agape.app.agape.app

import android.content.SharedPreferences
import android.preference.PreferenceManager
import ng.agape.app.agape.AgapeApplication

class DependencyManager {

    private var mSharedPreferences: SharedPreferences? = null

    init {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(AgapeApplication.application?.applicationContext)
    }

    companion object {

        private var dependencyManager: DependencyManager? = null
        fun manager() : DependencyManager {

            if (dependencyManager == null) {
                dependencyManager = DependencyManager()
            }

            return dependencyManager!!
        }
    }

    public fun preferences() = mSharedPreferences!!
}