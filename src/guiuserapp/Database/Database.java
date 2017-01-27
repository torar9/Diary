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
 */
package guiuserapp.Database;

import guiuserapp.User.UserData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Store user data into file
 * @author Tomáš Silber
 */
public class Database implements IDatabase
{
    private String DataLocation;
    private final ObservableList<UserData> content = FXCollections.observableArrayList();
    private final ArrayList<String> dataNames = new ArrayList<>();
    private UserData deletedContent;
    
    public Database()
    {
        setPath();
    }
    
    public Database(String path)
    {
        DataLocation = path;
    }
    
    public void addData(UserData data) throws Exception
    {
        if(isSaved(data.getName()))
            throw new Exception("Data name already saved!");
        if(data.getName().contentEquals(""))
            throw new Exception("Data name cannot be empty!");
        if(isSaved(data.getName()))
            throw new Exception("Data already saved!");
        if(data.getName().contentEquals(" ") || data.getName().contentEquals("\\") || data.getName().contentEquals("\\n"))
            throw new Exception("Invalid characters!");
        if(!saveFile(data.getName(), data.getText()))
            throw new Exception("Unable to save data!");
        
        content.add(data);
        dataNames.add(data.getName());
    }
    
    public void removeData(UserData data) throws Exception 
    {
        if(data == null)
            return;
        
        if(removeFile(data))
        {
            deletedContent = data;
            content.remove(data);
            dataNames.remove(data.getName());
        }
        else
            throw new Exception("Unable to delete data!");
    }
    
    public void restoreContent() throws Exception
    {
        addData(deletedContent);
    }

    public void editData(UserData oldData, UserData newData) throws Exception 
    {
        if(isSaved(newData.getName()) && !oldData.getName().contains(newData.getName()))
            throw new Exception("Data name already saved!");
        if(newData.getName().contentEquals(""))
            throw new Exception("Data name cannot be empty!");
        if(newData.getName().contentEquals(" ") 
                || newData.getName().contentEquals("\\") 
                || newData.getName().contentEquals("\\n") 
                || newData.getName().contentEquals("."))
            throw new Exception("Invalid characters!");
            
            editFile(oldData, newData);
    }

    public ObservableList<UserData> getContents() 
    {
        loadFiles();
        return content;
    }
    
    /**
     * Removes file
     * @param userData
     * @return 
     */
    private boolean removeFile(UserData userData)
    {
        File floc = new File(DataLocation + File.separator + userData.getName() + ".txt");
        return floc.delete();
    }
    
    /**
     * Sets new data
     * @param oldData
     * @param newData
     * @throws Exception 
     */
    private void editFile(UserData oldData, UserData newData)
    {
        if(!newData.getName().contentEquals(oldData.getName()))
        {
            File floc = new File(DataLocation + File.separator + oldData.getName() + ".txt");
            floc.renameTo(new File(DataLocation + File.separator + newData.getName() + ".txt"));
            
            content.remove(oldData);
            content.add(newData);
            dataNames.remove(oldData.getName());
            dataNames.add(newData.getName());
        }
        
        if(!newData.getText().contentEquals(oldData.getText()))
        {
            saveFile(newData.getName(), newData.getText());
            content.remove(oldData);
            content.add(newData);
        }
    }
    
    /**
     * 
     * @param name of file
     * @param text inside file
     * @throws Exception 
     */
    private boolean saveFile(String name, String text)
    {
        name = name.toLowerCase();
        
        File fUserData = new File(DataLocation + File.separator + name);
        if(!fUserData.exists())
        {
            try(BufferedWriter fileBufferW = new BufferedWriter(new FileWriter(DataLocation + File.separator + name + ".txt")))
            {
                text = Encryptor.Encrypt(text);
                
                fileBufferW.write(text);
                fileBufferW.flush();
                fileBufferW.close();
            }
            
            catch(Exception e)
            {
                System.err.println("Error, unable to save." + e);
                return false;
            }
        }     
        else
        {
            System.err.println("File already exists!");
            return false;
        }
        
        return true;
    }
    
    private boolean isSaved(String name)
    {
        return dataNames.contains(name);
    }
    
/**
 * Loads user data
 */
    private void loadFiles()
    {
        File fUserData = new File(DataLocation + File.separator);
        if(!fUserData.exists())
        {
            fUserData.mkdirs();
        }
        else
        {
            File[] listOfFiles = fUserData.listFiles();
            for(File file : listOfFiles)
            {
                 if(file.isFile())
                {
                    String name = file.getName();
                   int pos = name.lastIndexOf(".");
                    if(pos > 0)
                    name = name.substring(0, pos);
                     
                    String prefileText = "";
                    String fileText = "";
                    try(BufferedReader fileBufferR = new BufferedReader(new FileReader(DataLocation + File.separator + file.getName())))
                    {  
                        while((prefileText = fileBufferR.readLine()) != null)
                        {
                            fileText += prefileText + "\n";
                        }
                    
                        fileText = Encryptor.Decrypt(fileText);
                    
                        UserData userDataAdd = new UserData(name, fileText);
                        content.add(userDataAdd);
                        dataNames.add(userDataAdd.getName());
                    }
                 
                    catch(Exception e)
                    {
                        System.err.println("Error, unable to open/save file." + e);
                    }
                }
            }
        }
    }
    
    /**
     * Sets path to save/load destination
     */
    private void setPath()
    {
        String OS = System.getProperty("os.name").toUpperCase();
        
        if(OS.contains("LINUX"))
            DataLocation = System.getProperty("user.home") + File.separator + ".torar/" + "save";
        else if(OS.contains("WIN") )
            DataLocation = System.getenv("APPDATA") + File.separator + "torar/" + "save";
        else
            DataLocation = System.getProperty("user.home") + File.separator + ".torar/" + "save";
    }
}