/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiuserapp.Database;

import guiuserapp.User.UserData;
import javafx.collections.ObservableList;

/**
 * Interface for database, contains basic operations for adding, removing, restoring user data.
 * Methods throws Exception when user input is not valid. 
 * Exception message contains message for user.
 * @author Tomáš Silber
 */
public interface IDatabase 
{
    /**
     * 
     * @param data
     * @throws Exception if something went wrong, contains message for user.
     */
    void addData(UserData data) throws Exception;
    
    /**
     * Replaces the old data with new data.
     * @param oldData to be replaced with newData
     * @param newData replaces the oldData
     * @throws Exception if something went wrong, contains message for user.
     */
    void editData(UserData oldData, UserData newData) throws Exception;
    
    /**
     * Removes user data.
     * @param data
     * @throws Exception if something went wrong, contains message for user.
     */
    void removeData(UserData data) throws Exception;
    
    /**
     * Restore previously deleted data.
     * @throws Exception if something went wrong, contains message for user.
     */
    void restoreContent() throws Exception;
    
    /**
     * 
     * @return Returns list of user dat. 
     */
    ObservableList<UserData> getContents();
}