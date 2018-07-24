package bookfair.android.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.unstoppable.submitbuttonview.SubmitButton;

import java.net.UnknownHostException;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.api.BookFairApiService;
import bookfair.android.api.models.LogInResult;
import bookfair.android.core.PreferenceManager;
import bookfair.android.db.BookFairRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bookfair.android.ui.views.SnackBarFactory.createSnackbar;
import static bookfair.android.util.Util.isEmailValid;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private boolean loginErrorOccured;

    @Inject
    PreferenceManager preferenceManager;
    @Inject
    Lazy<BookFairApiService> bookFairApiServiceLazy;
    @Inject
    BookFairRepository bookFairRepository;

    @BindView(R.id.login_email_editText)
    TextInputEditText loginEmailEditText;
    @BindView(R.id.login_email_layout)
    TextInputLayout loginEmailLayout;
    @BindView(R.id.login_password_editText)
    TextInputEditText loginPasswordEditText;
    @BindView(R.id.login_password_layout)
    TextInputLayout loginPasswordLayout;
    @BindView(R.id.facebook_login_btn)
    FancyButton facebookLoginBtn;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.login_btn)
    SubmitButton loginBtn;
    @BindView(R.id.logo)
    AppCompatImageView logo;
    @BindView(R.id.login_coordinator)
    CoordinatorLayout loginCoordinator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        applicationComponent(this).inject(LoginActivity.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });
    }

    private void attemptLogin() {

        //Check for a valid email.
        if (TextUtils.isEmpty(loginEmailEditText.getText().toString())) {
            loginEmailEditText.setError(getString(R.string.error_field_required));
            loginBtn.reset();
            return;
        }

        if (!isEmailValid(loginEmailEditText.getText().toString())) {
            loginEmailEditText.setError(getString(R.string.error_invalid_email));
            loginBtn.reset();
            return;
        }

        //Check for a valid password.
        if (TextUtils.isEmpty(loginPasswordEditText.getText().toString())) {
            loginPasswordEditText.setError(getString(R.string.error_field_required));
            loginBtn.reset();
            return;
        }


        Call<LogInResult> resultCall = bookFairApiServiceLazy.get().attemptLogIn(loginEmailEditText.getText().toString(), loginPasswordEditText.getText().toString());

        resultCall.enqueue(new Callback<LogInResult>() {
            @Override
            public void onResponse(Call<LogInResult> call, Response<LogInResult> response) {

                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {

                        // Set Logged In status to 'true'
                        preferenceManager.setLoggedInStatus(getApplicationContext(), true);
                        bookFairRepository.saveUserProfile(response.body().getProfile());

                        loginBtn.doResult(true);

                        new Handler().postDelayed(() -> {
                            goToMainActivity();
                        }, 2000);

                    } else {
                        createSnackbar(LoginActivity.this, loginCoordinator, response.body().getErrorMessage() ).show();

                        loginBtn.doResult(false);
                        new Handler().postDelayed(() -> loginBtn.reset(), 2000);
                        loginErrorOccured = true;

                    }

                }
            }

            @Override
            public  void onFailure(Call<LogInResult> call, Throwable t) {
                if (t instanceof UnknownHostException) {
                }else {
                    createSnackbar(LoginActivity.this, loginCoordinator, "Oops! Check your internet connection.").show();
                    loginBtn.doResult(false);
                    new Handler().postDelayed(() -> loginBtn.reset(), 2000);
                }
            }
        });
    }

    private void facebookLogin() {

    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
