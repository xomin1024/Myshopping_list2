package com.example.min.myshopping_list.data.Database;

/**
 * Created by mxion on 9/21/2017.
 */

public class DataSource {

    public static String CREATE_LIST_TABLE ="CREATE TABLE ShoppingList (" +
            " Id integer PRIMARY KEY," +
            " Name text NOT NULL," +
            " Date text NOT NULL)";

    public static String CREATE_ITEM_TABLE ="CREATE TABLE  Item(" +
            " id integer PRIMARY KEY," +
            " List_Id integer NOT NULL," +
            " Name text NOT NULL,"+
            "Store_Name text  NULL,"+
            "Cross_off integer  NULL,"+
            "Note text  NULL)";

    public static String DELETE_LIST_TABLE ="delete TABLE ShopingList";
    public static String DELETE_ITEM_TABLE ="delete TABLE Item";
}
