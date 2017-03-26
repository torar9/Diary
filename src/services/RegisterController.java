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

import guiuserapp.Diary;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tomáš Silber
 */
public class RegisterController extends LoginController
{
    @FXML
    private PasswordField passwdFieldAgain;
    
    @FXML
    private void handleLogin_button() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setScene(new Scene(root1));
        stage.show();
        
        Diary.stage.close();
        Diary.stage = stage;
    }
    
    @FXML
    private void handleRegister_button()
    {
        UserService usr = new UserService();
        try
        {
            usr.register(nickField.getText(), passwdField.getText().toCharArray(), passwdFieldAgain.getText().toCharArray());
        }
        catch(Exception ex)
        {
            errorText.setText(ex.getMessage());
        }
    }
}
