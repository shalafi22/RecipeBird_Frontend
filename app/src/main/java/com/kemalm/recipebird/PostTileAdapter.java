package com.kemalm.recipebird;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostTileAdapter extends RecyclerView.Adapter<PostTileAdapter.ViewHolder> {

    private final List<Post> localDataSet;
    Context ctx;
    Boolean flag;

    public PostTileAdapter(List<Post> localDataSet, Context ctx, Boolean flag) {
        this.localDataSet = localDataSet;
        this.ctx = ctx;
        this.flag = flag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_tile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTxtPostTileTitle().setText(localDataSet.get(position).getPostTitle());
        holder.getTxtPostTileAuthor().setText(localDataSet.get(position).getAuthor());
        holder.getRow().setOnClickListener(v -> {
            PostViewModel postViewModel = new ViewModelProvider((AppCompatActivity) ctx).get(PostViewModel.class);
            postViewModel.setSelectedItem(localDataSet.get(position));

            NavController navController = Navigation.findNavController((AppCompatActivity) ctx, R.id.fragmentContainerViewHome);
            navController.navigate(flag? R.id.action_profilePageFragment_to_postDetailsFragment : R.id.action_homeFragment_to_postDetailsFragment);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtPostTileTitle;
        private final ConstraintLayout row;
        private final TextView txtPostTileAuthor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPostTileAuthor = itemView.findViewById(R.id.txtPostTileAuthor);
            txtPostTileTitle = itemView.findViewById(R.id.txtPostTileTitle);
            row = itemView.findViewById(R.id.clPostRow);
        }

        public TextView getTxtPostTileTitle() {
            return txtPostTileTitle;
        }

        public ConstraintLayout getRow() {
            return row;
        }

        public TextView getTxtPostTileAuthor() {
            return txtPostTileAuthor;
        }
    }
}
