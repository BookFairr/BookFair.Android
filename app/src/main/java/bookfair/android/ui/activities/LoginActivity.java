package bookfair.android.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.core.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends BaseActivity {

    @Inject
    PreferenceManager preferenceManager;

    @BindView(R.id.email_or_username_editText)
    TextInputEditText emailOrUsernameEditText;
    @BindView(R.id.email_or_username_layout)
    TextInputLayout emailOrUsernameLayout;
    @BindView(R.id.password_editText)
    TextInputEditText passwordEditText;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.facebook_login_btn)
    FancyButton facebookLoginBtn;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.login_btn)
    FancyButton loginBtn;

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

    private void attemptLogin () {

        //Reset errors.
        emailOrUsernameEditText.setError(null);
        passwordEditText.setError(null);

        boolean cancel = false;
        View focusView = null;

        //Check for a valid email.
        if (TextUtils.isEmpty(emailOrUsernameEditText.getText().toString())) {
            emailOrUsernameEditText.setError(getString(R.string.error_field_required));
            focusView = emailOrUsernameEditText;
            cancel = true;
        }

        //Check for a valid password.
        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError(getString(R.string.error_field_required));
            focusView = emailOrUsernameEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }   else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            logInGo();
        }
    }

    private void logInGo () {

    }

    private void facebookLogin () {

    }
}
