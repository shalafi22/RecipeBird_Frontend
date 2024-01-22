package com.kemalm.recipebird;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String username = getIntent().getExtras().getString("username");
        UserViewModel model = new ViewModelProvider(this).get(UserViewModel.class);
        model.setLoggedInUser(username);
    }
}