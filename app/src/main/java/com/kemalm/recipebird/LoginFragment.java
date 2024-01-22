package com.kemalm.recipebird;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;


public class LoginFragment extends Fragment {
    View v;
    EditText etLoginUsername;
    EditText etLoginPassword;
    Handler loginHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if ((Boolean) msg.obj) {
                UserViewModel model = new ViewModelProvider(getActivity()).get(UserViewModel.class);
                model.setLoggedInUser(etLoginUsername.getText().toString());
                Bundle b = new Bundle();
                b.putString("username", etLoginUsername.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_homeActivity, b);
            } else {
                Toast toast = Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true;
        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login, container, false);

        Button toSignup = v.findViewById(R.id.btnToSignup);
        Button btnLogin = v.findViewById(R.id.btnSubmitLogin);
        etLoginUsername = v.findViewById(R.id.etLoginUsername);
        etLoginPassword = v.findViewById(R.id.etLoginPassword);

        toSignup.setOnClickListener((view) -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment);
        });
        btnLogin.setOnClickListener((view) -> {
            ExecutorService srv = ((RecipeBirdApplication) getActivity().getApplication()).srv;
            UserRepository userRepository = new UserRepository();
            userRepository.loginUser(srv, loginHandler, etLoginUsername.getText().toString(), etLoginPassword.getText().toString());
        });

        return v;
    }
}