package com.example.whatsappclone.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone.R;
import com.example.whatsappclone.models.MessgeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{


    ArrayList<MessgeModel> messModels;

    public ChatAdapter(ArrayList<MessgeModel> messModels, Context context, String resid) {
        this.messModels = messModels;
        this.context = context;
        this.resid = resid;
    }

    Context context;
    String resid;
     int SENDER_VIEW_TYPE=1;
     int RECEIVER_VIEW_TYPE=2;



    public ChatAdapter(ArrayList<MessgeModel> messgeModels, Context context) {
        this.messModels = messgeModels;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender_chat,parent,false);
            return new senderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {


        //sender is only on person
        if(messModels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //get the messageclass for details of message and user id
        MessgeModel m1= messModels.get(position);
           if(holder.getClass()==senderViewHolder.class)
           {
               ((senderViewHolder)holder).sendermessage.setText(m1.getMessage().toString());
           }
           else
           {
               ((ReceiverViewHolder)holder).receivermessage.setText(m1.getMessage().toString());
           }
           holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   new AlertDialog.Builder(context)
                           .setTitle("delete")
                           .setMessage("are you sure you want to delete the message")
                           .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   //delete the ,essage id which is chat section in realtimedatabase
                                   FirebaseDatabase database = FirebaseDatabase.getInstance();
                                   String senderroom = FirebaseAuth.getInstance().getUid() + resid;
                                   database.getReference().child("chats").child(senderroom)
                                           .child(m1.getMessage_id())
                                           .setValue(null);

                               }
                           })
                           .setNegativeButton("No", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           }).show();

                   return  false;
               }
           });
    }

    @Override
    public int getItemCount() {
        return messModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView receivertime,receivermessage;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receivermessage=itemView.findViewById(R.id.chat_receiver);
            receivertime
                    =itemView.findViewById(R.id.time_receiver);
        }
    }

    public class senderViewHolder extends  RecyclerView.ViewHolder {
        TextView sendertime,sendermessage;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendertime=itemView.findViewById(R.id.textchat);
            sendermessage=itemView.findViewById(R.id.chat_sender);
        }
    }
}
