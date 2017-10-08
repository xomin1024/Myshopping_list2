package com.example.min.myshopping_list.data.DAO;

import java.util.Date;

/**
 * Created by mxion on 10/6/2017.
 */

public class ShoppingListDao {
    private int Id;
    private String Name;
    private Date Date;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }
}
