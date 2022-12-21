package com.example.klclaundry.CardViews;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klclaundry.Adaptors.FirebaseAdaptor;
import com.example.klclaundry.Adaptors.UserAdaptor;
import com.example.klclaundry.R;

import java.util.ArrayList;

public class UsersCardView extends RecyclerView.Adapter<UsersCardView.HolderDesign> {

    private Context cnt;
    private ArrayList<UserAdaptor> users;
    private FirebaseAdaptor firebaseAdaptor = new FirebaseAdaptor();

    public UsersCardView(Context cnt, ArrayList<UserAdaptor> users) {
        this.cnt = cnt;
        this.users = users;
    }

    @NonNull
    @Override
    public HolderDesign onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cnt).inflate(R.layout.user_list, parent, false);
        return new HolderDesign(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderDesign holder, int position) {
        holder.but1.setBackgroundColor(12);
        holder.but2.setBackgroundColor(12);
        holder.but3.setBackgroundColor(12);
        holder.but4.setBackgroundColor(12);

        UserAdaptor user = users.get(position);

        if (user.getStatement() == 0) {
            holder.but1.setScaleX(1.2F);
            holder.but1.setScaleY(1.2F);
        }


        if (user.getStatement() == 1) {
            holder.but2.setScaleX(1.2F);
            holder.but2.setScaleY(1.2F);
        }

        if (user.getStatement() == 2) {
            holder.but3.setScaleX(1.2F);
            holder.but3.setScaleY(1.2F);
        }

        if (user.getStatement() == 3) {
            holder.but4.setScaleX(1.2F);
            holder.but4.setScaleY(1.2F);
        }
        holder.userName.setText(user.getName());

        holder.but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultState(holder,1);
                user.setStatement(0);
                firebaseAdaptor.update(user);
            }
        });

        holder.but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultState(holder,2);
                user.setStatement(1);
                firebaseAdaptor.update(user);
            }
        });

        holder.but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultState(holder,3);
                user.setStatement(2);
                firebaseAdaptor.update(user);
            }
        });

        holder.but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultState(holder,4);
                user.setStatement(3);
                firebaseAdaptor.update(user);
            }
        });
    }

    public void defaultState(HolderDesign holder, int i) {
        if(i==1) {
            holder.but1.setScaleX(1.2F);
            holder.but1.setScaleY(1.2F);
            holder.but2.setScaleX(1F);
            holder.but2.setScaleY(1F);
            holder.but3.setScaleX(1F);
            holder.but3.setScaleY(1F);
            holder.but4.setScaleX(1F);
            holder.but4.setScaleY(1F);
        }
        if(i==2) {
            holder.but1.setScaleX(1F);
            holder.but1.setScaleY(1F);
            holder.but2.setScaleX(1.2F);
            holder.but2.setScaleY(1.2F);
            holder.but3.setScaleX(1F);
            holder.but3.setScaleY(1F);
            holder.but4.setScaleX(1F);
            holder.but4.setScaleY(1F);
        }
        if(i==3) {
            holder.but1.setScaleX(1F);
            holder.but1.setScaleY(1F);
            holder.but2.setScaleX(1F);
            holder.but2.setScaleY(1F);
            holder.but3.setScaleX(1.2F);
            holder.but3.setScaleY(1.2F);
            holder.but4.setScaleX(1F);
            holder.but4.setScaleY(1F);
        }
        if(i==4) {
            holder.but1.setScaleX(1F);
            holder.but1.setScaleY(1F);
            holder.but2.setScaleX(1F);
            holder.but2.setScaleY(1F);
            holder.but3.setScaleX(1F);
            holder.but3.setScaleY(1F);
            holder.but4.setScaleX(1.2F);
            holder.but4.setScaleY(1.2F);
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class HolderDesign extends RecyclerView.ViewHolder {
        private TextView userName;
        private CardView cv;
        private ImageView but1, but2, but3, but4;

        public HolderDesign(@NonNull View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.CardView);
            userName = itemView.findViewById(R.id.usersName);

            but1 = itemView.findViewById(R.id.userStateBut1);
            but2 = itemView.findViewById(R.id.userStateBut2);
            but3 = itemView.findViewById(R.id.userStateBut3);
            but4 = itemView.findViewById(R.id.userStateBut4);

        }
    }
}
