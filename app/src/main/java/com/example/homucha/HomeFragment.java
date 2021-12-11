package com.example.homucha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeFragment extends Fragment implements View.OnClickListener {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.ce1, R.drawable.ce2, R.drawable.ce3};
    CardView sofa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sofa = v.findViewById(R.id.sofa);
        sofa.setOnClickListener(this);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };

        carouselView = v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        return v;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sofa:
                Intent intent = new Intent(getActivity(), ListSofaActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
