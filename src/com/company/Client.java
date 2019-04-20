package com.company;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.NumberFormat;

public class Client {

        ProblemObject objectClient = null;
        Socket socket = null;
        ObjectOutputStream objOutStream;
        BufferedReader bufferedReader;

// GUI
        JFrame ramka;
        JRadioButton radioButton1;
        JRadioButton radioButton2;
        JButton button1;
        JFormattedTextField textField1;
        JFormattedTextField textField2;
        JFormattedTextField textField3;
        JTextArea odebrane;
        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        NumberFormatter digitFormatter = new NumberFormatter(longFormat);

// MAIN
    public static void main(String[] args) {

    Client client = new Client();
    client.createGUI();
    client.createConnection();
    }
// CONNECTON
    public void createConnection(){
        try {
            socket = new Socket("localhost", 5555);
            System.out.println("utworzono socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objOutStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("utworzono OutputStream");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Utworzono BufferedReader");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
// GUI
    public void createGUI() {
// FAME AND NUMBERFORMATTERS
        ramka = new JFrame();
        ramka.setLayout(new GridLayout(3, 1));
        longFormat.setGroupingUsed(false);
        numberFormatter.setValueClass(Long.class);
        numberFormatter.setAllowsInvalid(false);
        digitFormatter.setValueClass(Long.class);
        digitFormatter.setAllowsInvalid(false);

// NORTH PANEL CONFIGURATION
        JPanel panelGorny = new JPanel();
        panelGorny.setLayout(new FlowLayout());

        textField1 = new JFormattedTextField(numberFormatter);
        textField2 = new JFormattedTextField(numberFormatter);
        textField3 = new JFormattedTextField(digitFormatter);
        textField1.setColumns(20);
        textField2.setColumns(20);
        textField3.setColumns(5);

        textField3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textField3.getText().length() >= 1 ) {

                    digitFormatter.setAllowsInvalid(true);
                    textField3.setText("");
                    digitFormatter.setAllowsInvalid(false);

                }
            }
        });

        JLabel label1 = new JLabel("Dolna granica przedziału");
        JLabel label2 = new JLabel("Górna granica przedziału");
        JLabel label3 = new JLabel("Szukana cyfra");
        JButton buttonReset = new JButton("Reset");
        buttonReset.addActionListener(new ButtonResetListener());

        panelGorny.add(label1);
        panelGorny.add(textField1);
        panelGorny.add(label2);
        panelGorny.add(textField2);
        panelGorny.add(label3);
        panelGorny.add(textField3);
        panelGorny.add(buttonReset);

// CENTER PANEL CONFIGURATION
        JPanel panelSrodkowy = new JPanel();
        panelSrodkowy.setLayout(new GridLayout(1, 2));

        radioButton1 = new JRadioButton("Łączna ilosc znaków", true);
        radioButton2 = new JRadioButton("Ilosc liczb naturalnych");
        radioButton1.addActionListener(new RadioButton1ActionListener());
        radioButton2.addActionListener(new RadioButton2ActionListener());

        panelSrodkowy.add(radioButton1);
        panelSrodkowy.add(radioButton2);

// SOUTH PANEL CONFIGURATION
        JPanel panelDolny = new JPanel();
        panelDolny.setLayout(new FlowLayout());

        button1 = new JButton("Oblicz");
        button1.addActionListener(new buttonObliczListener());

        odebrane = new JTextArea(10, 30);
        odebrane.setEditable(true);
        odebrane.setLineWrap(true);
        odebrane.setWrapStyleWord(true);
        odebrane.setFont(new Font("Futura", Font.BOLD, 15));


        panelDolny.add(button1);
        panelDolny.add(odebrane);

// FRAME CONFIGURATION
        ramka.setSize(400, 400);
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ramka.setResizable(false);
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
        ramka.getContentPane().add(panelGorny);
        ramka.getContentPane().add(panelSrodkowy);
        ramka.getContentPane().add(panelDolny);
//        ramka.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                try {
//                    socket.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
    }

// ALL LISTENERS
    public class RadioButton1ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            radioButton1.setSelected(true);
            radioButton2.setSelected(false);

        }}
    public class RadioButton2ActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            radioButton2.setSelected(true);
            radioButton1.setSelected(false);

        }
    }
    public class ButtonResetListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            textAreaCleaner();
        }
    }
    public class buttonObliczListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areNotEmpty()){
                    if (!firstIsLower()){
                        JOptionPane.showMessageDialog(ramka, "Dolna granica powinna byc mniejsza od gornej");
                    } else {
                      objectClient = new ProblemObject();
                      objectClient.setLowerBound(Integer.parseInt(textField1.getText()));
                      objectClient.setUpperBound(Integer.parseInt(textField2.getText()));
                      objectClient.setDigitToCheck(Integer.parseInt(textField3.getText()));
                      if(radioButton1.isSelected()) objectClient.setAllDigits(true);
                      if(radioButton2.isSelected()) objectClient.setAllDigits(false);
                      Thread watekClient = new Thread(new Wysylacz());
                      watekClient.start();
                    }
                } else {
                    JOptionPane.showMessageDialog(ramka, "Wszystkie pola muszą zostać uzupełnione");
                }
            }
        }

// ALL METHODS USED IN LISTENERS
    public void textAreaCleaner(){

        textField1.setValue(null);
        textField2.setValue(null);
        textField3.setValue(null);
    }
    public boolean areNotEmpty(){
        return ((!textField1.getText().equals("")) && (!textField2.getText().equals("")) &&
                (!textField3.getText().equals("")));
    }
    public boolean firstIsLower(){
        return (Integer.parseInt(textField1.getText()) < (Integer.parseInt(textField2.getText())));
    }

// THREADS
    public class Wysylacz implements Runnable{
    @Override
    public void run() {
        try {
            while(objectClient != null){
            objOutStream.writeObject(objectClient);
            objectClient = null;
            while (true){
                odebrane.setText(bufferedReader.readLine());
            }
            }
        } catch(Exception e) {e.printStackTrace();}
    }
}


}




