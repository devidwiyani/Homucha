package com.example.homucha;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment{
    RelativeLayout expandableView;
    ImageButton arrowBtn;
    ImageView imgLogout;
    CardView cardView;
    TextView accName, accEmail, accAddress, accPhone, edit;
    String strName, strEmail, strAddress, strPhone, strUsername;
    Button editButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        expandableView = v.findViewById(R.id.expandableView);
        arrowBtn = v.findViewById(R.id.arrowBtn);
        cardView = v.findViewById(R.id.cardView);
        imgLogout = v.findViewById(R.id.img_logout);
        editButton = (Button) v.findViewById(R.id.btn_editProfile);

        accName = (TextView) v.findViewById(R.id.prName);
        accEmail = (TextView) v.findViewById(R.id.prEmail);
        accAddress= (TextView) v.findViewById(R.id.prAlamat);
        accPhone = (TextView) v.findViewById(R.id.prPhone);
        edit = (TextView) v.findViewById(R.id.editProfile);

        DashboardActivity activity = (DashboardActivity) getActivity();
        strName = activity.getNameFragment();
        strEmail = activity.getMailFragment();
        strAddress = activity.getAdressFragment();
        strPhone = activity.getPhoneFragment();
        strUsername = activity.getPhoneFragment();

        accName.setText(strName);
        accEmail.setText(strEmail);
        accAddress.setText(strAddress);
        accPhone.setText(strPhone);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        arrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableView.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_arrow_drop_up);
                } else {
                    TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    arrowBtn.setBackgroundResource(R.drawable.ic_arrow_drop);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),EditProfileActivity.class);
                intent.putExtra("editProfile", strName);
                intent.putExtra("editProfile1", strUsername);
                startActivity(intent);
                getActivity().finish();
            }
        });


        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Logout",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
