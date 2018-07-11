package bookfair.android.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatImageView;
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

    @BindView(R.id.email_editText)
    TextInputEditText emailTextInputEditText;
    @BindView(R.id.facebook_login_btn)
    FancyButton facebookLoginBtn;
    @BindView(R.id.editText_layout)
    TextInputLayout editTextLayout;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    @BindView(R.id.logo)
    AppCompatImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        applicationComponent(this).inject(SignUpActivity.this);
    }
}
