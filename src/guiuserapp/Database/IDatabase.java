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

/**
 * Interface for database, contains basic operations for adding, removing, restoring user data.
 * Methods throws Exception when user input is not valid. 
 * Exception contains message for user.
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
