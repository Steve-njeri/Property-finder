package com.app.propertyfinder.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.propertyfinder.R;
import com.app.propertyfinder.models.Property;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertyDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.propertyimage) ImageView mPropertyView;
    @BindView(R.id.propertyLocation) TextView mPropertyLocationView;
    @BindView(R.id.propertyDescription) TextView mPropertyDescriptionView;
    @BindView(R.id.propertyName) TextView mPropertyNameView;
    @BindView(R.id.propertyStatus) TextView mPropertyStatusView;
    @BindView(R.id.propertyPrice) TextView mPropertyPriceView;
    @BindView(R.id.propertyPhone) TextView mPropertyPhoneView;

    private Property mProperty;

    public  PropertyDetailFragment() {

    }
    public  static  PropertyDetailFragment newInstance(Property property){
        PropertyDetailFragment propertyDetailFragment = new PropertyDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("property", Parcels.wrap(property));
        propertyDetailFragment.setArguments(args);
        return  propertyDetailFragment;
    }

    @Override
    public  void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        assert  getArguments() != null;
        mProperty = Parcels.unwrap(getArguments().getParcelable("property"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_property_detail, container,false);
        ButterKnife.bind(this, view);
        Picasso.get().load(mProperty.getImageUri()).into(mPropertyView);
        mPropertyNameView.setText(mProperty.getTitle());
        mPropertyPriceView.setText(mProperty.getPrice());

        if (mProperty.getOccupied()){
            mPropertyStatusView.setText("Occupied");
        }
        if (!(mProperty.getOccupied())){
            mPropertyStatusView.setText("Vacant");
        }

        mPropertyDescriptionView.setText(mProperty.getDescription());
        mPropertyLocationView.setText(mProperty.getLocation());
        mPropertyPhoneView.setText(mProperty.getPhone());

        mPropertyPhoneView.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View view) {
        if (view == mPropertyPhoneView) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mProperty.getPhone()));
            startActivity(phoneIntent);
        }
    }
}
