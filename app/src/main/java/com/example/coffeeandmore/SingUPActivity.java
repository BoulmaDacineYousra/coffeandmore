package com.example.coffeeandmore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message ;
import javax.mail.MessagingException ;
import javax.mail.PasswordAuthentication ;
import javax.mail.Session ;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress ;
import javax.mail.internet.MimeMessage ;
import java.util.Properties ;




public class SingUPActivity extends AppCompatActivity {
    private EditText emailInput , passwordInput , confirmPasswordInput , userNameInput ;
    private Button singUpBtn ;
    private TextView returnToLogIn ;
    private String email , password , name , confirmPassword ;
    private String userCode ;
    private int vereficationCode ;
    private DatabaseHelper dbHelper ;
    private ProgressDialog loadingBar ;
    private final static Pattern PASSWORD_PATTERN = Pattern.compile(
                    "(?=.*[0-9])" +         //at least 1 digitower case letter
                  //  "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,15}" +               //at least
                    "(?=.*[a-z])" +         //at least 1 l8 characters
                    "$");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);



     emailInput =  (EditText) findViewById(R.id.sign_up_email_input);
     passwordInput=  (EditText) findViewById(R.id.sign_up_password_input);
     confirmPasswordInput =  (EditText) findViewById(R.id.sign_up_confirm_password_input);
     userNameInput =  (EditText) findViewById(R.id.sing_up_user_name_input);
     singUpBtn =(Button) findViewById(R.id.sign_up_btn_btn) ;
     returnToLogIn = (TextView) findViewById(R.id.return_log_in_page) ;






    dbHelper = new DatabaseHelper(this);
    loadingBar= new ProgressDialog(this);

     returnToLogIn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext() , MainActivity.class);
             startActivity(intent);
         }
     });

     singUpBtn.setOnClickListener( new View.OnClickListener() {

         @Override
         public void onClick(View v) {

             email = emailInput.getText().toString();
             name = userNameInput.getText().toString();
             password = passwordInput.getText().toString();
             confirmPassword= confirmPasswordInput.getText().toString();




             if ( TextUtils.isEmpty(password) && TextUtils.isEmpty(email)
                     && TextUtils.isEmpty(confirmPassword)&&TextUtils.isEmpty(name) )
             {
                 Toast.makeText(SingUPActivity .this , " les champs ne peuvent pas " +
                         "etre vide ", Toast.LENGTH_LONG).show();
             }


             else if (! Patterns.EMAIL_ADDRESS.matcher(email).matches())
             {
                 Toast.makeText(SingUPActivity.this , "le format de l'email  " +
                         "est erroné ",1).show();
             }
             else if (  PASSWORD_PATTERN.matcher(password).matches())
             {
                 Toast.makeText(SingUPActivity.this , "le mot de passe doit contenir un " +
                         "caractére alpanumériqe et un caractére special " , 1).show();

                }
             else if ( ! TextUtils.equals(password,confirmPassword)){
                     Toast.makeText(getApplicationContext(), "les mots de passes" +
                             " ne sont pas identique ", 1).show();
             }
             else
             {
                 loadingBar.setTitle("creer un vompte  ");
                 loadingBar.setMessage("veillez attendre  ");
                 loadingBar.setCanceledOnTouchOutside(false);
                 loadingBar.show();

//                SendVereficationEmail(email);
                CreateAccount();

             }

         }
     });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void SendVereficationEmail(String email) {
        final String EMAIL = "coffandmore@gmail.com";
        final String PASSWORD = "byGeeks_Z";
        Random rand = new Random();
      vereficationCode = (int) (rand.nextInt(999999)) ;



        String emailToSend = "Bonjour ,  " + name
                + getString(R.string.email) + vereficationCode
                + getString(R.string.suite);





        Properties prop = new Properties();
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.host","smtp.gmail.com");
        prop.put("mail.smtp.port","587");



        Session session = Session.getDefaultInstance(prop, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {


                return new PasswordAuthentication(EMAIL,PASSWORD);
            }
        });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
            message.setSubject(getString(R.string.email_object));
            message.setText(emailToSend);

            Transport.send(message);

            loadingBar.dismiss();
            Toast.makeText(getApplicationContext() , "un email de " +
                    "verification a étais envoyé , verifier votre boite email ", 1).show();




           AlertDialog.Builder builder = new AlertDialog.Builder(this );
            builder.setTitle("code de confirmation");

          final   EditText code = new EditText(this );
           code.setHint("code de confirmation");
           code.setInputType(InputType.TYPE_CLASS_PHONE);
           builder.setView(code);



            builder.setPositiveButton("confirmer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                   userCode = code.getText().toString(); //.trim();
                  Toast.makeText(getApplicationContext() , " le code est "
                          + userCode + "le code de verification " + vereficationCode
                          , 1).show();

                }
            });


            builder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                }
            });
            builder.create().show();





        }      catch( MessagingException e ){
            throw new RuntimeException(e);

        }
      if (TextUtils.equals(Integer.toString(vereficationCode), userCode) ){
          CreateAccount();

      }
      else {
          Toast.makeText(getApplicationContext(),"le code de confirmation " +
                  "est erronée",1).show();
      }

    }

    private void CreateAccount() {

        Boolean  accountCreated = dbHelper.Regester(name , email , password);
        if (accountCreated == true ){
            Toast.makeText(SingUPActivity.this , "le compte a été creer" +
                    " avec succé , vous pouvez vous connecter a votre compte ",1).show();
            Intent intent = new Intent(SingUPActivity.this , MainActivity.class);
            startActivity(intent);

        }  else {
            Toast.makeText(SingUPActivity.this , "le compte n'a pas pu etre creer ",1 ).show();


        }
    }


}


