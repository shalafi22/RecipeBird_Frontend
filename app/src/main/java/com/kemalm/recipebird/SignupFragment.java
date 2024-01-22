package com.kemalm.recipebird;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;


public class SignupFragment extends Fragment {
    View v;
    Handler signupHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if ((boolean) msg.obj) {
                Toast toast = Toast.makeText(getActivity(), "Account created!", Toast.LENGTH_SHORT);
                toast.show();
                Navigation.findNavController(v).navigate(R.id.action_signupFragment_to_loginFragment);
            } else {
                Toast toast = Toast.makeText(getActivity(), "Failed to signup!", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_signup, container, false);
        Button toLogin = v.findViewById(R.id.btnToLogin);
        Button btnSignup = v.findViewById(R.id.btnSubmitSignup);
        EditText etUsername = v.findViewById(R.id.etSignupUsername);
        EditText etPassword = v.findViewById(R.id.etSignupPassword);
        toLogin.setOnClickListener(view -> {
            Navigation.findNavController(v).navigate(R.id.action_signupFragment_to_loginFragment);
        });
        btnSignup.setOnClickListener(view -> {
            ExecutorService srv = ((RecipeBirdApplication) getActivity().getApplication()).srv;
            UserRepository repo = new UserRepository();
            repo.signupUser(srv, signupHandler, etUsername.getText().toString(), etPassword.getText().toString() );
        });

        return v;
    }
}