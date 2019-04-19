package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    ProblemObject objectClient = null;
    ClientGUI clientGUI = new ClientGUI();

    Socket socket = null;
    ObjectOutputStream objOutStream;

    obliczClientListener listener2 = new obliczClientListener();


    public static void main(String[] args) {

    Client klient = new Client();
    klient.clientGUI.createGUI();
    klient.createConnection();

    }
    public void createConnection(){
//        try {
//            socket = new Socket("localhost", 5555);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            objOutStream = new ObjectOutputStream(socket.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (objectClient != null){
//
//            System.out.println(objectClient.toString());
//            objectClient = null;
//        }
    }

    public void sendObject() {
//        System.out.println(objectClient.toString())
//        objectClient = null;
    }


    public class obliczClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            objectClient = clientGUI.obj;
            System.out.println("POSZLO Z INNEJ KLASY");
        }
    }

}
