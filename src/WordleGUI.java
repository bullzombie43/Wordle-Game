import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class WordleGUI implements ActionListener{

    private static JFrame frame;
    private static JPanel panel;

    private static String path = "/Users/justin/VSCODE PROJECTS/numberReveral/src/";
    
    private static JLabel title;
    private static JTextField textField;
    private static Font font = new Font("Ink Free", Font.BOLD, 15);
    private static JLabel[] labels;
    private static int tries;
    private static String[] validWords = new String[12947];
    private static String[] answers = new String[2315];
    private static String answer;
    private static String[] wordsTried = new String[6];
    private static JLabel invalidLabel = new JLabel("Not a valid word");
    

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String BLACK_COLOR = "<html><font color=\"black\">";
    public static final String YELLOW_COLOR = "<html><font color=\"yellow\">";
    public static final String GREEN_COLOR = "<html><font color=\"green\">";
    


    public WordleGUI(){

        frame = new JFrame();
        panel = new JPanel();
        tries = 0;

        frame.setSize(420, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("WordleGUI");
        frame.add(panel);

        panel.setLayout(null);

        title = new JLabel("Wordle by Justin");
        title.setBounds(135, 20, 150, 50);
        panel.add(title);

        textField = new JTextField();
        textField.setFont(font);
        textField.setBounds(135, 80, 80,15);
        textField.addActionListener(this);
        panel.add(textField);

        invalidLabel.setBounds(135,250,150,30);

        labels = new JLabel[6];

        for(int i = tries + 1; i < 6; i++){
            labels[i] = new JLabel("- - - - -");
            labels[i].setBounds(135, 80+(i*30), 80, 15);
            panel.add(labels[i]);
        }

        try { //copied from https://replit.com/@skutschke/WordleWords#Main.java
            File myObj = new File(path + "ValidWords.txt");
            Scanner myReader = new Scanner(myObj);
            int indexCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //add data to the array
                validWords[indexCounter] = data;
                indexCounter++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try { //copied from https://replit.com/@skutschke/WordleWords#Main.java
            File myObj = new File(path + "wordleAnswers.txt");
            Scanner myReader = new Scanner(myObj);
            int indexCounter = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //add data to the array
                answers[indexCounter] = data;
                indexCounter++;
            }
            myReader.close();   
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        answer = answers[(int)(Math.random() * (answers.length - 1))];

    

        frame.setVisible(true);
    
    }

    public static void main(String[] args){
        new WordleGUI();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        boolean done = gradeWord(textField.getText());
        

        System.out.println("ANSWER: " + answer);
        

        if(validWord(textField.getText())){

            panel.remove(invalidLabel);
            frame.repaint();

            if(tries < 5){
                wordsTried[tries] = returnColoredWord(textField.getText());
                tries++;

                for(int i = tries; i < 6; i++){
                    panel.remove(labels[i]);
                }

                textField.setBounds(135, 80+(tries*30), 80, 15);

                for(int i =0; i < tries; i++){
                    JLabel pastLabel = new JLabel(wordsTried[i]);
                    pastLabel.setBounds(135, 80 + (i *30), 80, 15);
                    panel.add(pastLabel);
                }

                for(int i = tries + 1; i < 6; i++){
                    labels[i] = new JLabel("- - - - -");
                    labels[i].setBounds(135, 80+(i*30), 80, 15);
                    panel.add(labels[i]);
                }

                if(done){
                    panel.remove(textField);
                    JLabel wonLabel = new JLabel("nice good job");
                    wonLabel.setBounds(135,250,150,50);
                    wonLabel.setFont(font);
                    panel.add(wonLabel);
                    System.out.println("YOU WON");
                    
                }
                
            
            }
            else {

                if(done){
                    panel.remove(textField);
                    JLabel wonLabel = new JLabel("nice good job");
                    wonLabel.setBounds(135,300,150,50);
                    wonLabel.setFont(font);
                    panel.add(wonLabel);
                    System.out.println("YOU WON");
                    
                }
                else{
                    panel.remove(textField);
                    JLabel lostLabel = new JLabel("Dumbass");
                    lostLabel.setBounds(135,300,150,50);
                    lostLabel.setFont(font);
                    panel.add(lostLabel);
                    System.out.println("dumbass");
                    
                }
            }
            
        }
        else{
            panel.add(invalidLabel);
            frame.repaint();
        }

        frame.repaint();
        
        
    }

    public static boolean validWord(String input){
        if(input.length() != 5){
            System.out.println("Please enter a 5 letter word");
            return false;
        }
        for(String word : validWords){
            if(word.equals(input)){ 
                System.out.println("valid");
                return true;
            }
        }
        return false;
        
    }

    public static String returnColoredWord(String input){
        String returString = "";

        for(int i =0; i < 5; i++){
            if(input.charAt(i) == answer.charAt(i)) 
                returString += (GREEN_COLOR + input.charAt(i));
            else if (answer.indexOf(input.charAt(i)) >= 0)
                returString += (YELLOW_COLOR+ input.charAt(i));
            else 
                returString += (BLACK_COLOR + input.charAt(i));
        }

        

        return returString;
    }

    public static boolean gradeWord(String input){
        for(int i =0; i < 5; i++){
            if(input.charAt(i) != answer.charAt(i))
                return false;
        }

        return true;
    }

    
}
