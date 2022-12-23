package com.example.klclaundry.Adaptors;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseAdaptor {

    private DatabaseReference dbref;
    private DatabaseReference dbref2;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://klclaundry-f42ea-default-rtdb.europe-west1.firebasedatabase.app/"); //database adresi

    public FirebaseAdaptor () {
        dbref = database.getReference(); //Nesne cagrildiginda adaptor olusturuluyor
        dbref2 = database.getReference("laundry");
    }

    public Task<Void> add (UserAdaptor user) {
        return dbref.child(user.getId()).setValue(user);//kullanici bilgilerini tutmak için oluşturulan sınıf database'e itiliyo
    }

    public Task<Void> LaundryOpenOrClose(boolean state) {
        return dbref2.child("laundry").setValue(state);
    }

    public Task<Void> remove(String id) {
        return dbref.child(id).removeValue();
    }

    public Query get()  {
        return dbref.orderByKey(); //database de gezinmek için bir sorgu nesnesi dönmeli Query turunde bu nesne cagridigi yerde istenilen soruguyu getiriyo
    }

    public Task<Void> update(UserAdaptor user) {
        return dbref.child(user.getId()).setValue(user);
    }

    //https://firebase.google.com/ firebase icin dokumantasyon

}
