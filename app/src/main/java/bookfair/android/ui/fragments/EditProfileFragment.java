package bookfair.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import bookfair.android.BookFairApp;
import bookfair.android.R;
import bookfair.android.ui.activities.AccountSettingsActivity;
import bookfair.android.util.UniversalImageLoader;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    Unbinder unbinder;
    @BindView(R.id.back_arrow_editprofile)
    ImageView backarrow;
    @BindView(R.id.profile_photo)
    CircleImageView profilePhoto;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        unbinder = ButterKnife.bind(this, view);

        //back button navigation
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        String[] location = {"Kingston","Montego Bay", "Spanish Town","Mandeville",
                "Ocho Rios","Port Antonio","May Pen","Negril","Falmouth","PortMore","Black River","Old Harbour","Savanna La Mar","Lucea",
                "Port Maria", "Morant Bay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, location);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        locationSpinner.setAdapter(adapter);

        return view;

    }

    private void setProfileImage(){
            String imgUrl = "https://www.vectorstock.com/royalty-free-vector/default-avatar-profile-icon-vector-18942381";
        UniversalImageLoader.setImage(imgUrl,profilePhoto, null,"");
    }
}
