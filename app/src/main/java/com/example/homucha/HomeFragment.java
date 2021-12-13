package com.example.homucha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    CardView sofa, meja, kursi, dekorasi, penyimpanan, furnitur, kasur, elektronik;
    ImageButton next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sofa = v.findViewById(R.id.sofa);
        meja = v.findViewById(R.id.meja);
        kursi = v.findViewById(R.id.kursi);
        dekorasi = v.findViewById(R.id.dekorasi);
        penyimpanan = v.findViewById(R.id.penyimpanan);
        furnitur = v.findViewById(R.id.furnitur);
        kasur = v.findViewById(R.id.kasur);
        elektronik = v.findViewById(R.id.elektronik);
        next = v.findViewById(R.id.next);

        sofa.setOnClickListener(this);
        meja.setOnClickListener(this);
        kursi.setOnClickListener(this);
        dekorasi.setOnClickListener(this);
        penyimpanan.setOnClickListener(this);
        furnitur.setOnClickListener(this);
        kasur.setOnClickListener(this);
        elektronik.setOnClickListener(this);
        next.setOnClickListener(this);

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
                Intent intent1 = new Intent(getActivity(), ListSofaActivity.class);
                startActivity(intent1);
                break;
            case R.id.meja:
                Intent intent2 = new Intent(getActivity(), ListTableActivity.class);
                startActivity(intent2);
                break;
            case R.id.kursi:
                Intent intent3 = new Intent(getActivity(), ListChairActivity.class);
                startActivity(intent3);
                break;
            case R.id.dekorasi:
                Intent intent4 = new Intent(getActivity(), ListDecorationActivity.class);
                startActivity(intent4);
                break;
            case R.id.penyimpanan:
                Intent intent5 = new Intent(getActivity(), ListStorageActivity.class);
                startActivity(intent5);
                break;
            case R.id.furnitur:
                Intent intent6 = new Intent(getActivity(), ListFurnitureActivity.class);
                startActivity(intent6);
                break;
            case R.id.kasur:
                Intent intent7 = new Intent(getActivity(), ListBedActivity.class);
                startActivity(intent7);
                break;
            case R.id.elektronik:
                Intent intent8 = new Intent(getActivity(), ListElectronicActivity.class);
                startActivity(intent8);
                break;
            case R.id.next:
                Intent intent9 = new Intent(getActivity(), BestSellerActivity.class);
                startActivity(intent9);
                break;
            default:
                break;
        }
    }
}
