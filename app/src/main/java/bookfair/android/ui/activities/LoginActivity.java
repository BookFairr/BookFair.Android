package bookfair.android.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();


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
    FancyButton loginBtn;
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

        //Reset errors.
        loginEmailEditText.setError(null);
        loginPasswordEditText.setError(null);

        boolean cancel = false;
        View focusView = null;

        //Check for a valid email.
        if (TextUtils.isEmpty(loginEmailEditText.getText().toString())) {
            loginEmailEditText.setError(getString(R.string.error_field_required));
            focusView = loginEmailEditText;
            cancel = true;
        }

        //Check for a valid password.
        if (TextUtils.isEmpty(loginPasswordEditText.getText().toString())) {
            loginPasswordEditText.setError(getString(R.string.error_field_required));
            focusView = loginEmailEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            logInGo();
        }
    }

    private void logInGo() {

        Call<LogInResult> resultCall = bookFairApiServiceLazy.get().attemptLogIn(loginEmailEditText.getText().toString(), loginPasswordEditText.getText().toString());

        resultCall.enqueue(new Callback<LogInResult>() {
            @Override
            public void onResponse(Call<LogInResult> call, Response<LogInResult> response) {
                if (response.body().isSuccess()) {
                    preferenceManager.setLoggedInStatus(true);
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                } else {
                    createSnackbar(LoginActivity.this, loginCoordinator, response.body().getErrorMessage()).show();

                }
            }

            @Override
            public void onFailure(Call<LogInResult> call, Throwable t) {
                // Log error here if request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void facebookLogin() {

    }
}
