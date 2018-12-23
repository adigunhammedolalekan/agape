package ng.agape.app.agape.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.ArrayMap
import kotlinx.android.synthetic.main.layout_activity_main.*
import ng.agape.app.agape.R
import ng.agape.app.agape.ui.base.BaseActivity
import ng.agape.app.agape.ui.fragments.AccountFragment
import ng.agape.app.agape.ui.fragments.MessageHistoryFragment
import ng.agape.app.agape.ui.fragments.ScheduleListFragment
import ng.agape.app.agape.ui.views.SimpleBottomBar

class HomeActivity : BaseActivity() {

    private val fragmentManager = supportFragmentManager
    private val mPageCache: HashMap<String, Fragment> = HashMap<String, Fragment>()
    private var mLastSelectedTab = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)

        btn_add_schedule_home.setOnClickListener {
            startActivity(Intent(this, ScheduleSettingsActivity::class.java))
        }

        setupBottomBar()
        initPageCache()

    }

    private fun initPageCache() {

        mPageCache[AccountFragment.TAG] = AccountFragment.newInstance()
        mPageCache[MessageHistoryFragment.TAG] = MessageHistoryFragment.newInstance()
        mPageCache[ScheduleListFragment.TAG] = ScheduleListFragment.newInstance()
    }

    private fun setupBottomBar() {

        bottom_bar_main.mCallback = {action ->

            if (action != mLastSelectedTab) {

                mLastSelectedTab = action
                when(action) {
                    SimpleBottomBar.Action.SCHEDULE_ -> {
                        fragmentManager.beginTransaction().replace(R.id.fragment_container_main_activity,
                                mPageCache[ScheduleListFragment.TAG]).commit()
                    }
                    SimpleBottomBar.Action.HISTORY_ -> {
                        fragmentManager.beginTransaction().replace(R.id.fragment_container_main_activity,
                                mPageCache[MessageHistoryFragment.TAG]).commit()
                    }
                    SimpleBottomBar.Action.ACCOUNT_ -> {
                        fragmentManager.beginTransaction().replace(R.id.fragment_container_main_activity,
                                mPageCache[AccountFragment.TAG]).commit()
                    }else -> {}
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mPageCache.clear()
    }
}