package com.example.whatsappclone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.whatsappclone.ChatDetailActivity;
import com.example.whatsappclone.R;
import com.example.whatsappclone.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
//this fragment is to set recyclerview in chatfragment
//so the code is same as common adapter for recyclerview
//but here we get the data of Users class and set the data into the textfield of recyclerview
public class userAdapter extends RecyclerView.Adapter<userAdapter.viewholder> {
ArrayList<Users> l1;
Context context;

    public userAdapter(ArrayList<Users> l1, Context context) {
        this.l1 = l1;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_show_user,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
      Users user=l1.get(position);

      //Picasso.get().load(user.getProfilePicture()).into(holder.image);
        RequestOptions option=new RequestOptions()
                .fitCenter()
                .placeholder(R.drawable.user)
                .error(R.drawable.user);
        Glide.with(context).load(user.getProfilePicture()).apply(option).into(holder.image);
      holder.t1.setText(user.getName());

        FirebaseDatabase.getInstance().getReference().child("chats").
               child(FirebaseAuth.getInstance().getUid()+user.getUserid()).
                orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren())
                        {
                            for(DataSnapshot snapshot2:snapshot.getChildren())
                            {
                                holder.t2.setText(snapshot2.child("message").getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i6=new Intent(context, ChatDetailActivity.class);
              i6.putExtra("userID",user.getUserid());
              i6.putExtra("userName",user.getName());
              context.startActivity(i6);
          }
      });
    //  holder.t2.setText(user.getLastmessage());
    }

    @Override
    public int getItemCount() {
        return l1.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView t1,t2;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.profile_image);
            t1=(TextView)itemView.findViewById(R.id.magneta);
            t2=(TextView)itemView.findViewById(R.id.last_message);

        }
    }
}
