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

 //   @Test
//    public void getshoppinglistempty() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//        DBhelper db = new DBhelper(appContext);
//        List<ShoppingListDao> testList= db.getShoppingLis();
//        assertEquals(testList.size() == 0, true);
//    }

//    @Test
//    public void getshoppinglistNotempty() throws Exception {
//        // Context of the app under test.
//        try{
//            Context appContext = InstrumentationRegistry.getTargetContext();
//            DBhelper db = new DBhelper(appContext);
//
//            ShoppingListDao testS= new ShoppingListDao();
//            testS.setName("test");
//            testS.setDate(new Date());
//
//            db.addShoppingList(testS);
//            List<ShoppingListDao> testList= db.getShoppingLis();
//
//            assertEquals(testList.size() > 0, true);
//
//            for (ShoppingListDao s: testList) {
//                db.removeShoppingList(s.getId());
//            }
//
//        }
//        catch (Exception ex){
//            Assert.assertFalse(true);
//        }
//    }

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

    @Test
    public void getItemNotempty() throws Exception {
        // Context of the app under test.
        try{
            Context appContext = InstrumentationRegistry.getTargetContext();
            DBhelper db = new DBhelper(appContext);

            ItemDao item= new ItemDao();
            item.setStoreName("test store");
            item.setNoteText("test note");
            item.setName("test");
            item.setListId(100);
            item.setCrossOff(false);

            db.addItem(item);
            List<ItemDao> testList= db.getItems(100);

            assertEquals(testList.size() > 0, true);

            for (ItemDao s: testList) {
                db.removeItems(s.getId());
            }

        }
        catch (Exception ex){
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testUpdate() throws Exception {
        // Context of the app under test.
        try{
            Context appContext = InstrumentationRegistry.getTargetContext();
            DBhelper db = new DBhelper(appContext);

            ItemDao item= new ItemDao();
            item.setStoreName("test store");
            item.setNoteText("test note");
            item.setName("test");
            item.setListId(100);
            item.setCrossOff(false);

            db.addItem(item);

            List<ItemDao> testList= db.getItems(100);

            for (ItemDao s: testList) {
                s.setCrossOff(false);
                s.setNoteText("fsfs");
                s.setStoreName("fdywefdyewgdkjew");

                db.updateItem(s);
            }

            testList= db.getItems(100);

            for (ItemDao s: testList) {
                assertEquals( !s.getNoteText().equalsIgnoreCase(item.getNoteText()), true);
            }

            for (ItemDao s: testList) {
                db.removeItems(s.getId());
            }
        }
        catch (Exception ex){
            Assert.assertFalse(true);
        }
    }
}

/*
public class addItem
{
    private readonly IUnitOfWorkFactory _factory;

    public MyClass(IUnitOfWorkFactory factory)
    {
        _factory = factory;
    }

    public Messages addItem(Item item)
    {
        Messages resultMessage = Messages.Success;

        using (IUnitOfWork unitOfWork = _factory.GetUnitOfWork())
        {
            try
            {
                unitOfWork.ItemRep.Insert(item);

                unitOfWork.Commit();
            }

            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                resultMessage = Messages.DB_Failure;
            }


        }

        return resultMessage;
    }


    public void Test()
    {
        // Arrange
        var factoryMock = new Mock<IUnitOfWorkFactory>();
        var uowMock = new Mock<IUnitOfWork>();
        var repositoryMock = new Mock<IItemRepository>();

        factoryMock.Setup(f => f.GetUnitOfWork()).Returns(uowMock.Object);
        uowMock.Setup(u => u.ItemRep).Returns(repositoryMock.Object);

        var sut = new MyClass(factoryMock.Object);

        // Act
        var item = new Item();
        sut.addItem(item);


        // Assert
        repositoryMock.Verify(r => r.Insert(item), Times.Once);
        uowMock.Verify(u => u.Commit(), Times.Once);
    }
}
*/

