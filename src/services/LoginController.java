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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tomáš Silber
 */
public class LoginController implements Initializable
{
    @FXML
    protected Text errorText;
    @FXML
    protected TextField nickField;
    @FXML
    protected PasswordField passwdField;
    
    @FXML
    private void handleLogin_button() throws IOException
    {
        UserService serv = new UserService();
        if(serv.login(nickField.getText(), passwdField.getText()))
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/guiuserapp/FXMLDocument.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.getIcons().add(new Image("/images/icon.png"));
            stage.setScene(new Scene(root1));
            stage.show();
        
            Diary.stage.close();
            Diary.stage = stage;
        }
        else
            errorText.setText("Invalid login!");
    }
    
    @FXML
    private void handleRegister_button() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setScene(new Scene(root1));
        stage.show();
        
        Diary.stage.close();
        Diary.stage = stage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }
}
