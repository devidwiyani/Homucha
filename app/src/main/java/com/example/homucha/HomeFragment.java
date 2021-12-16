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
        Intent intent1 = new Intent(getActivity(), ListSofaActivity.class);
        switch (v.getId()){
            case R.id.sofa:
                intent1.putExtra("id_jenis",1);
                startActivity(intent1);
                break;
            case R.id.meja:
                intent1.putExtra("id_jenis",2);
                startActivity(intent1);
                break;
            case R.id.kursi:
                intent1.putExtra("id_jenis",3);
                startActivity(intent1);
                break;
            case R.id.dekorasi:
                intent1.putExtra("id_jenis",4);
                startActivity(intent1);
                break;
            case R.id.penyimpanan:
                intent1.putExtra("id_jenis",5);
                startActivity(intent1);
                break;
            case R.id.furnitur:
                intent1.putExtra("id_jenis",6);
                startActivity(intent1);
                break;
            case R.id.kasur:
                intent1.putExtra("id_jenis",7);
                startActivity(intent1);
                break;
            case R.id.elektronik:
                intent1.putExtra("id_jenis",8);
                startActivity(intent1);
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
