package ng.agape.app.agape.models

import com.google.gson.Gson
import ng.agape.app.agape.app.DependencyManager

class Account {

    public var email: String = ""
    public var name: String = ""
    public var token: String = ""

    public fun persist() {

        DependencyManager.manager().preferences().edit().let {

            if (token != "") {
                it.putString(TOKEN_KEY, token)
            }
            it.putString(ACCOUNT_DATA_KEY, JSON())

            it.apply()
        }
    }

    private fun JSON() = Gson().toJson(this)
    companion object {

        const val TOKEN_KEY = "TokenKey"
        const val ACCOUNT_DATA_KEY = "AccountDataKey"
        fun currentAccount(): Account? {

            val json = DependencyManager.manager().preferences().getString(ACCOUNT_DATA_KEY, "")
            if (json == "") {
                return null
            }

            return Gson().fromJson(json, Account::class.java)
        }
    }
}