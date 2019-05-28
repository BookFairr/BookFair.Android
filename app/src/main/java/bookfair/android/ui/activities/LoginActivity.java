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
import com.unstoppable.submitbuttonview.SubmitButton;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.core.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

import static bookfair.android.ui.views.SnackBarFactory.createSnackbar;
import static bookfair.android.util.Util.isEmailValid;
import static bookfair.android.util.Util.passwordStrength;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private boolean loginErrorOccured;

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
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.login_btn)
    SubmitButton loginBtn;
    @BindView(R.id.login_coordinator)
    CoordinatorLayout loginCoordinator;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        applicationComponent(this).inject(LoginActivity.this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //Check for a valid email.
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.error_field_required));
            loginBtn.reset();
            return;
        }

        if (!isEmailValid(email)) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            loginBtn.reset();
            return;
        }

        //Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.error_field_required));
            loginBtn.reset();
            return;
        }

        if (!passwordStrength(password)) {
            passwordEditText.setError(getString(R.string.password_strength));
            loginBtn.reset();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if  (task.isSuccessful()) {
                            // Set Logged In status to 'true'
                            preferenceManager.setLoggedInStatus(getApplicationContext(), true);
                            loginBtn.doResult(true);
                            createSnackbar(LoginActivity.this, loginCoordinator, "Getting Things Ready...").show();

                            new Handler().postDelayed(() -> {
                                goToMainActivity();
                            }, 2000);

                        } else {
                            createSnackbar(LoginActivity.this, loginCoordinator, task.getException().getMessage()).show();
                            loginBtn.doResult(false);
                            new Handler().postDelayed(() -> loginBtn.reset(), 2000);
                            loginErrorOccured = true;
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


    /** Call<LogInResult> resultCall = bookFairApiServiceLazy.get().attemptLogIn(emailEditText.getText().toString(), passwordEditText.getText().toString());

     resultCall.enqueue(new Callback<LogInResult>() {
    @Override
    public void onResponse(Call<LogInResult> call, Response<LogInResult> response) {

    if (response.isSuccessful()) {
    if (response.body().isSuccess()) {

    // Set Logged In status to 'true'
    preferenceManager.setLoggedInStatus(getApplicationContext(), true);

    try {
    preferenceManager.setUserFullName(response.body().getProfile().getFullname());
    preferenceManager.setUsername(response.body().getProfile().getUsername());

    }catch (Exception e){}

    loginBtn.doResult(true);
    createSnackbar(LoginActivity.this, loginCoordinator, "Getting things ready..." ).show();

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
    }); */
