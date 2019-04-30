package com.example.mac.firebasechat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import adapter.UserListAdapter;
import model.User;

public class MainActivity extends AppCompatActivity {

    ArrayList<User> userList = new ArrayList<>();
    UserListAdapter userListAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialisation de la liste des user
        User user1 = new User();
        user1.setId("1");
        user1.setName("Mohamed Tariq");
        userList.add(user1);
        User user2 = new User();
        user2.setId("1");
        user2.setName("Abdellah Bn");
        userList.add(user2);
        User user3 = new User();
        user3.setId("1");
        user3.setName("Achraf Bounani");
        userList.add(user3);
        User user4 = new User();
        user4.setId("1");
        user4.setName("Khalid Elguim");
        userList.add(user4);

        recyclerView = findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        userListAdapter = new UserListAdapter(this, userList);
        recyclerView.setAdapter(userListAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Vous etes deconnecte", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }
        return true;
    }
}
