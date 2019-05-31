package bookfair.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import bookfair.android.BookFairApp;
import bookfair.android.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditProfileFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.location_spinner)
    NiceSpinner locationSpinner;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> location = new LinkedList<>(Arrays.asList("Kingston","Montego Bay", "Spanish Town","Mandeville",
                "Ocho Rios","Port Antonio","May Pen","Negril","Falmouth","PortMore","Black River","Old Harbour","Savanna La Mar","Lucea",
                "Port Maria", "Morant Bay"));
        locationSpinner.attachDataSource(location);

        locationSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            // This example uses String, but your type can be any
            String item = parent.getItemAtPosition(position).toString();

        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;


    }
}
