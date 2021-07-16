package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.databinding.ActivityGroupchatBinding;
import com.example.whatsappclone.models.MessgeModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Groupchat extends AppCompatActivity {
ActivityGroupchatBinding binding;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupchatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ArrayList<MessgeModel> mc1=new ArrayList<>();
        final ChatAdapter ch12=new ChatAdapter(mc1,this);
        binding.recycleChatGroup.setAdapter(ch12);
        LinearLayoutManager lj1=new LinearLayoutManager(this);
        binding.recycleChatGroup.setLayoutManager(lj1);
getSupportActionBar().hide();
database=FirebaseDatabase.getInstance();
        FirebaseAuth fauth=FirebaseAuth.getInstance();
        String senderid=fauth.getUid();

        binding.nameGroup.setText("Friends Group");


        binding.sendmessageChatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message=binding.chatGroup.getText().toString();
                final MessgeModel model=new MessgeModel(senderid,message);
                binding.chatGroup.setText("");
                model.setTimestamp(new Date().getTime());
                database=FirebaseDatabase.getInstance();
                database.getReference().child("Groupchat")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });
    database.getReference().child("Groupchat")
            .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                       mc1.clear();
                       for(DataSnapshot snapshot1:snapshot.getChildren())
                       {
                           MessgeModel model=snapshot1.getValue(MessgeModel.class);
                           mc1.add(model);
                       }
                       ch12.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}