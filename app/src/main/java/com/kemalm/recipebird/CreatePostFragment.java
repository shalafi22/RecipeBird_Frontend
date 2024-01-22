package com.kemalm.recipebird;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

import java.util.List;
import java.util.concurrent.ExecutorService;


public class CreatePostFragment extends Fragment {
    View v;
    Handler createHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            PostViewModel viewModel = new ViewModelProvider(getActivity()).get(PostViewModel.class);
            LiveData<List<Post>> l = viewModel.getPostsData();
            List<Post> myList = l.getValue();
            myList.add((Post) msg.obj);
            viewModel.setPostsData(myList);
            Navigation.findNavController(v).navigate(R.id.action_createPostFragment_to_homeFragment);
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_create_post, container, false);
        EditText etTitle = v.findViewById(R.id.etCreateTitle);
        EditText etContent = v.findViewById(R.id.etCreateContent);
        Button btnSubmit = v.findViewById(R.id.btnCreateSubmit);

        btnSubmit.setOnClickListener(view -> {
            String title = etTitle.getText().toString();
            String content = etContent.getText().toString();
            ExecutorService srv = ((RecipeBirdApplication) getActivity().getApplication()).srv;
            PostRepository repository = new PostRepository();
            UserViewModel model = new ViewModelProvider(getActivity()).get(UserViewModel.class);
            String username = model.getLoggedInUser().getValue();
            repository.createPost(srv, createHandler, title, content, username);
        });
        return v;
    }
}