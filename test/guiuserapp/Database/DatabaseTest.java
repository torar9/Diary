/*
 * Copyright (C) 2016 Tomáš Silber
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package guiuserapp.Database;

import guiuserapp.User.UserData;
import javafx.collections.ObservableList;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Tomáš SIlber
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest 
{
    private static userDataDatabase database;
    
    private static UserData data;
    private static UserData newData;
    
    private static ObservableList<UserData> list;
    
    public DatabaseTest() 
    {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        database = new userDataDatabase();
        list = database.getContents();
        data = new UserData("test name+ěčšě","Text ěščěščěš ");
        newData = new UserData("new","new");
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
    }
    
    @Before
    public void setUp() 
    {
        System.out.println();
        System.out.print("Content list: ");
        for(UserData name : list)
        {
            System.out.print(name.getName() + " , ");
        }
        System.out.println(" Total size: " + list.size());
    }
    
    @After
    public void tearDown() 
    {
    }

    /**
     * Test of addData method, of class userDataDatabase.
     */
    @Test
    public void testAddData()
    {
        System.out.println("Starting testAddData method");
        try
        {
            System.out.println("    Adding " + data.getName() + " To database");
            database.addData(data);
            
            if(!list.contains(data))
                fail("Unable to add data");
        }
        catch(Exception e)
        {
            fail("Due to: " + e.getMessage());
        }
        
        System.out.println("Ending testAddData method");
    }
    
    /**
     * Test of addData method, of class userDataDatabase.
     */
    @Test
    public void testEditData()
    {
        System.out.println("Starting testEditData method");
        try
        {
            System.out.println("    Editing " + data.getName() + " to " + newData.getName());
            database.editData(data, newData);
            if(!list.contains(newData))
                fail("Unable to edit data");
        }
        catch(Exception e)
        {
            fail("Due to: " + e.getMessage());
        }
        
        System.out.println("Ending testEditData method");
    }
    
    /**
     * Test of removeData method, of class userDataDatabase.
     */
    @Test
    public void testRemoveData()
    {
        System.out.println("Starting testRemoveData method");
        try
        {
            System.out.println("    Removing " + newData.getName() + " from database");
            database.removeData(newData);
            if(!list.contains(newData))
                fail("File not deleted");
        }
        catch(Exception e)
        {
            fail("Due to: " + e.getMessage());
        }
        
        System.out.println("Ending testRemoveData method");
    }
    
    /**
     * Test of removeData method, of class userDataDatabase.
     */
    @Test
    public void testRestoreData()
    {
        System.out.println("Starting testRestoreData method");
        try
        {
            System.out.println("    Restoring previously removed data");
            database.restoreContent();
            if(!list.contains(newData))
                fail("Unable to restore data");
            
            database.removeData(newData);
        }
        catch(Exception e)
        {
            fail("Due to: " + e.getMessage());
        }
        
        System.out.println("Ending testRestoreData method");
    }
    
    /**
     *
     */
    @Test
    public void testExceptionName()
    {
        System.out.println("Starting testExceptionName method");
        
        System.out.println("    Testing empty string");
        try
        {
            database.addData(new UserData("", ""));
            fail("No exception to empty string");
        }
        catch(Exception e)
        {
            System.out.println("        Exception " + e + " SUCCEED");
        }
        
        System.out.println("    Testing whiteSpace string");
        try
        {
            database.addData(new UserData(" ", ""));
            fail("No exception to whitespace string ' '");
        }
        catch(Exception e)
        {
            System.out.println("        Exception " + e + " SUCCEED");
        }
        
        System.out.println("    Testing newLine string");
        try
        {
            database.addData(new UserData("\\n", ""));
            fail("No exception to \\\\n string '\\\\n '");
        }
        catch(Exception e)
        {
            System.out.println("        Exception " + e + " SUCCEED");
        }
        
        System.out.println("Ending testExceptionName method");
    }
    
}
