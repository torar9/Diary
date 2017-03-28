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
import java.util.Arrays;

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
    private boolean authenticate(String nick, char[] passwd)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(userPath + "/users")))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                int index = line.indexOf(';');
                if(index == -1)
                    continue;
                
                String user = line.substring(0, index);
                if(line.substring(0, index).equals(nick))
                {
                    char[] pswd = line.substring(index + 1).toCharArray();
                    if(Arrays.equals(pswd, passwd))
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
    public boolean login(String nick, char[] passwd)
    {
        if(authenticate(nick, passwd))
        {
            User user = User.getInstance();
            user.setData(nick, passwd);
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
    public void register(String nick, char[] passwd, char[] passwdAgain) throws Exception
    {
        if(nick.length() == 0 || passwd.length == 0 || passwdAgain.length == 0)
            throw new Exception("Invalid input!");
        
        if(Arrays.equals(passwd, passwdAgain))
        {
            if(exists(nick))
                throw new Exception("User already exists!");
            else
            {
                createUser(nick, passwd);
            }
        }
        else
            throw new Exception("Password does not match!");
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
     * 
     * @param nick
     * @param passwd
     * @throws Exception if something went wrong, contains message for user.
     */
    private void createUser(String nick, char[] passwd) throws Exception
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