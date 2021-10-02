package com.app.propertyfinder.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.propertyfinder.R;
import com.app.propertyfinder.adapters.PropertyPagerAdapter;
import com.app.propertyfinder.models.Property;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager) ViewPager mViewPager;
    private PropertyPagerAdapter adapterViewPager;
    List<Property> mProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        ButterKnife.bind(this);

        mProperty = Parcels.unwrap(getIntent().getParcelableExtra("properties"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new PropertyPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mProperty);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }
}
