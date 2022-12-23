package com.example.klclaundry.MainPages.SideMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klclaundry.LOGSCREEN.SignUpPage;
import com.example.klclaundry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class changePage extends AppCompatActivity {

    private FirebaseAuth AuthUI;
    private FirebaseUser user;
    private Button deleteAcc,Confirm;
    private EditText username,password,email;
    private TextView id,trust;
    private String strUsername,strPass,strEmail;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_page);

        definetions();
        Initialize();
        events();
    }

    protected void events() {
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp = getSharedPreferences("userName",MODE_PRIVATE);

                if (sp.getString("user","").equals("admin")) {
                    Toast.makeText(getApplicationContext(),"admin için geçersiz işlemler",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"silindi", Toast.LENGTH_SHORT).show();
                    deleteAccount();
                }
            }
        });

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String admin = sp.getString("name","");
                if (admin.equals("admin")) {
                    Toast.makeText(getApplicationContext(),"admin için geçersiz işlemler",Toast.LENGTH_SHORT).show();
                } else {
                    take();
                    updateEmail(strEmail);
                    updatePass(strPass);
                    updateUserName();
                }

            }
        });
    }


    protected  void updateUserName() {

        if (strUsername.isEmpty()) {
            Toast.makeText(getApplicationContext(),"kullanıcı adı boş bırakılamaz",Toast.LENGTH_SHORT).show();
            strUsername = sp.getString("name","");
        }

        SharedPreferences.Editor edt = sp.edit();
        edt.putString("name",strUsername);
        edt.apply();
    }

    protected void take() {
        strUsername = username.getText().toString();
        strPass = username.getText().toString();
        strEmail = email.getText().toString();
    }

    protected void definetions() {
        AuthUI = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        deleteAcc = findViewById(R.id.deleteButton);
        username = findViewById(R.id.AccUsername);
        password = findViewById(R.id.AccPass);
        email = findViewById(R.id.AccEmail);
        id = findViewById(R.id.accId);
        trust = findViewById(R.id.accTrust);
        Confirm = findViewById(R.id.Accconfirm);
        sp = getSharedPreferences("USERNAME",MODE_PRIVATE);

    }

    protected void Initialize() {
        String tempname = sp.getString("name","");
        username.setText(tempname);
        password.setHint("yeni parola giriniz");
        email.setText(user.getEmail());
        trust.setText(String.valueOf("email dogrulama: "+user.isEmailVerified()));
        id.setText(String.valueOf("kullanıcı id: "+user.getUid()));
    }

    protected void sendVertification() {
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"emailinizi kontrol ediniz",Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void updateEmail(String email) {
        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"email basarıyla guncellendi",Toast.LENGTH_SHORT).show();
            }

        });
    }
    protected void updatePass(String password) {
        if(password.isEmpty() || password.length()<6) {
            Toast.makeText(getApplicationContext(),"lutfen kurallara uyarak tekrar deneyin",Toast.LENGTH_SHORT).show();
        } else {
            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"parola basarıyla guncellendi",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    protected void deleteAccount() {
        user.delete();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}