package com.example.davidarisz.pokemonbuilder.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.davidarisz.pokemonbuilder.FragmentFiles.AddFragment;
import com.example.davidarisz.pokemonbuilder.FragmentFiles.ListFragment;
import com.example.davidarisz.pokemonbuilder.FragmentFiles.PokedexFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    int numberOfTabs;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ListFragment tab1 = new ListFragment();
                return tab1;
            case 1:
                AddFragment tab2 = new AddFragment();
                return tab2;
            case 2:
                PokedexFragment tab3 = new PokedexFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
