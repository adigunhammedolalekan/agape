package ng.agape.app.agape.net.social.fb;

/**
 * Created by multidots on 6/16/2016.
 */
public interface FacebookResponse {
    void onFbSignInFail();

    void onFbSignInSuccess();

    void onFbProfileReceived(FacebookUser facebookUser);

    void onFBSignOut();
}
