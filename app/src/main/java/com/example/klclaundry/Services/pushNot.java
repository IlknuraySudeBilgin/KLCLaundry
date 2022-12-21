package com.example.klclaundry.Services;

import static androidx.fragment.app.FragmentManager.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class pushNot {

    private String token="";

    public void connect() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {

            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    token = task.getResult();

                    Log.i("token: ", token);
                }
            }
        });
    }

    public String getToken() {
        return this.token;
    }
}
