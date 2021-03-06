package com.example.rjt.backtoschool.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.rjt.backtoschool.R;
import com.example.rjt.backtoschool.activities.MainActivity;
import com.example.rjt.backtoschool.rest.ApiClient;
import com.example.rjt.backtoschool.rest.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {
    TextView email, password, register_link;
    BootstrapButton login_button;

    public SignInFragment() {

    }

    public static SignInFragment newSignInInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);
        email = (TextView) v.findViewById(R.id.login_email);
        password = (TextView) v.findViewById(R.id.login_password);
        register_link = (TextView) v.findViewById(R.id.register_link);
        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.startPage_fragment_container, new SignUpFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        login_button = (BootstrapButton) v.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> call = apiInterface.loginCheck(email.getText().toString(), password.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("TAG", response.code() + "");
                        String s = null;
                        try {
                            s = response.body().string();
                            if (s.substring(9, 16).equals("failure")) {
                                Toast.makeText(getActivity(), "The combination of email and password doesn't match. Please try again.", Toast.LENGTH_LONG).show();
                            } else if (s.substring(9, 16).equals("success")) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });
        return v;
    }
}
