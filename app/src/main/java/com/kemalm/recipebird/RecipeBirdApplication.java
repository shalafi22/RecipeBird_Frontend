package com.kemalm.recipebird;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Threads are created from the pool of the application here.

public class RecipeBirdApplication extends Application {
    ExecutorService srv = Executors.newCachedThreadPool();
}
