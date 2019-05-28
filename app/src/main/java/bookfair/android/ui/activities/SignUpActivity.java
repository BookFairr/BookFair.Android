package bookfair.android.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.unstoppable.submitbuttonview.SubmitButton;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.core.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bookfair.android.ui.views.SnackBarFactory.createSnackbar;
import static bookfair.android.util.Util.isEmailValid;
import static bookfair.android.util.Util.passwordStrength;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private boolean signUpErrorOccured;


    @Inject
    PreferenceManager preferenceManager;

    @BindView(R.id.email_editText)
    TextInputEditText emailEditText;
    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;
    @BindView(R.id.password_editText)
    TextInputEditText passwordEditText;
    @BindView(R.id.password_layout)
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

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }


    private void createAccount() {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.error_field_required));
            signupBtn.reset();
            return;
        }

        if (!isEmailValid(email)) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            signupBtn.reset();
            return;
        }

        //Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.error_field_required));
            signupBtn.reset();
            return;
        }

        if (!passwordStrength(password)) {
            passwordEditText.setError(getString(R.string.password_strength));
            signupBtn.reset();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    signupBtn.doResult(true);
                    createSnackbar(SignUpActivity.this, signUpCoordinator, "Account Successfully Created").show();

                    new Handler().postDelayed(() -> {
                        goToMainActivity();
                    }, 2000);
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        createSnackbar(SignUpActivity.this, signUpCoordinator, "Account Already Registered").show();
                    } else {
                        createSnackbar(SignUpActivity.this, signUpCoordinator, task.getException().getMessage()).show();
                    }

                    signupBtn.doResult(false);
                    new Handler().postDelayed(() -> signupBtn.reset(), 2000);
                    signUpErrorOccured = true;
                }
            }

        });

    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}


        /**
         * Manually auth setup before firebase auth was implemented
        Call<SignUpResult> resultCall = bookFairApiServiceLazy.get().createAccount(emailEditText.getText().toString(), fullNameEditTex.getText().toString(),
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
        public void onFailure(@NonNull Task<AuthResult> task, Throwable t) {
        if (t instanceof UnknownHostException) {
        } else {

        createSnackbar(SignUpActivity.this, signUpCoordinator, "Oops! Network error occurred. Try Again").show();
        signupBtn.doResult(false);
        new Handler().postDelayed(() -> signupBtn.reset(), 2000);

        }
        }

         */



