package bookfair.android.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.core.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class SignUpActivity extends BaseActivity {

    @Inject
    PreferenceManager preferenceManager;
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
                signUp();
            }
        });
    }

    private void facebookLogin() {

    }

    private void signUp() {
        editTextValidation();
    }

    private void editTextValidation() {
        if (TextUtils.isEmpty(emailEditText.getText()) || TextUtils.isEmpty(fullNameEditTex.getText()) || TextUtils.isEmpty(usernameEditText.getText())
                || TextUtils.isEmpty(passwordEditText.getText()))
        {

        }
    }
}
