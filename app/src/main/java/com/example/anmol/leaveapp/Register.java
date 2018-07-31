package com.example.anmol.leaveapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    Spinner Designation;
    Button register;
    TextInputEditText RegName;
    TextInputEditText RegEmail;
    TextInputEditText RegPassword;
    LeaveDatabase SignUp;

    String Desig;
    String Name_Reg;
    String Email_Reg;
    String Password_Reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SignUp =new LeaveDatabase(this);

        String[] designation={"Student","Head of Department","Administrative"};

        Designation=(Spinner)findViewById(R.id.designation);
        register =(Button)findViewById(R.id.RegisterSignUp);
        RegEmail=(TextInputEditText)findViewById(R.id.EmailRegister);
        RegName=(TextInputEditText)findViewById(R.id.NameRegister);
        RegPassword=(TextInputEditText)findViewById(R.id.PasswordRegister);

        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.bpinnerback,designation);
        Designation.setAdapter(adapter);

        register.setOnClickListener(this);
        Designation.setOnItemSelectedListener(this);

    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.RegisterSignUp){

            Name_Reg=RegName.getText().toString();
            Email_Reg=RegEmail.getText().toString();
            Password_Reg=RegPassword.getText().toString();

            Log.i("name",Name_Reg);
            Log.i("email",Email_Reg);
            Log.i("password",Password_Reg);
            Log.i("Desig",Desig);



            if(TextUtils.isEmpty(Name_Reg)||TextUtils.isEmpty(Email_Reg)||TextUtils.isEmpty(Password_Reg)||TextUtils.isEmpty(Desig)){
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
            else {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (Email_Reg.matches(emailPattern) && Email_Reg.length() > 0){
                    if(Password_Reg.length()>=8){

                        SignUp.WriteData(Name_Reg,Email_Reg,Password_Reg,Desig);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("LoginDesig",Desig);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(this,"Minimum password size is 8",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"EMail is not valid",Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Desig=Designation.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
