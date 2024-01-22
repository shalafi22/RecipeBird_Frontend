package com.kemalm.recipebird;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class ProfilePageFragment extends Fragment {
    View v;
    Handler loadPostsHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            RecyclerView rvPosts = v.findViewById(R.id.rvProfilePosts);
            List<Post> posts = (List<Post>) msg.obj;
            rvPosts.setAdapter(new PostTileAdapter(posts, getActivity(), true));
            rvPosts.setLayoutManager(new LinearLayoutManager(getActivity()));
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_profile_page, container, false);
        TextView tvTitle = v.findViewById(R.id.tvProfileName);

        PostRepository repo = new PostRepository();
        ExecutorService srv = ((RecipeBirdApplication) getActivity().getApplication()).srv;
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        String u = userViewModel.getViewedUser().getValue();
        tvTitle.setText(u);
        repo.getPostsByUser(srv, loadPostsHandler, u);


        return v;
    }
}