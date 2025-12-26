/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import javax.swing.*;

public class Logout {
    public static void logOut(JFrame context, login loginScreen){
        LoginSession.isLoggedIn = false;
        LoginSession.UID = 0;
        LoginSession.Username = null;
        LoginSession.Nickname = null;
        LoginSession.Usertype = null;
        
        context.dispose();
        loginScreen.setVisible(true);
    }
}
