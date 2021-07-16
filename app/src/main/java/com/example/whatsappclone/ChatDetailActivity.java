package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.whatsappclone.Adapters.ChatAdapter;
import com.example.whatsappclone.databinding.ActivityChatDetailBinding;
import com.example.whatsappclone.databinding.ActivityMainBinding;
import com.example.whatsappclone.models.MessgeModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ChatDetailActivity extends AppCompatActivity {
ActivityChatDetailBinding binding;
FirebaseAuth auth5;
FirebaseDatabase database;
 ChatAdapter ch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth5 = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
       final  String senderid = auth5.getUid();
        String receiverid = getIntent().getStringExtra("userID");
        String name = getIntent().getStringExtra("userName");
        binding.nameChat.setText(name);

        binding.backChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i7 = new Intent(ChatDetailActivity.this, MainActivity.class);
                startActivity(i7);
            }
        });

final  String senderroom=senderid+receiverid;
final String receiverroom=receiverid+senderid;


        ArrayList<MessgeModel> amg1 = new ArrayList<>();
        ch1=new ChatAdapter(amg1,this,receiverid);
        binding.recycleChat.setAdapter(ch1);

        LinearLayoutManager l1 = new LinearLayoutManager(this);
        binding.recycleChat.setLayoutManager(l1);



        database.getReference().child("chats")
                .child(senderroom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        amg1.clear();
                        for(DataSnapshot snapshot1:snapshot.getChildren())
                        {
                            MessgeModel model=snapshot1.getValue(MessgeModel.class);
                            model.setMessage_id(snapshot1.getKey());
                            amg1.add(model);

                        }
                        ch1.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.sendmessageChat.setOnClickListener(new View.OnClickListener() {
            @Override
            //whenever send button clicks we saved the data in firebase realtime database

            public void onClick(View v) {
                String message = binding.chatChat.getText().toString();
                final MessgeModel m1 = new MessgeModel(senderid, message);
                m1.setTimestamp(new Date().getTime());
                binding.chatChat.setText("");
                database.getReference().child("chats").child(senderroom)
                        .push()
                        .setValue(m1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("chats").child(receiverroom)
                                .push()
                                .setValue(m1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }
        });





    }

}