package com.example.min.myshopping_list.data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.min.myshopping_list.data.DAO.ItemDao;
import com.example.min.myshopping_list.data.DAO.ShoppingListDao;

/**
 * Created by mxion on 9/21/2017.
 */

public class DBhelper extends SQLiteOpenHelper {


    private static final int version = 1;
    private static final String database_name = "ShoppingList.db";

    public DBhelper(Context context) {
        super(context, database_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataSource.CREATE_LIST_TABLE);
        db.execSQL(DataSource.CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DataSource.DELETE_LIST_TABLE);
        db.execSQL(DataSource.DELETE_ITEM_TABLE);

        db.execSQL(DataSource.CREATE_LIST_TABLE);
        db.execSQL(DataSource.CREATE_ITEM_TABLE);
    }

    public List<ShoppingListDao> getShoppingLis(){
        List<ShoppingListDao> shoppingListDaos= new ArrayList<ShoppingListDao>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,name,date FROM ShoppingList ", null);

        if(c.moveToFirst()){
            do{
                ShoppingListDao shoppingListDao= new ShoppingListDao();

                shoppingListDao.setId(c.getInt(0));
                shoppingListDao.setName(c.getString(1));
                shoppingListDao.setDate(new Date(c.getLong(2)));

                shoppingListDaos.add(shoppingListDao);
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return shoppingListDaos;
    }

    public Boolean addShoppingList(ShoppingListDao shoppingListDao){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = false;

        ContentValues values = new ContentValues();

        values.put("Name", shoppingListDao.getName());
        values.put("Date", shoppingListDao.getDate().getTime());

        createSuccessful = db.insert("ShoppingList", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public void removeShoppingList(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("ShoppingList", "Id=?", new String[]{Long.toString(id)});

        db.close();
    }

    public void removeShoppingListItems(long listId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Item", "List_Id=?", new String[]{Long.toString(listId)});

        db.close();
    }

    public Boolean addItem(ItemDao itemDao){
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = false;

        ContentValues values = new ContentValues();

        values.put("List_Id", itemDao.getListId());
        values.put("Name", itemDao.getName());
        values.put("Store_Name", itemDao.getStoreName());
        values.put("Cross_Off", itemDao.isCrossOff()? 1:0);
        values.put("Note", itemDao.getNoteText());

        createSuccessful = db.insert("Item", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public List<ItemDao> getItems(int shopingListId){
        List<ItemDao> itemDaos= new ArrayList<ItemDao>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Id, List_Id, Name, Store_Name, Cross_Off, Note  FROM Item where List_Id = " + shopingListId, null);

        if(c.moveToFirst()){
            do{
                ItemDao itemDao= new ItemDao();

                itemDao.setId(c.getInt(0));
                itemDao.setListId(c.getInt(1));
                itemDao.setName(c.getString(2));
                itemDao.setStoreName(c.getString(3));
                itemDao.setCrossOff(c.getInt(4)==1);
                itemDao.setNoteText(c.getString(5));

                itemDaos.add(itemDao);
            }while(c.moveToNext());
        }
        c.close();
        db.close();

        return itemDaos;
    }

    public void removeItems(long Id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("Item", "Id=?", new String[]{Long.toString(Id)});

        db.close();
    }

}

