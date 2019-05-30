package bookfair.android.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import java.util.ArrayList;

import bookfair.android.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountSettingsActivity extends BaseActivity {

    @BindView(R.id.optionslist)
    ListView optionsListView;
    @BindView(R.id.back_arrow)
    ImageView backArrow;

    private Context context;

    private static final String TAG = "AccountSettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);
        ButterKnife.bind(this);
        context = AccountSettingsActivity.this;
        Log.d(TAG, "onCreate Started.");
        applicationComponent(this).inject(AccountSettingsActivity.this);

        setupSettingsList();

        //setup back arrow navigation to "Profile Fragment"
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setupSettingsList(){
        ArrayList<String> options =  new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString(R.string.change_password));
        options.add(getString(R.string.log_out));

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, options);
        optionsListView.setAdapter(adapter);
    }

}
