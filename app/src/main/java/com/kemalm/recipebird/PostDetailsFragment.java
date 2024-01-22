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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class PostDetailsFragment extends Fragment {
    TextView numLikes;
    TextView numComments;
    Handler likeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Boolean res = (Boolean) msg.obj;
            if (res) {
                Toast toast = Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT);
                toast.show();
                String[] parts = numLikes.getText().toString().split(" ");
                String numberString = parts[0];
                int number = Integer.parseInt(numberString);
                number++;
                String result = number + " Likes";
                numLikes.setText(result);
            } else {
                Toast toast = Toast.makeText(getActivity(), "Couldn't like", Toast.LENGTH_SHORT);
                toast.show();
            }
            return true;
        }
    });

    Handler commentHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Boolean res = (Boolean) msg.obj;
            if (res) {
                Toast toast = Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT);
                toast.show();
                String[] parts = numComments.getText().toString().split(" ");
                String numberString = parts[0];
                int number = Integer.parseInt(numberString);
                number++;
                String result = number + " Comments";
                numComments.setText(result);

                Comment c = new Comment(etComment.getText().toString(),(new ViewModelProvider(getActivity()).get(UserViewModel.class)).getLoggedInUser().getValue() );
                Comment[] l = (new ViewModelProvider(getActivity()).get(PostViewModel.class)).getSelectedItem().getValue().getComments();
                List<Comment> myL = new ArrayList<>();
                myL.addAll(Arrays.asList(l));
                myL.add(c);
                rvComments.setAdapter(new CommentAdapter(myL, getActivity()));
                rvComments.setLayoutManager(new LinearLayoutManager(getActivity()));

                etComment.setText("");
            } else {
                Toast.makeText(getActivity(), "Couldn't add comment", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });

    EditText etComment;
    RecyclerView rvComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_details, container, false);
        TextView title = v.findViewById(R.id.tvDetailsTitle);
        TextView content = v.findViewById(R.id.tvDetailsContent);
        numLikes = v.findViewById(R.id.tvDetailsNumLikes);
        numComments = v.findViewById(R.id.tvDetailsNumComments);
        Button btnProfile = v.findViewById(R.id.btnDetailsSeeProfile);
        Button btnLike = v.findViewById(R.id.btnDetailsLike);
        rvComments = v.findViewById(R.id.rvDetailsComments);
        Button btnComment = v.findViewById(R.id.btnDetailsComment);
        etComment = v.findViewById(R.id.etDetailsComment);



        PostViewModel postViewModel = new ViewModelProvider(getActivity()).get(PostViewModel.class);
        Post p = postViewModel.getSelectedItem().getValue();
        btnProfile.setText("See " + p.getAuthor());
        List<Comment> c = new ArrayList<>();
        for (int i = 0; i < p.getComments().length; i++) {
            c.add(p.getComments()[i]);
        }
        rvComments.setAdapter(new CommentAdapter(c, getActivity()));
        rvComments.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserViewModel userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        btnProfile.setOnClickListener((view) -> {

            userViewModel.setViewedUser(p.getAuthor());
            Navigation.findNavController(v).navigate(R.id.action_postDetailsFragment_to_profilePageFragment);
        });

        btnLike.setOnClickListener(view -> {
            ExecutorService srv = ((RecipeBirdApplication) getActivity().getApplication()).srv;
            PostRepository repo = new PostRepository();
            repo.likePost(srv, likeHandler, p.getId());

        });

        btnComment.setOnClickListener(view -> {
            String comment = etComment.getText().toString();
            if (comment.equals("")) {
                Toast.makeText(getActivity(), "Write something in the comment!", Toast.LENGTH_SHORT).show();
            } else {
                PostRepository repo = new PostRepository();
                ExecutorService srv = ((RecipeBirdApplication) getActivity().getApplication()).srv;
                String id = postViewModel.getSelectedItem().getValue().getId();
                repo.addComment(srv, commentHandler, id, etComment.getText().toString(), userViewModel.getLoggedInUser().getValue());
            }
        });

        title.setText(p.getPostTitle());
        content.setText(p.getPostContent());
        numLikes.setText(p.getNumLikes() + " Likes");
        numComments.setText(p.getComments().length + " Comments");



        return v;
    }
}