package com.example.mac.firebasechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public ViewPager viewPager;
    public ViewPagerAdapter adapter;

    // connexion widgets
    EditText email, password;
    Button connexion;
    TextView registertxt;
    // register widgets
    EditText rEmail, rName, rPassword;
    Button register;

    FirebaseAuth mauth;
    ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser() != null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        // set my viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPagerVertical);

        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        // inscription
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        registertxt = findViewById(R.id.textRegistration);
        connexion = findViewById(R.id.connexionBtn);

        // Registration
        rEmail = findViewById(R.id.emailregister);
        rName = findViewById(R.id.nameregister);
        rPassword = findViewById(R.id.passwordRegister);
        register = findViewById(R.id.registerBtn);

        registertxt.setOnClickListener(this);
        connexion.setOnClickListener(this);
        register.setOnClickListener(this);


        progressDialog=new ProgressDialog(LoginActivity.this);


        mDatabase= FirebaseDatabase.getInstance().getReference().child("users");

    }


    public void register(){
        final String name = rName.getText().toString().trim();
        String email = rEmail.getText().toString().trim();
        String password = rPassword.getText().toString().trim();

        if(name.equals("")){
            Toast.makeText(LoginActivity.this, "Nom invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.equals("")){
            Toast.makeText(LoginActivity.this, "Email invalide", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(password.length()<6){
            Toast.makeText(LoginActivity.this, "Mot de passe invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setTitle("Registration");
        progressDialog.setMessage("Creation de compte... ");
        progressDialog.setCancelable(false);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    final String uid=current_user.getUid();
                    String token_id = FirebaseInstanceId.getInstance().getToken();
                    Map userMap=new HashMap();
                    userMap.put("device_token",token_id);
                    userMap.put("name",name);

                    mDatabase.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if(task1.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Nouveau utilisateur", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Log.d("taask", task1.getException().getMessage());
                                Toast.makeText(LoginActivity.this, "Nom non enregistrer", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }
                else{
                    Log.d("useeer", task.getException().getMessage());
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Erreur de registration...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void login() {
        String eml = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        //---CHECKING IF EMAIL AND PASSWORD IS NOT EMPTY----
        if(TextUtils.isEmpty(eml)||TextUtils.isEmpty(pass)){
            Toast.makeText(LoginActivity.this, "Remplire tout les champs", Toast.LENGTH_SHORT).show();
            return ;
        }
        progressDialog.setTitle("Connexion");
        progressDialog.setMessage("Encours...");
        progressDialog.setCancelable(false);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        mauth.signInWithEmailAndPassword(eml,pass).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            String user_id=mauth.getCurrentUser().getUid();
                            String token_id= FirebaseInstanceId.getInstance().getToken();
                            Map addValue = new HashMap();
                            addValue.put("device_token",token_id);

                            mDatabase.child(user_id).updateChildren(addValue, new DatabaseReference.CompletionListener(){

                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                    if(databaseError==null){
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, databaseError.toString()  , Toast.LENGTH_SHORT).show();
                                        Log.e("Error is : ",databaseError.toString());

                                    }
                                }
                            });



                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == registertxt){
            viewPager.setCurrentItem(1);
        }
        else if(v == register){
            register();
        }
        else if(v == connexion){
            login();
        }
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 1){
            viewPager.setCurrentItem(0);
        }else {
            super.onBackPressed();
        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.connexionLayout;
                    break;
                case 1:
                    resId = R.id.registerLayout;
                    break;

            }
            return findViewById(resId);
        }
    }
}
