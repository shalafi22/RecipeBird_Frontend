package com.kemalm.recipebird;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;


public class UserRepository {
    public void loginUser(ExecutorService srv, Handler uiHandler, String username, String password) {
        srv.execute(() -> {
            //adp: 10.51.19.216
            //ev: 10.36.64.58
            String urlStr = "http://10.36.64.58:8080/api/v1/users/login";
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject userObject = new JSONObject();
                userObject.put("username", username);
                userObject.put("password", password);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(userObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();
                int responseCode = conn.getResponseCode();
                Message msg = new Message();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    msg.obj = true;
                } else {
                    msg.obj = false;
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

    public void signupUser(ExecutorService srv, Handler uiHandler, String username, String password) {
        srv.execute(() -> {
            String urlStr = "http://10.36.64.58:8080/api/v1/users/register";
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject userObject = new JSONObject();
                userObject.put("username", username);
                userObject.put("password", password);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(userObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();
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
}
