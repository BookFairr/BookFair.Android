package bookfair.android.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.api.BookFairApiService;
import bookfair.android.api.models.SignUpResult;
import bookfair.android.core.PreferenceManager;
import bookfair.android.db.BookFairRepository;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Callback;
import retrofit2.Response;

import static bookfair.android.util.Util.isEmailValid;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();


    @Inject
    PreferenceManager preferenceManager;
    @Inject
    Lazy<BookFairApiService> bookFairApiServiceLazy;
    @Inject
    BookFairRepository bookFairRepository;

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
    @BindView(R.id.password_editText)
    TextInputEditText passwordEditText;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.signup_btn)
    FancyButton signupBtn;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        applicationComponent(this).inject(SignUpActivity.this);

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

        // Reset errors.
        emailEditText.setError(null);
        fullNameEditTex.setError(null);
        usernameEditText.setError(null);
        passwordEditText.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            emailEditText.setError(getString(R.string.error_field_required));
            focusView = emailEditText;
            cancel = true;
        } else if (!isEmailValid(emailEditText.getText().toString())) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            focusView = emailEditText;
            cancel = true;
        }

        // Check for a valid name.
        if (TextUtils.isEmpty(fullNameEditTex.getText().toString())) {
            fullNameEditTex.setError(getString(R.string.error_field_required));
            focusView = fullNameEditTex;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(usernameEditText.getText().toString())) {
            usernameEditText.setError(getString(R.string.error_field_required));
            focusView = usernameEditText;
            cancel = true;
        }

        //Check for a valid password.
        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError(getString(R.string.error_field_required));
            focusView = passwordEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            signUpGo();
        }
    }

    private void signUpGo() {

        retrofit2.Call<SignUpResult> resultCall = bookFairApiServiceLazy.get().attemptSignUp(emailEditText.getText().toString(), fullNameEditTex.getText().toString(),
                usernameEditText.getText().toString(), passwordEditText.getText().toString());

        resultCall.enqueue(new Callback<SignUpResult>() {
            @Override
            public void onResponse(retrofit2.Call<SignUpResult> call, Response<SignUpResult> response) {
                if (response.body().isSuccess()) {
                    preferenceManager.setLoggedInStatus(true);
                    bookFairRepository.saveUserProfiles(response.body().getData());
                    Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(retrofit2.Call<SignUpResult> call, Throwable t) {
                // Log error here if request failed
                Log.e(TAG, t.toString());
            }
        });

    }
}

