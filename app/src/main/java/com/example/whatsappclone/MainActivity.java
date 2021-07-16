package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.whatsappclone.Adapters.FragmentAdapter;
import com.example.whatsappclone.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
FirebaseAuth auth3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        auth3=FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        binding.viewspages.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tablayout.setupWithViewPager(binding.viewspages);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflte=getMenuInflater();
        inflte.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.setting:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));

                break;
            case R.id.logout:

                auth3.signOut();
                Intent i5=new Intent(MainActivity.this,Sign_in.class);
                startActivity(i5);
                break;
            case R.id.groupchat:
                startActivity(new Intent(MainActivity.this,Groupchat.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}