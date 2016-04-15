/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otpclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Diogo
 */
public class OtpClient {

    private static final int NUMBER_OF_PASSWORDS = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList tokensArray = new ArrayList();
        
        // Gets user input
        System.out.println("Username: ");
        String username = scanner.next();
        System.out.println("Password: ");
        String password = scanner.next();
        
        // Gets seed and clears password
        int seed = password.hashCode();
        password = "";
        
        // Gets current date
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        
        // Generates tokens based on seed
        String newToken = String.valueOf(seed);
        System.out.println("The tokens generated are: ");
        for (int i = 0; i < NUMBER_OF_PASSWORDS; i++) {
            newToken = newToken + currentDate;
            System.out.println(newToken.hashCode());
        }
    }
    
}
