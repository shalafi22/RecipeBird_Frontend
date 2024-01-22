package com.kemalm.recipebird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private final List<Comment> localDataSet;
    Context ctx;

    public CommentAdapter(List<Comment> localDataSet, Context ctx) {
        this.localDataSet = localDataSet;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment_tile, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.getUsername().setText(localDataSet.get(position).getAuthorUser());
        holder.getContent().setText(localDataSet.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView username;
        private final TextView content;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            this.username = itemView.findViewById(R.id.tvCommentUsername);
            this.content = itemView.findViewById(R.id.tvCommentContent);
        }

        public TextView getUsername() {
            return username;
        }

        public TextView getContent() {
            return content;
        }
    }
}
