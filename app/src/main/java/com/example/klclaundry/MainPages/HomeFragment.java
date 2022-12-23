package com.example.klclaundry.MainPages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klclaundry.Adaptors.FirebaseAdaptor;
import com.example.klclaundry.Adaptors.UserAdaptor;
import com.example.klclaundry.CardViews.UsersCardView;
import com.example.klclaundry.R;
import com.example.klclaundry.Services.PreferenceService;
import com.example.klclaundry.Services.pushNotService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private View root;
    private TextView homeText, StatementText;
    private ImageView StateImg;
    private FloatingActionButton addFloating;
    private RecyclerView recyclerView;
    private UsersCardView userCard;
    private  FirebaseAdaptor firebaseAdaptor;
    private  String name,id;
    private PreferenceService sp;
    private pushNotService notif;
    private UserAdaptor user,currentUser;
    private ArrayList<UserAdaptor> allUsers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home1, container, false);


        whatIsUserName();

        //Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
        defaultdefinetions();

        if (IsAdmin()) {
            definetionsAdmin();
            initializeForAdmin();
            loadDataforAdmin();

        } else {
            definetionsUser();
            initialize();
            events();
        }



        return root;
    }


    protected void defaultSizes() {
        StateImg.setScaleX(1F);
        StateImg.setScaleY(1F);
        StatementText.setScaleX(1F);
        StatementText.setScaleY(1F);
        StateImg.setImageResource(getResources().getIdentifier("resim1",
                                        "drawable",
                                                getActivity().getPackageName()));
    }
    protected void initialize() {
        defaultSizes();
        loadDataforUser();
        homeText.setText("LAUNDRY");
        StatementText.setText("her hangi bir işlem yok");
        homeText.setVisibility(View.VISIBLE);
        StatementText.setVisibility(View.VISIBLE);
        StateImg.setVisibility(View.VISIBLE);
        addFloating.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        //kullanıcın verileri databaseden çekilip yerine yerleşmesi için
    }

    protected void initializeForAdmin() {
        homeText.setVisibility(View.INVISIBLE);
        StatementText.setVisibility(View.INVISIBLE);
        StateImg.setVisibility(View.INVISIBLE);
        addFloating.setVisibility(View.INVISIBLE);


    }

    // tüm adminler burda tanımlı
    protected boolean IsAdmin() {
        if(id.equals("admin")) return true;
        else return false;

    }

    protected void events() {

        addFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.getStatement() != -1) {
                    Toast.makeText(getContext(),"zaten işlem sırasındasın! ",Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("name:2 ",name);
                    Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                    UserAdaptor user = new UserAdaptor(name,0,id);
                    addFloating.setVisibility(View.INVISIBLE);
                    firebaseAdaptor.add(user);
                }

            }
        });

        StateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.getStatement() == 3) {
                    //0 1 2 3 durumları mevcut
                    firebaseAdaptor.remove(currentUser.getId());
                    addFloating.setVisibility(View.VISIBLE);
                    initialize();


                }

            }
        });
    }


    protected void defaultdefinetions() {
        homeText = root.findViewById(R.id.homeText);
        StatementText = root.findViewById(R.id.homeTextState);
        StateImg = root.findViewById(R.id.homeView);
        addFloating = root.findViewById(R.id.homeAddfloating);
        recyclerView = root.findViewById(R.id.HomeUsersList);
        firebaseAdaptor = new FirebaseAdaptor();
        // kullanıcı şuan hangi aşamada


    }

    protected void definetionsUser() {
        homeText.setVisibility(View.VISIBLE);
        StatementText.setVisibility(View.VISIBLE);
        StateImg.setVisibility(View.VISIBLE);
        addFloating.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    protected void definetionsAdmin() {
        homeText.setVisibility(View.INVISIBLE);
        StateImg.setVisibility(View.INVISIBLE);
        StatementText.setVisibility(View.INVISIBLE);
        addFloating.setVisibility(View.INVISIBLE);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView = root.findViewById(R.id.HomeUsersList);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userCard = new UsersCardView(getContext(),allUsers);
        recyclerView.setAdapter(userCard);

    }


    protected void updateStates(int value) {
        if (value == 0) {
            StatementText.setText("henüz işleme girmedi");
            StateImg.setImageResource(R.drawable.basket);
            notif.senNotification("sıraya girdiniz","ÇAMAŞIRHANE");
        }
        else if(value == 1) {
            StatementText.setText("yıkanıyor");
            StateImg.setImageResource(R.drawable.washing);
            notif.senNotification("yıkanıyor","ÇAMAŞIRHANE");
        }
        else if(value == 2) {
            StatementText.setText("kurutuluyor");
            StateImg.setImageResource(R.drawable.laundry);
            notif.senNotification("kurutuluyor","ÇAMAŞIRHANE");
        }
        else if(value == 3) {
            StatementText.setText("çıktı");
            StatementText.setScaleX(1.5F);
            StatementText.setScaleY(1.5F);
            StateImg.setScaleX(1.5F);
            StateImg.setScaleY(1.5F);
            StateImg.setImageResource(R.drawable.towels);
            notif.senNotification("çıktı","ÇAMAŞIRHANE");
        }

    }

    protected void loadDataforUser() {
        firebaseAdaptor.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = new UserAdaptor(name,-1,id); // kullanıcı ekleme
                for (DataSnapshot d: snapshot.getChildren()) {
                    UserAdaptor tempUser = d.getValue(UserAdaptor.class);

                    if (tempUser.getId().equals(id)) {
                        currentUser = tempUser;
                        break;
                    }
                }
                name = currentUser.getName();
                pushToMemo();
                updateStates(currentUser.getStatement());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void loadDataforAdmin() {
        firebaseAdaptor.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allUsers.clear(); //liste bosaltılıyo
                for (DataSnapshot d: snapshot.getChildren()) {
                    user = d.getValue(UserAdaptor.class);
                    allUsers.add(user); // tekrar dolduruluyo eger bosaltılmazsa varolan listeye ekleme yapılır
                }
                userCard.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("database hatasi: ",error.getMessage()); // hatalar daha iyi anlaşılmak için loglanmalı
            }
        });
    }




    protected void pushToMemo() {
        //sp = getActivity().getSharedPreferences("USERNAME", MODE_PRIVATE);
        //SharedPreferences.Editor edt = sp.edit();
        //edt.putString("name",name);
        //edt.apply();
        sp.push("name",name);
        sp.push("id",id);
        sp.pushInt("state",currentUser.getStatement());
    }

    protected void whatIsUserName() {
        //sp = requireActivity().getSharedPreferences("USERNAME", MODE_PRIVATE);
        //id = sp.getString("id","");
        //name = id;
        sp = new PreferenceService(getActivity());
        id = sp.get("id",id);
        name = id;
    }

    @Override
    public void onResume() {
        //activite her degistiginde shared preference icin NULL deger doner
        //ama onResume devam eden programda her bir context 'ten kopan fragment olayı için
        //yeni bir activite id'si döndürecek ve null pointer hatası ortadan kalkacaktır
        super.onResume();
        whatIsUserName();
        notif = new pushNotService(getActivity());
    }
}