package com.dilaratatlisu.javamaps.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canhub.cropper.CropImageActivity;
import com.canhub.cropper.CropImageView;

import com.dilaratatlisu.javamaps.Fragments.ProfileFragment;
import com.dilaratatlisu.javamaps.R;
import com.dilaratatlisu.javamaps.databinding.ActivityEditProfileBinding;
import com.dilaratatlisu.javamaps.databinding.ActivityPostBinding;
import com.dilaratatlisu.javamaps.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView close;
    private CircleImageView imageProfile;
    private TextView save;
    private TextView changePhoto;
    private MaterialEditText fullname;
    private MaterialEditText username;
    private MaterialEditText bio;

    private FirebaseUser fUser;

    Uri mImageUri;
    private UploadTask uploadTask;
    private StorageReference storageRef;
    private ActivityResultLauncher<String> permissionLauncher;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Uri imageUri;
    private ActivityEditProfileBinding binding;
    FirebaseAuth auth;
    User user;
    ActivityResultLauncher<CropImageContractOptions> cropImage;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        user = new User();

        close = findViewById(R.id.close);
        imageProfile = findViewById(R.id.image_profile);
        save = findViewById(R.id.save);
        changePhoto = findViewById(R.id.change_photo);
        fullname = findViewById(R.id.name);
        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);
        auth = FirebaseAuth.getInstance();

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference().child("Uploads");

        registerLauncher();

        FirebaseDatabase.getInstance().getReference().child("Users").child(fUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullname.setText(user.getName());
                username.setText(user.getUsername());
                bio.setText(user.getBio());
                //Picasso.get().load(user.getImageUrl()).into(imageProfile);
                Glide.with(getApplicationContext()).load(user.getImageUrl()).into(imageProfile);
                //user.setImageUrl(imageProfile.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);


            }
        });

         */

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).start(EditProfileActivity.this);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                uploadImage();
            }
        });
    }

    private void updateProfile() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("fullname", fullname.getText().toString());
        map.put("username", username.getText().toString());
        map.put("bio", bio.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Users").child(fUser.getUid()).updateChildren(map);



        /*String fullName = fullname.getText().toString();
        String userName = username.getText().toString();
        String bioText = bio.getText().toString();

        User user1 = new User(fullName, userName, url, bioText);

        FirebaseDatabase.getInstance().getReference().child("Users").child(fUser.getUid()).setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    
                }
            }
        })

         */


    }

    private void uploadImage() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        AlertDialog dialog = alertDialog.create();
        dialog.show();

        /*if (mImageUri != null) {
            StorageReference reference = storageRef.child(System.currentTimeMillis() + ".jpeg");

            uploadTask = reference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return  reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String url = downloadUri.toString();

                        FirebaseDatabase.getInstance().getReference().child("Users").child(fUser.getUid()).child("imageurl").setValue(url);
                        imageProfile.setImageURI(downloadUri);

                    } else {
                        Toast.makeText(EditProfileActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }

         */


            StorageReference reference = storageRef.child(mImageUri.getLastPathSegment());

            reference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri downloadUri = uriTask.getResult();
                    url = downloadUri.toString();
                    FirebaseDatabase.getInstance().getReference().child("Users").child(fUser.getUid()).child("imageurl").setValue(url);
                    updateProfile();
                    dialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            });



    }
    public void selectProfileImage(View view){

        if (Build.VERSION.SDK_INT >= 33){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)){

                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give permision", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //request permission
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);

                        }
                    }).show();

                } else {
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                }
            } else {
                //go gallery
                Intent intentoGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentoGallery);


            }

        }

        else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

                    Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give permision", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //request permission
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                        }
                    }).show();

                } else {
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            } else {
                //go gallery
                Intent intentoGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentoGallery);


            }

        }


    }



   /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ActivityResultLauncher<CropImageContractOptions> cropImage = registerForActivityResult(new CropImageContract(), result -> {
                Uri uriContent = result.getUriContent();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(EditProfileActivity.this.getContentResolver(), uriContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageProfile.setImageBitmap(bitmap);
            });

        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    */

    public void registerLauncher() {

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

                if (o.getResultCode() == Activity.RESULT_OK) {
                    Intent intentFromResult = o.getData();
                        if (intentFromResult!= null){
                            mImageUri = intentFromResult.getData();
                            imageProfile.setImageURI(mImageUri);
                        }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }

            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {

                if (o){
                    //permission granted, go gallery
                    Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentGallery);

                } else {
                    //permission denied
                    Toast.makeText(EditProfileActivity.this, "Permission needed for gallery", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}
