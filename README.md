#Basic diary

<h2>About</h2>
<p>Basic diary that saves notes into file. User can save file under name that he/she choosed. (except special characters)</p>
<p>The text inside file is encrypted with AES encryption.</p>

<h2>Specification</h2>
<p>Program is written in Netbeans IDE(v8.2) using JavaFX (JavaJDK v8), user data are stored in a file with FXML format.</p>

<h3>Libraries</h3>
<ul>
  <li>JUnit v 4.12</li>
  <li>hamcrest-core v 1.3</li>
</ul>

<h3>Project Hiearchy:</h3>

<hr>

<h3>Package: guiuserapp</h3>
<ul>
  <li><b>Diary</b>.java - Main class</li>
  <li><b>FXMLDocument</b>.fxml - Contains GUI informations and settings.</li>
  <li><b>FXMLDocumentController</b>.java - Controller class.</li>
</ul>

<h3>Package: Database</h3>
<ul>
<li><b>IDatabase</b>.java - Database Interface</li>
  <li><b>userDataDatabase</b>.java - Class is used for saving, loading and removing user data.</li>
  <li><b>Encryptor</b>.java - Static class for encrypting and decrypting text.</li>
</ul>

<h3>Package: User</h3>
<ul>
  <li><b>UserData</b>.java - Class contains name of the user record and its text.</li>
</ul>

<h3>Tests: </h3>

<ul>
  <li><b>DatabaseTest.java</b> - Not fully implemented</li>
</ul>

<hr>

<h2>Author, License</h2>
<p><b>Author: Tomáš Silber</b></p>

<p>This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details
 </p>
