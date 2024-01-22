package com.kemalm.recipebird;

import java.io.Serializable;
import java.util.Arrays;

public class Post implements Serializable {
    private String author;
    private String postContent;
    private String postTitle;
    private int numLikes;
    private Comment[] comments;
    private String id;

    Post() {}


    public Post(String author, String postContent, String postTitle, int numLikes, Comment[] comments, String id) {
        this.author = author;
        this.postContent = postContent;
        this.postTitle = postTitle;
        this.numLikes = numLikes;
        this.comments = comments;
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public Comment[] getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "author='" + author + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", numLikes=" + numLikes +
                ", comments=" + Arrays.toString(comments) +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

