package com.example.whatsappclone.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsappclone.FRAGMENTS_of_VIEWPAGER.CallFragment;
import com.example.whatsappclone.FRAGMENTS_of_VIEWPAGER.ChatFragment;
import com.example.whatsappclone.FRAGMENTS_of_VIEWPAGER.StatusFragment;
//this fragment class is used as a adapter to show different different fragment according to tab layout
//here we use viewpager because in the tablayout we cant switch the fragments but in the viewpager we can do it this
//using the gettItem method we switch the data from one to another fragment like chatfragment to call fragment
//using getPageTitle we change the title of the tablayout according to fragment which they relate like for ChatFragment it is CHATS
public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new ChatFragment();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallFragment();
            default:
                return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0)
            title="CHATS";
        else if(position==1)
            title="STATUS";
                    else if(position==2)
                        title="CALLS";
        return title;
    }
}
