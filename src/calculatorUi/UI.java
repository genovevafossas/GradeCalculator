/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculatorUi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 */

public class UI {
    JFrame frame;
    JMenuBar bar;
    JMenu prefs;
    JMenuItem about;
    JMenuItem exit;
    JTabbedPane panelHolder;
    JPanel percentPanel;
    JPanel pointPanel;

    public UI () {

        BorderLayout layout = new BorderLayout();

        //Initializing Jframe and basic components
        frame = new JFrame("Grade Calculators");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(325,470);
        frame.setLayout(layout);

        //Initializing Menu Bar and menus
        bar = new JMenuBar ();
        prefs = new JMenu("Menu");
        bar.add(prefs);

        //Initializing Menu items
        exit = new JMenuItem("Exit");
        about = new JMenuItem("About");
        prefs.add(exit);
        prefs.add(about);

        //Adding action listeners to menu items
        exit.addActionListener(new exitListener());
        about.addActionListener(new aboutListener());

        //Initializing panels & other JFrame components
        panelHolder = new JTabbedPane();
        percentPanel = new CalculationUI(false);
        pointPanel = new CalculationUI(true);


        //adding components onto frame
        frame.setJMenuBar(bar);
        frame.add(panelHolder);
        panelHolder.add("% Calculator", percentPanel);
        panelHolder.add("Pt Calculator", pointPanel);


        pointPanel.setVisible(false);
        percentPanel.setVisible(true);
        frame.setVisible(true);
    }

    class exitListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            String ObjButtons[] = {"Yes", "No"};
            int prompt = JOptionPane.showOptionDialog(null,
                    "Are you sure you want to exit?",
                    "Grade Calc",JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
            if (prompt == JOptionPane.YES_OPTION)
                System.exit(0);
        }
    }

    class aboutListener implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            JOptionPane.showMessageDialog(null, "Created by Genoveva Fossas\nUniversity of " +
                    "Central Florida 2018");
        }
    }


}
