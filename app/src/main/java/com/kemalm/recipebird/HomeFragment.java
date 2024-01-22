package com.kemalm.recipebird;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class HomeFragment extends Fragment {
    Handler recHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            PostViewModel postViewModel = new ViewModelProvider(getActivity()).get(PostViewModel.class);
            postViewModel.setPostsData((List<Post>) msg.obj);
            PostTileAdapter adp = new PostTileAdapter(postViewModel.getPostsData().getValue(), getActivity(), false);
            rvHomePosts.setAdapter(adp);
            rvHomePosts.setLayoutManager(new LinearLayoutManager(getActivity()));
            return true;
        }
    });

    RecyclerView rvHomePosts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        rvHomePosts = v.findViewById(R.id.rvHomePosts);
        TextView tvHomeTitle = v.findViewById(R.id.tvHomeTitle);
        Button btnAddPost = v.findViewById(R.id.btnAddPost);

        PostRepository postsRepo = new PostRepository();
        ExecutorService srv = ((RecipeBirdApplication)getActivity().getApplication()).srv;
        postsRepo.getAllPosts(srv, recHandler);

        btnAddPost.setOnClickListener(view -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_createPostFragment);
        });

        return v;
    }
}