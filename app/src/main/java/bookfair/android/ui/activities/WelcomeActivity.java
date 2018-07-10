package bookfair.android.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import bookfair.android.R;
import bookfair.android.core.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class WelcomeActivity extends BaseActivity {

@BindView(R.id.launch_login)
    FancyButton launchLoginBtn;

@BindView(R.id.launch_signup)
    FancyButton launchSignUpBtn;

@Inject
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        applicationComponent(this).inject(this);

        launchLoginBtn.setOnClickListener(v -> goToLoginActivity());

        launchSignUpBtn.setOnClickListener(v -> goToSignUpActivity());
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToSignUpActivity () {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
