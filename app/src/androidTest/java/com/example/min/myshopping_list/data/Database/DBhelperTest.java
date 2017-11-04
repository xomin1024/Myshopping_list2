package com.example.min.myshopping_list.data.Database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.min.myshopping_list.data.DAO.ItemDao;
import com.example.min.myshopping_list.data.DAO.ShoppingListDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mxion on 11/3/2017.
 */
@RunWith(AndroidJUnit4.class)
public class DBhelperTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.min.myshopping_list", appContext.getPackageName());
    }

    @Test
    public void getshoppinglistempty() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        DBhelper db = new DBhelper(appContext);
        List<ShoppingListDao> testList= db.getShoppingLis();
        assertEquals(testList.size() == 0, true);
    }

    @Test
    public void getshoppinglistNotempty() throws Exception {
        // Context of the app under test.
        try{
            Context appContext = InstrumentationRegistry.getTargetContext();
            DBhelper db = new DBhelper(appContext);

            ShoppingListDao testS= new ShoppingListDao();
            testS.setName("test");
            testS.setDate(new Date());

            db.addShoppingList(testS);
            List<ShoppingListDao> testList= db.getShoppingLis();

            assertEquals(testList.size() > 0, true);

            for (ShoppingListDao s: testList) {
                db.removeShoppingList(s.getId());
            }

        }
        catch (Exception ex){
            Assert.assertFalse(true);
        }
    }

    @Test
    public void getItemempty() throws Exception {
        // Context of the app under test.
        try{
            Context appContext = InstrumentationRegistry.getTargetContext();
            DBhelper db = new DBhelper(appContext);
            List<ItemDao> testList= db.getItems(5);
            assertEquals(testList.size() == 0, true);
        }
        catch (Exception ex){
            Assert.assertFalse(true);
        }
    }
}
