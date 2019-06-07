package bookfair.android.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import bookfair.android.R;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;
import static bookfair.android.ui.views.SnackBarFactory.createSnackbar;

public class EditProfileFragment extends Fragment {

    @BindView(R.id.location_spinner)
    Spinner locationSpinner;
    Unbinder unbinder;
    @BindView(R.id.back_arrow_editprofile)
    ImageView backarrow;
    @BindView(R.id.checkmark)
    ImageView saveUserInfoCheckMark;
    @BindView(R.id.profile_photo)
    ImageView profilePhoto;
    @BindView(R.id.changeProfilePhoto)
    TextView changeProfilePhoto;
    @BindView(R.id.edit_profile_coord)
    CoordinatorLayout editProfileCoord;
    @BindView(R.id.username)
    EditText usernameEditText;
    @BindView(R.id.display_name)
    EditText nameEditText;
    @BindView(R.id.email_edittext)
    EditText emailEditText;

    Uri uriProfileImage;
    String profileImageURL;
    FirebaseAuth firebaseAuth;

    private static final int CHOOSE_IMAGE = 07;

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

        //initialize firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //back button navigation
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        saveUserInfoCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });


        String[] location = {"Kingston","Montego Bay", "Spanish Town","Mandeville",
                "Ocho Rios","Port Antonio","May Pen","Negril","Falmouth","PortMore","Black River","Old Harbour","Savanna La Mar","Lucea",
                "Port Maria", "Morant Bay"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, location);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        locationSpinner.setAdapter(adapter);

        //change profile photo
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfilePhoto();
            }
        });

        return view;

    }

    private void saveUserInfo() {

        String username = usernameEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        //check for valid username
        if(TextUtils.isEmpty(username)) {
            usernameEditText.setError("username is required");
            usernameEditText.requestFocus();
            return;
        }
        //check for valid name
        if(TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            nameEditText.requestFocus();
            return;
        }
        //check for valid email
        if(TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null && profileImageURL != null){
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setDisplayName(username)
                    .setPhotoUri(Uri.parse(profileImageURL))
                    .build();
            user.updateProfile(userProfileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            createSnackbar(getActivity(), editProfileCoord, "Profile Updated").show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            createSnackbar(getActivity(), editProfileCoord, e.getMessage()).show();
                        }
                    });
        }
    }

    //function needed to select the profile image...
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uriProfileImage);
                profilePhoto.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis() + ".jpg");
        if(uriProfileImage != null) {
            storageReference.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profileImageURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    createSnackbar(getActivity(), editProfileCoord, e.getMessage()).show();
                }
            });
        }
    }

    private void changeProfilePhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }
}
