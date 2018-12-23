package ng.agape.app.agape.ui.activities

import android.os.Bundle
import ng.agape.app.agape.R
import ng.agape.app.agape.models.Account
import ng.agape.app.agape.ui.base.BaseActivity
import ng.agape.app.agape.ui.fragments.IntroFragment
import ng.agape.app.agape.ui.fragments.SplashFragment

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_activity_entry)

        val account = Account.currentAccount()
        if (account == null) {

            val manager = supportFragmentManager
            manager.beginTransaction().replace(R.id.fragment_container_entry_activity,
                    IntroFragment.newInstance()).commit()
        }else {

            val manager = supportFragmentManager
            manager.beginTransaction().replace(R.id.fragment_container_entry_activity,
                    SplashFragment.newInstance()).commit()
        }
    }
}