package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivitySettingsBinding;
import com.example.whatsappclone.models.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
ActivitySettingsBinding binding;
FirebaseAuth fauth;
FirebaseDatabase database;
FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase
                .getInstance();
        fauth=FirebaseAuth.getInstance();
        getSupportActionBar().hide();
database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user=snapshot.getValue(Users.class);
                Picasso.get().load(user.getProfilePicture()).placeholder(R.drawable.user)
                        .into(binding.mainImage);
                binding.usernameEdit.setText(user.getName().toString());
                if(database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("aboutus")!=null)
                binding.aboutedit.setText(user.getAboutus().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this,MainActivity.class));
            }
        });
        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null)
        {
            Uri file=data.getData();
            binding.mainImage.setImageURI(file);
            //get storage reference
            final StorageReference reference=storage.getReference().child("profilepicture").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                         database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profilepic").setValue(uri.toString());
                         Toast.makeText(SettingsActivity.this,"Profile Picture updated",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });

        }
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status=binding.usernameEdit.getText().toString();
                String name=binding.aboutedit.getText().toString();


                HashMap<String,Object> obj=new HashMap<>();
                obj.put("name",status);
                obj.put("aboutus",name);

                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).
                        updateChildren(obj);


            }
        });
    }
}