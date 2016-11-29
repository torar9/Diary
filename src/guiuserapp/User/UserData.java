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
package guiuserapp.User;

/**
 * Class is used to store data, the class is used to pack the datas.
 * @author Tomáš Silber
 */
public class UserData 
{
    private String DataName;
    private String text;
    
    /**
     * Constructor which sets name of the data and text
     * @param DataName Name of data
     * @param text Text
     */
   public UserData(String DataName, String text)
   {
       this.DataName = DataName.toLowerCase();
       this.text     = text;
   }
   
   /**
    * Returns saved text
    * @return text
    */
   public String getText()
   {
       return text;
   }
   
   /**
    * Returns name of saved data
    * @return 
    */
   public String getName()
   {
       return DataName;
   }
   
   /**
    * Sets a text and name of data
    * @param DataName name of data
    * @param text text
    */
   public void setDatas(String DataName, String text)
   {
       this.DataName = DataName.toLowerCase();
       this.text     = text;
   }
   
   public void setName(String DataName)
   {
       this.DataName = DataName;
   }
   
   public void setText(String text)
   {
       this.text = text;
   }
   
   @Override
    public String toString()
    {
        return DataName;
    }
}