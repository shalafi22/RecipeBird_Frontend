package com.kemalm.recipebird;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import kotlin.text.Charsets;


public class PostRepository {
    public void getAllPosts(ExecutorService srv, Handler uiHandler) {
        srv.execute(() -> {
            String urlStr = "http://10.36.64.58:8080/api/v1/posts/getall";
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray postsArr = new JSONArray(buffer.toString());
                List<Post> postsList = new ArrayList<Post>();
                for (int i = 0; i < postsArr.length(); i++) {
                    JSONObject obj = postsArr.getJSONObject(i);
                    String author = obj.getJSONObject("author").getString("username");
                    String content = obj.getString("postContent");
                    String title = obj.getString("postTitle");
                    String id = obj.getString("id");
                    int numLikes =  Integer.parseInt(obj.getString("numLikes"));
                    JSONArray commentJSONArray = obj.getJSONArray("comments");
                    Comment[] comments = new Comment[commentJSONArray.length()];
                    for (int j = 0; j < commentJSONArray.length(); j++) {
                        JSONObject comment = commentJSONArray.getJSONObject(j);
                        comments[j] = new Comment(comment.getString("content"), comment.getJSONObject("authorUser").getString("username"));
                    }
                    postsList.add(new Post(author, content, title, numLikes, comments, id));
                }

                Message msg = new Message();
                msg.obj = postsList;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void getPostsByUser(ExecutorService srv, Handler uiHandler, String username) {
        srv.execute(() -> {
            String urlStr = "http://10.36.64.58:8080/api/v1/posts/getbyusername/" + username;

            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray postsArr = new JSONArray(buffer.toString());
                List<Post> postsList = new ArrayList<Post>();
                for (int i = 0; i < postsArr.length(); i++) {
                    JSONObject obj = postsArr.getJSONObject(i);
                    String author = obj.getJSONObject("author").getString("username");
                    String content = obj.getString("postContent");
                    String title = obj.getString("postTitle");
                    String id = obj.getString("id");
                    int numLikes =  Integer.parseInt(obj.getString("numLikes"));
                    JSONArray commentJSONArray = obj.getJSONArray("comments");
                    Comment[] comments = new Comment[commentJSONArray.length()];
                    for (int j = 0; j < commentJSONArray.length(); j++) {
                        JSONObject comment = commentJSONArray.getJSONObject(j);
                        comments[j] = new Comment(comment.getString("content"), comment.getJSONObject("authorUser").getString("username"));
                    }
                    postsList.add(new Post(author, content, title, numLikes, comments, id));
                }

                Message msg = new Message();
                msg.obj = postsList;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void likePost(ExecutorService srv, Handler uiHandler, String postId) {
        srv.execute(() -> {
            String urlStr = "http://10.36.64.58:8080/api/v1/posts/like/" + postId;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");

                int responseCode = conn.getResponseCode();

                Message msg = new Message();

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    msg.obj = false;
                } else {
                    msg.obj = true;
                }
                uiHandler.sendMessage(msg);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void createPost(ExecutorService srv, Handler uiHandler, String title, String content, String author) {
        srv.execute(() -> {
            String urlStr = "http://10.36.64.58:8080/api/v1/posts/create";
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject userObject = new JSONObject();
                userObject.put("title", title);
                userObject.put("author", author);
                userObject.put("content", content);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(userObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();
                Message msg = new Message();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }
                JSONObject obj = new JSONObject(buffer.toString());
                String author2 = obj.getJSONObject("author").getString("username");
                String content2 = obj.getString("postContent");
                String title2 = obj.getString("postTitle");
                String id = obj.getString("id");
                int numLikes =  Integer.parseInt(obj.getString("numLikes"));
                JSONArray commentJSONArray = obj.getJSONArray("comments");
                Comment[] comments = new Comment[commentJSONArray.length()];
                for (int j = 0; j < commentJSONArray.length(); j++) {
                    JSONObject comment = commentJSONArray.getJSONObject(j);
                    comments[j] = new Comment(comment.getString("content"), comment.getJSONObject("authorUser").getString("username"));
                }
                msg.obj = new Post(author2, content2, title2, numLikes, comments, id);

                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addComment(ExecutorService srv, Handler uiHandler, String postId, String content, String author) {
        srv.execute(() -> {
            String urlStr = "http://10.36.64.58:8080/api/v1/posts/addcomment/" + postId;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject userObject = new JSONObject();
                userObject.put("content", content);
                userObject.put("author", author);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(userObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }
                int responseCode = conn.getResponseCode();

                Message msg = new Message();

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    msg.obj = false;
                } else {
                    msg.obj = true;
                }
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //TODO: implement like, unlike, publish post and add comment

    /*
    //This function is for practice and notes
    void aRequestToApi(ExecutorService srv, Handler uiHandler, String param) {
        //boilerplate for calls to API.
        //srv is required to call API with a seperate thread, uiHandler for handling the response

        srv.execute(() -> {
            //This is needed to run the thread
            String url = "http://localhost:8080/api/" + param;
            try {
                URL u = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                Message msg = new Message();
                msg.obj = buffer.toString();
                uiHandler.sendMessage(msg);

                handler is declared in activity as follows
                * Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {

                        binding.txtOutput.setText(msg.obj.toString());
                        progressBar.setVisibility(View.INVISIBLE);
                        return true;
                    }
                });
                *
                *
                *
                *

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void postRequestWithJSON(ExecutorService srv, Handler uiHandler, String param1, String param2) {
        //GSON is Google's default JSON package

        srv.execute(() -> {
            String urlStr = "http://localhost:8080/api/post";
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject obj =  new JSONObject();
                obj.put("param1", param1);
                obj.put("param2", param2);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(obj.toString().getBytes(Charsets.UTF_8));
                writer.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject retObj = new JSONObject(buffer.toString());

                String fieldData = retObj.getString("fieldName");

                String toReturn = "Fields updated, returned: " + fieldData;

                Message msg = new Message();
                msg.obj = toReturn;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }


    void aRequestThatReturnsList(ExecutorService srv, Handler uiHandler, int numOfItems) {
        srv.execute(() -> {
            String urlStr = "http://localhost:8080/api/getList/" + numOfItems;
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedInputStream reader = new BufferedInputStream(conn.getInputStream());
                StringBuilder buffer = new StringBuilder();
                int chr = 0;

                //Need to read byte by byte
                while ((chr = reader.read()) != -1) {
                    buffer.append((char) chr);
                }

                JSONArray arr = new JSONArray(buffer.toString());
                List<Post> l = new ArrayList<Post>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    JSONArray commentArray = obj.getJSONArray("comments");
                    Comment[] comments = new Comment[commentArray.length()];
                    for (int j = 0; j < commentArray.length(); j++) {
                        comments[i] = new Comment(commentArray.getJSONObject(j).getString("content"), commentArray.getJSONObject(j).getString("author"));
                    }
                    l.add(new Post( obj.getString("author"), obj.getString("content"), obj.getString("title"), obj.getInt("likes"), comments ));
                }

                Message msg = new Message();
                msg.obj = l;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
    */



}
