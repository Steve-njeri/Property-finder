package com.app.propertyfinder.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.propertyfinder.models.Property;
import com.app.propertyfinder.ui.PropertyDetailFragment;

import java.util.List;

public class PropertyPagerAdapter extends FragmentPagerAdapter {
    private List<Property> mProperties;

    public PropertyPagerAdapter(@NonNull FragmentManager fm, int behavior,List<Property> mProperties) {
        super(fm, behavior);
        this.mProperties = mProperties;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PropertyDetailFragment.newInstance(mProperties.get(position));
    }

    @Override
    public int getCount() {
        return mProperties.size();
    }

    public CharSequence getPageTitle(int position) {
        return mProperties.get(position).getTitle();
    }
}
