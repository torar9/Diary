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

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Tomáš Silber
 */
public class Encryptor 
{
    private static final String ALGO = "AES";
    private static final byte[] Hiddenkey = new byte[] { 'T', 'H', 'E', 'G', 'L', 'O', 'R','D', 'G', 'S', 'A','R', 'U', 'M', 'A', 'N' };//I know...
    
    private Encryptor(){};
    /**
     * Encrypt string value with AES encryption
     * @param Data Data which should be encrypted
     * @return Returns encrypted string value
     * @throws Exception 
     */
    public static String Encrypt(String Data) throws Exception 
    {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());   
        
        return Base64.getEncoder().withoutPadding().encodeToString(encVal);
    }
    
    /**
     * 
     * @param Data String value which should be decrypted
     * @return String value
     * @throws Exception 
     */
    public static String Decrypt(String Data) throws Exception 
    {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getMimeDecoder().decode(Data);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);

        return decryptedValue;
    }
    
    /**
     * Methos is used to generate hidden key
     * @return Returns key
     * @throws Exception 
     */
    private static Key generateKey() throws Exception 
    {
        Key key = new SecretKeySpec(Encryptor.Hiddenkey, ALGO);
        return key;
    }
}
