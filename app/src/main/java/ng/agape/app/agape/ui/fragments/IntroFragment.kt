package ng.agape.app.agape.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.layout_fragment_intro.*
import ng.agape.app.agape.R
import ng.agape.app.agape.models.Account
import ng.agape.app.agape.net.social.fb.FacebookHelper
import ng.agape.app.agape.net.social.fb.FacebookResponse
import ng.agape.app.agape.net.social.fb.FacebookUser
import ng.agape.app.agape.net.social.google.GoogleAuthResponse
import ng.agape.app.agape.net.social.google.GoogleAuthUser
import ng.agape.app.agape.net.social.google.GoogleSignInHelper
import ng.agape.app.agape.utils.L

class IntroFragment : Fragment() {

    private var mGoogleSignIn: GoogleSignInHelper? = null
    private var mFacebookSign: FacebookHelper?= null

    companion object {

        fun newInstance() = IntroFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGoogleSignIn = GoogleSignInHelper(activity,
                getString(R.string.GOOGLE_CLIENT_ID), googleAuthResponse)
        mFacebookSign = FacebookHelper(facebookSignResponse, "id,email,name,picture", activity!!)

        btn_continue_with_facebook.setOnClickListener {

            mFacebookSign?.performSignIn(this)
        }

        btn_continue_with_google.setOnClickListener {

            mGoogleSignIn?.performSignIn(this)
        }
    }

    val googleAuthResponse = object: GoogleAuthResponse {

        override fun onGoogleAuthSignIn(user: GoogleAuthUser?) {

            L.fine("Google Token => " + user?.idToken)
            val account = Account()
            account.token = user?.idToken!!
            account.persist()
        }

        override fun onGoogleAuthSignInFailed() {

        }

        override fun onGoogleAuthSignOut(isSuccess: Boolean) {}
    }

    var facebookSignResponse = object: FacebookResponse {

        override fun onFbProfileReceived(facebookUser: FacebookUser?) {

        }

        override fun onFBSignOut() {}

        override fun onFbSignInFail() {

        }

        override fun onFbSignInSuccess() {

            val accessToken = AccessToken.getCurrentAccessToken().token
            L.fine("Fb Token => " + accessToken)

            val account = Account()
            account.token = accessToken
            account.persist()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mGoogleSignIn?.onActivityResult(requestCode, resultCode, data)
        mFacebookSign?.onActivityResult(requestCode, resultCode, data)
    }
}