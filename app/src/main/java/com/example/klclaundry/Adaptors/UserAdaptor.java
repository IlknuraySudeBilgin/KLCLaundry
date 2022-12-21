package com.example.klclaundry.Adaptors;

import androidx.navigation.NavType;

import java.io.Serializable;

public class UserAdaptor implements Serializable {
    private String name,id;
    private int Statement=0;

    public UserAdaptor() {

    }
    public UserAdaptor(String name, int Statement, String id) {
        this.name = name;
        this.Statement = Statement;
        this.id = id;
    }

    public String getId() {return  id;}
    public void setId(String id) {this.id=id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatement() {
        return Statement;
    }

    public void setStatement(int statement) {
        Statement = statement;
    }
}
