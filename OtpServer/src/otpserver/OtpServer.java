/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otpserver;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Diogo
 */
public class OtpServer {
    private static final int NUMBER_OF_PASSWORDS = 5;
    private Scanner scanner;
    private String seed;
    private ArrayList tokensArray;
    private String currentDate;
    
    public OtpServer() {
        this.tokensArray = new ArrayList();
        this.scanner = new Scanner(System.in);
        readFile();
    }
    
    // Reads seed from file
    public void readFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader( new FileInputStream(new File("password.txt"))))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                seed = currentLine;
            }
        } catch (IOException e) {}
    }
    
    // Simply returns current date in the specified format
    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public void generateTokens() {
        String newToken = seed;
        
        // Generate a number of tokens based on the seed given
        for (int i = 0; i < NUMBER_OF_PASSWORDS; i++) {
            newToken = newToken + currentDate;
            tokensArray.add(newToken.hashCode());
            System.out.println(newToken.hashCode());
        }
    }
    
    public void run() {
        // Updates current date and generate first tokens
        currentDate = getCurrentDate();
        generateTokens();
        
        // Create variables used
        String userInput = "";
        int usedTokenIndex = NUMBER_OF_PASSWORDS;
        
        while (true) {
            
            // If time limit has been reached, update tokens
            if (!currentDate.equals(getCurrentDate())) {
                currentDate = getCurrentDate();
                generateTokens();
            }
            
            // Gets input from user
            System.out.println("Enter your token: ");
            userInput = scanner.next();
            
            // Exit program if exit command is typed
            if (userInput.equals("exit")) {
                break;
            }
            
            // Checks if the token on input matches with any that has been created
            if (tokensArray.contains(Integer.valueOf(userInput))) {
                System.out.println("SUCCESS | Token matches!");
                usedTokenIndex = tokensArray.indexOf(Integer.valueOf(userInput));
            } else {
                System.out.println("ERROR | Token is not valid!");
            }
            
            // Discards tokens generated from the used token
            while (tokensArray.size() > usedTokenIndex) {
                tokensArray.remove(usedTokenIndex);
            }

        }
        
    }
    
    public static void main(String[] args) {
        OtpServer otpServer = new OtpServer();
        otpServer.run();
    }
}
