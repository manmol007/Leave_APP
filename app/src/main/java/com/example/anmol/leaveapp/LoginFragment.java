package com.example.anmol.leaveapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button signUp;
    private Button signin;
    private TextInputEditText email;
    private TextInputEditText password;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login, container, false);


        signUp = (Button) view.findViewById(R.id.SignUp);
        signin = (Button) view.findViewById(R.id.SignIn);

        email = (TextInputEditText) view.findViewById(R.id.Email);
        password = (TextInputEditText) view.findViewById(R.id.Password);

        signin.setOnClickListener(this);

        signUp.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.SignUp) {

            startActivity(new Intent(getActivity(), Register.class));

        }
        if (view.getId() == R.id.SignIn) {

            LeaveDatabase db = new LeaveDatabase(getActivity());
            ArrayList<String> credentials = db.getData();

            if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {

                Toast.makeText(getActivity(), "Fields are empty", Toast.LENGTH_LONG).show();
            } else {

                for (int i = 0; i < credentials.size(); ++i) {
                    Intent intent = new Intent(getActivity(), FinalThread.class);

                    ++i;

                    if (credentials.get(i).equals(email.getText().toString())) {

                        intent.putExtra("From", credentials.get(i));

                        i++;
                        if (credentials.get(i).equals(password.getText().toString())) {


                            ++i;

                            if (!credentials.get(i).equals("Student")) {
                                --i;
                                --i;
                                intent = new Intent(getActivity(), thread.class);
                                intent.putExtra("From", credentials.get(i));
                                ++i;
                                ++i;

                            }
                            intent.putExtra("Designation", credentials.get(i));
                            startActivity(intent);
                            break;
                        }
                    }

                }

            }

        }
    }
}
