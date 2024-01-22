package com.kemalm.recipebird;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    MutableLiveData<String> loggedInUser = new MutableLiveData<>();
    MutableLiveData<String> viewedUser;

    UserViewModel() {
        this.viewedUser = new MutableLiveData<>();
    }

    public LiveData<String> getLoggedInUser() {
        return this.loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {

        this.loggedInUser.setValue(loggedInUser);
    }

    public MutableLiveData<String> getViewedUser() {
        return viewedUser;
    }

    public void setViewedUser(String viewedUser) {
        this.viewedUser.setValue(viewedUser);
    }
}
