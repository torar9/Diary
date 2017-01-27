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
package guiuserapp;

import guiuserapp.Database.IDatabase;
import guiuserapp.Database.userDataDatabase;
import guiuserapp.User.UserData;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author Tomáš Silber
 */
public class FXMLDocumentController implements Initializable
{
    private final IDatabase database;

    @FXML
    private TextField name;
    @FXML
    private ListView<UserData> nameList;
    @FXML
    private TextArea text;
    @FXML
    private Button Add_button;
    @FXML
    private Button Remove_button;
    @FXML
    private Text System_print;
    @FXML
    private Button Undo_button;
    @FXML
    private Button Save_button;
    
    private UserData safe;

    public FXMLDocumentController()
    {
        database = new userDataDatabase();
    }
    
    @FXML
    private void handleSave_button()
    {
        UserData data = new UserData(name.getText(), text.getText());
        try
        {
            database.editData(safe, data);
             clear();
             Save_button.setVisible(false);
        }
        catch(Exception e)
        {
            System_print.setText(e.getMessage());
        }
    }
    
    @FXML
    private void handleClick_event()
    { 
        UserData data = nameList.getSelectionModel().getSelectedItem();
        if(data != null)
        {
            safe = data;
            setText(data);
            Save_button.setVisible(true);
        }
    }

    @FXML
    private void handleAdd_button()
    {
        try
        {
            database.addData(new UserData(name.getText(), text.getText()));
        }
        catch(Exception e)
        {
            System_print.setText(e.getMessage());
        }
    }

    @FXML
    private void handleRemove_button()
    {
        UserData  data = (UserData)nameList.getSelectionModel().getSelectedItem();
        try
        {
            database.removeData(data);
        }
        catch(Exception e)
        {
            System_print.setText(e.getMessage());
        }
        clear();
        Save_button.setVisible(false);
    }

    @FXML
    private void handleUndo_button()
    {
        try
        {
            database.restoreContent();
        }
        catch(Exception e)
        {
            System_print.setText(e.getMessage());
        }
    }

    /**
     * Methos is used to remove texts from text fields
     */
    private void clear()
    {
        text.clear();
        name.clear();
        System_print.setText("");
    }
    
    private void setText(UserData data)
    {
        name.setText(data.getName());
        text.setText(data.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        nameList.setItems(database.getContents());
        Save_button.setVisible(false);
    }
}