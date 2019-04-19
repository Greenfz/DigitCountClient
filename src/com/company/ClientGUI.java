package com.company;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

public class ClientGUI {


        ProblemObject obj = null;


        JRadioButton radioButton1;
        JRadioButton radioButton2;

        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        NumberFormatter digitFormatter = new NumberFormatter(longFormat);

        JFormattedTextField textField1;
        JFormattedTextField textField2;
        JFormattedTextField textField3;
        JFrame ramka;
        public JButton button1;

        public void createGUI() {

            ramka = new JFrame();
            ramka.setLayout(new GridLayout(3, 1));

            JPanel panelGorny = new JPanel();
            panelGorny.setLayout(new FlowLayout());

            numberFormatter.setValueClass(Long.class);
            numberFormatter.setAllowsInvalid(false);
            digitFormatter.setValueClass(Long.class);
            digitFormatter.setAllowsInvalid(false);





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


            JPanel panelSrodkowy = new JPanel();
            panelSrodkowy.setLayout(new GridLayout(1, 2));


            radioButton1 = new JRadioButton("Łączna ilosc znaków", true);
            radioButton2 = new JRadioButton("Ilosc liczb naturalnych");

            panelSrodkowy.add(radioButton1);
            panelSrodkowy.add(radioButton2);

            radioButton1.addActionListener(new RadioButton1ActionListener());
            radioButton2.addActionListener(new RadioButton2ActionListener());


            JPanel panelDolny = new JPanel();
            panelDolny.setLayout(new FlowLayout());

            button1 = new JButton("Oblicz");
            button1.addActionListener(new buttonObliczListener());
            button1.addActionListener(new Client().listener2);

            JTextArea odebrane = new JTextArea(5, 30);

            panelDolny.add(button1);
            panelDolny.add(odebrane);

            ramka.setSize(400, 400);
            ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            ramka.setResizable(false);
            ramka.setLocationRelativeTo(null);
            ramka.setVisible(true);
            ramka.getContentPane().add(panelGorny);
            ramka.getContentPane().add(panelSrodkowy);
            ramka.getContentPane().add(panelDolny);
        }

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
                      obj = new ProblemObject();
                      obj.setUpperBound(Integer.parseInt(textField1.getText()));
                      obj.setLowerBound(Integer.parseInt(textField2.getText()));
                      obj.setDigitToCheck(Integer.parseInt(textField3.getText()));
                      if(radioButton1.isSelected()) obj.setAllDigits(true);
                      if(radioButton2.isSelected()) obj.setAllDigits(false);

                    }

                } else {
                    JOptionPane.showMessageDialog(ramka, "Wszystkie pola muszą zostać uzupełnione");
                }
            }
        }

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
        public void objectToNull(){
            obj = null;
        }

    }
