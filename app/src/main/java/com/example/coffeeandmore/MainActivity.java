package com.example.coffeeandmore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput ;
    private Button  logInBtn;
    private TextView forgetPassword , singInTxt ;
    private CheckBox checkBox ;
    private String email, password;
    private DatabaseHelper dbHelper;
    private ImageButton seePwd ;
    private final static Pattern PASSWORD_PATTERN = Pattern.compile(
            "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,15}" +               //at least 8 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailInput = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordInput = (EditText) findViewById(R.id.editTextTextPassword);
        singInTxt = (TextView) findViewById(R.id.sing_in_txt);
        logInBtn = (Button) findViewById(R.id.log_in_btn);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        forgetPassword = (TextView) findViewById(R.id.forget_password_txt);
        seePwd = (ImageButton) findViewById(R.id.see_password) ;


        dbHelper = new DatabaseHelper(this);


        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailInput.getText().toString();
                password = passwordInput.getText().toString();

                Toast.makeText(getApplicationContext(), email + password, 1).show();

                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, " les champs ne peuvent pas " +
                            "etre vide ", Toast.LENGTH_LONG).show();
                }
               if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(MainActivity.this , "le format de l'email est erroné ",1).show();
                }
               /*else if (  PASSWORD_PATTERN.matcher(password).matches())
                {
                     Toast.makeText(MainActivity.this , "le mot de passe doit contenir un " +
                             "caractére alpanumériqe et un caractére special " , 1).show();
                }*/
                else {

                    boolean logedIn = dbHelper.getData(email, password);
                    if (logedIn == true) {

                        //save user auth info
                        UserData currentUser = dbHelper.getUserData(email, password);
                        SessionManagment sessionManagment = new SessionManagment(MainActivity.this);
                        sessionManagment.saveSession(currentUser);

                        //move to home acctivity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);


                    } else {

                        Toast.makeText(MainActivity.this, "veillez vzrifier " +
                                "vous information ", 1).show();
                    }
                }
            }
        });

        singInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, SingUPActivity.class);
                startActivity(intent);

            }
        });
        seePwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());



            }
        });

    }



}