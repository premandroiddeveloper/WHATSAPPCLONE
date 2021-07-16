package com.example.whatsappclone.FRAGMENTS_of_VIEWPAGER;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.whatsappclone.Adapters.userAdapter;
import com.example.whatsappclone.MainActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.databinding.FragmentChatBinding;
import com.example.whatsappclone.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChatFragment extends Fragment {
//this fragment is used to bind recyclerview to userAdapter


    public ChatFragment() {
        // Required empty public constructor
    }
     FragmentChatBinding binding;

ArrayList<Users> userlist=new ArrayList<>();
FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatBinding.inflate(getLayoutInflater(),container,false);
        userAdapter adapter=new userAdapter(userlist, getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclesChat.setLayoutManager(linearLayoutManager);
        binding.recyclesChat.setAdapter(adapter);
        database=FirebaseDatabase.getInstance();
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Users users=dataSnapshot.getValue(Users.class);
                    users.setUserid(dataSnapshot.getKey());

                    userlist.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();

    }
}