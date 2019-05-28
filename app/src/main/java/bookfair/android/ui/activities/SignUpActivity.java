package bookfair.android.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.net.UnknownHostException;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.api.BookFairApiService;
import bookfair.android.api.models.SignUpResult;
import bookfair.android.core.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static bookfair.android.ui.views.SnackBarFactory.createSnackbar;
import static bookfair.android.util.Util.isEmailValid;
import static bookfair.android.util.Util.passwordStrength;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private boolean signUpErrorOccured;


    @Inject
    PreferenceManager preferenceManager;
    @Inject
    Lazy<BookFairApiService> bookFairApiServiceLazy;

    @BindView(R.id.facebook_login_btn)
    FancyButton facebookLoginBtn;
    @BindView(R.id.email_editText)
    TextInputEditText emailEditText;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.fullname_editTex)
    TextInputEditText fullNameEditTex;
    @BindView(R.id.fullname_layout)
    TextInputLayout fullnameLayout;
    @BindView(R.id.username_editText)
    TextInputEditText usernameEditText;
    @BindView(R.id.username_layout)
    TextInputLayout usernameLayout;
    @BindView(R.id.login_password_editText)
    TextInputEditText passwordEditText;
    @BindView(R.id.login_password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.signup_btn)
    SubmitButton signupBtn;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.sign_up_coordinator)
    CoordinatorLayout signUpCoordinator;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        applicationComponent(this).inject(SignUpActivity.this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });
    }

    private void facebookLogin() {

    }

    private void attemptSignUp() {

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            emailEditText.setError(getString(R.string.error_field_required));
            signupBtn.reset();
            return;
        }

        if (!isEmailValid(emailEditText.getText().toString())) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            signupBtn.reset();
            return;
        }

        // Check for a valid name.
        if (TextUtils.isEmpty(fullNameEditTex.getText().toString())) {
            fullNameEditTex.setError(getString(R.string.error_field_required));
            signupBtn.reset();
            return;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(usernameEditText.getText().toString())) {
            usernameEditText.setError(getString(R.string.error_field_required));
            signupBtn.reset();
            return;
        }

        //Check for a valid password.
        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError(getString(R.string.error_field_required));
            signupBtn.reset();
            return;
        }

        if (!passwordStrength(passwordEditText.getText().toString())) {
            passwordEditText.setError(getString(R.string.password_strength));
            signupBtn.reset();
            return;
        }


        Call<SignUpResult> resultCall = bookFairApiServiceLazy.get().attemptSignUp(emailEditText.getText().toString(), fullNameEditTex.getText().toString(),
                usernameEditText.getText().toString(), passwordEditText.getText().toString());

        resultCall.enqueue(new Callback<SignUpResult>() {
            @Override
            public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {

                        // Set Logged In status to 'true'
                        preferenceManager.setLoggedInStatus(getApplicationContext(), true);

                        preferenceManager.setUserFullName(response.body().getUserProfile().getFullname());
                        preferenceManager.setUsername(response.body().getUserProfile().getUsername());


                        signupBtn.doResult(true);
                        createSnackbar(SignUpActivity.this, signUpCoordinator, "Getting things ready..." ).show();

                        new Handler().postDelayed(() -> {
                            goToMainActivity();
                        }, 2000);

                    }
                    else
                    {

                    createSnackbar(SignUpActivity.this, signUpCoordinator, response.body().getErrorMessage()).show();

                    signupBtn.doResult(false);
                    new Handler().postDelayed(() -> signupBtn.reset(), 2000);
                    signUpErrorOccured = true;
                }
            }
        }

            @Override
            public void onFailure(Call<SignUpResult> call, Throwable t) {
                if (t instanceof UnknownHostException) {
                }else {

                    createSnackbar(SignUpActivity.this, signUpCoordinator, "Oops! Network error occurred. Try Again").show();
                    signupBtn.doResult(false);
                    new Handler().postDelayed(() -> signupBtn.reset(), 2000);

                }
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}


