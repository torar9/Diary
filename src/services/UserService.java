/*
 * Copyright (C) 2017 Tomáš Silber
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
package services;

import guiuserapp.User.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *Is used to login and register user
 * @author Tomáš Silber
 */
public class UserService
{
    private String userPath;
    
    UserService()
    {
        setPath();
    }
    
    /**
     * Checks if password match with user
     * @param nick
     * @param passwd
     * @return 
     */
    private boolean authenticate(String nick, String passwd)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(userPath + "/users")))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                int index = line.indexOf(';');
                if(index == -1)
                    continue;
                
                if(line.substring(0, index).equals(nick))
                {
                    String pswd = line.substring(index + 1);
                    
                    MessageDigest engine = MessageDigest.getInstance("SHA-256");
                	String sha = new String(engine.digest(passwd.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                    if(pswd.equals(sha))
                        return true;
                }
            }
        }
        catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return false;
    }
    
    /**
     * Login user if is valid
     * @param nick
     * @param passwd
     * @return 
     */
    public boolean login(String nick, String passwd)
    {
        if(authenticate(nick, passwd))
        {
            User user = User.getInstance();
            user.setData(nick);
            return true;
        }
        else
            return false;
    }
    
    /**
     * Register new user
     * @param nick User nick
     * @param passwd user password
     * @param passwdAgain user password again
     * @throws Exception Exception if something went wrong, contains message for user.
     */
    public void register(String nick, String passwd, String passwdAgain) throws Exception
    {
        if(nick.length() == 0 || passwd.length() == 0 || passwdAgain.length() == 0)
            throw new Exception("Invalid input!");
        
        if(passwd.equals(passwdAgain))
        {
            if(exists(nick))
                throw new Exception("User already exists!");
            else
            {
            	MessageDigest engine = MessageDigest.getInstance("SHA-256");
            	String sha = new String(engine.digest(passwd.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
                createUser(nick, sha);
            }
        }
        else
            throw new Exception("Passwords does not match!");
    }
    
    /**
     * Checks if user is already registered
     * @param nick
     * @return True if exists, otherwise False.
     */
    private boolean exists(String nick)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(userPath + "/users")))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                int index = line.indexOf(';');
                if(index == -1)
                    continue;
                
                line = line.substring(0, index);
                if(line.equals(nick))
                    return true;
            }
        }
        catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return false;
    }
    
    /**
     * Append new user into file, if file does not exists it will try to create new one.
     * @param nick
     * @param passwd
     * @throws Exception if something went wrong, contains message for user.
     */
    private void createUser(String nick, String passwd) throws Exception
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(userPath + "/users", true)))
        {
            writer.write(nick);
            writer.write(';');
            writer.write(passwd);
            writer.newLine();
            writer.close();
        }
        catch(Exception ex)
        {
            System.err.println(ex);
            throw new Exception("Unable to save user!");
        }
    }
    
    /**
     * Sets path to user destination
     */
    private void setPath()
    {
        String OS = System.getProperty("os.name").toUpperCase();
        
        if(OS.contains("LINUX"))
            userPath = System.getProperty("user.home") + File.separator + ".torar/" + "users";
        else if(OS.contains("WIN") )
            userPath = System.getenv("APPDATA") + File.separator + "torar/" + "users";
        else
            userPath = System.getProperty("user.home") + File.separator + ".torar/" + "users";
        
        File des = new File(userPath);
        if(!des.exists())
            des.mkdirs();
    }
}