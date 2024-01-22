package com.kemalm.recipebird;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class PostViewModel extends ViewModel {
    MutableLiveData<List<Post>> postsData;
    MutableLiveData<Post> selectedItem = new MutableLiveData<>();


    public PostViewModel() {

        postsData = new MutableLiveData<>();


    }
    public LiveData<Post> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Post p) {
        this.selectedItem.setValue(p);
    }

    public LiveData<List<Post>> getPostsData() {
        return this.postsData;
    }

    public void setPostsData(List<Post> posts) {
        postsData.setValue(posts);
    }
}
