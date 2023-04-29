package com.example.javaht;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.javaht.fragments.CharacterInfoFragment;
import com.example.javaht.fragments.EquipmentFragment;
import com.example.javaht.fragments.ListItemsFragment;


public class TabPagerAdapter extends FragmentStateAdapter {


    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CharacterInfoFragment();
            case 1:
                return new EquipmentFragment();
            case 2:
                return new ListItemsFragment();
            default:
                return new CharacterInfoFragment();
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }
}