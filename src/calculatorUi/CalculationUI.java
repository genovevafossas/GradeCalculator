/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculatorUi;

import calculatorLogic.Calculator;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;

/**
 *
 * @author gfoss
 */

public class CalculationUI extends JPanel
{
    private int numFields = 10;
    private double finalGrade;
    private boolean pointBased;
    private Calculator calc;
    private JLabel gradesLabel;
    private JLabel percentWorthLabel;
    private JLabel totalLabel;
    private JPanel earnedPanel;
    private JPanel percentPanel;
    private ArrayList <NumField> earnedFields;
    private ArrayList <NumField> possibleFields;
    private JButton enterButton;
    private JButton clearButton;
    private JButton exportCSVButton;




    public CalculationUI(boolean pointBased)
    {
        int i = 1;
        this.pointBased = pointBased;
        calc = new Calculator();


        //Setting layout for panel refrenced by this, percent calculator
        //panel in the original UI.
        this.setPreferredSize(new Dimension(300, 300));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Initializing and adding panels for earned and percentage worth
        earnedPanel = new JPanel();
        percentPanel = new JPanel();
        enterButton = new JButton("Enter");
        clearButton = new JButton("Clear");
        exportCSVButton = new JButton("Export to CSV");
        totalLabel = new JLabel("Final Grade: ");

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 40;
        this.add(earnedPanel, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 40;
        this.add(percentPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 0;
        c.insets = new Insets (5, 0, 5, 0);
        c.gridwidth = 2;
        this.add(totalLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        this.add(enterButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        this.add(exportCSVButton, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy = 4;
        this.add(clearButton, c);

        //adding button action listeners
        clearButton.addActionListener(new ClearListener());
        enterButton.addActionListener(new EnterListener());
        exportCSVButton.addActionListener(new CsvListener());

        //Setting Layouts for earned and percentage worth nested panels,
        //as well as the constraints for that layout
        earnedPanel.setLayout(new GridBagLayout());
        percentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //Initializes the arraylists that will hold the information about grades
        //entered through j text fields
        initTextFields();

        //Initializes and adds the label designating the earned grades, then adds it to the earned panel.
        gradesLabel = new JLabel("Earned Grade:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        earnedPanel.add(gradesLabel, gbc);

        //Initializes and adds the label designating percentage or points a grade is worth, then adds it to
        //the percent panel
        if (!pointBased)
            percentWorthLabel = new JLabel("  Percentage:  ");
        else
            percentWorthLabel = new JLabel ("Total Point Val:");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        percentPanel.add(percentWorthLabel, gbc);

        //Places JTextFields into the panel for user input
        for (NumField grade : earnedFields)
        {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(2, 2, 0, 0);
            gbc.gridx = 0;
            gbc.gridy = i;
            earnedPanel.add(grade, gbc);
            i++;
        }

        //Resets the index variable for column placement then adds the other set
        //of jlabels to the appropriate panel through GBC constraints
        i = 1;
        for (NumField grade : possibleFields)
        {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(2, 2, 0, 0);
            gbc.gridx = 0;
            gbc.gridy = i;
            percentPanel.add(grade, gbc);
            i++;
        }


    }



    public void initTextFields ()
    {
        //Creates arraylists to hold GradeFields.
        earnedFields = new ArrayList<>();
        possibleFields = new ArrayList<>();

        for (int i = 0; i<numFields; i++) {
            earnedFields.add(new NumField());
            possibleFields.add(new NumField());
        }
    }


    class EnterListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            if (pointBased){
                finalGrade = calc.calculateGradePoints(earnedFields, possibleFields);
                totalLabel.setText("Final Grade: " + finalGrade);
            }
            else {
                finalGrade = calc.calculateGradeWeight(earnedFields, possibleFields);
                totalLabel.setText("Final Grade: " + finalGrade);

            }
        }
    }

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i =0; i<numFields; i++) {
                earnedFields.get(i).setText("");
                earnedFields.get(i).setValue(0);
                possibleFields.get(i).setText("");
                possibleFields.get(i).setValue(0);
            }
            totalLabel.setText("Final Grade: ");
        }
    }

    class CsvListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            String filename = "gradeExport.csv";
            int len = earnedFields.size();
            try
            {
                //Default encoding
                FileWriter writer = new FileWriter(filename, false);

                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                for (int i = 0; i<len; i++) {
                    if (earnedFields.get(i).getText() == "" || possibleFields.get(i).getText() == "")
                        continue;

                    bufferedWriter.write(String.format("%.2f", earnedFields.get(i).getValue()));
                    bufferedWriter.write(',');
                    bufferedWriter.write(String.format("%.2f", possibleFields.get(i).getValue()));
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            catch (IOException ex) {
                System.out.println("Error writing to file '" + filename + "'");
            }
        }
    }
}