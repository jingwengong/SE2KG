package GUI;

import CSCI401.Menu;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UserInterface extends Frame{
    private String userChoice = "1";
    private String inputFileName = "";
    private Menu menu = new Menu();
    final JFileChooser fc = new JFileChooser();

    public UserInterface() {
        //Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
//        JMenu m1 = new JMenu("FILE");
//        JMenu m2 = new JMenu("Help");
//        mb.add(m1);
//        mb.add(m2);
//        JMenuItem m11 = new JMenuItem("Open");
//        JMenuItem m22 = new JMenuItem("Save as");
//        m1.add(m11);
//        m1.add(m22);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label); // Components Added using Flow Layout
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        JPanel middlePanel = new JPanel();
//        middlePanel.setLayout(new GridLayout(2,1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        ButtonGroup bGroup = new ButtonGroup();
        JRadioButton xmlButton = new JRadioButton("1. Input linkage file name.");
        xmlButton.setSelected(true);
        xmlButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = "1";
            }
        });
        bGroup.add(xmlButton);
        buttonPanel.add(xmlButton);
        JRadioButton csvButton = new JRadioButton("2. Auto generate linkage file.");
        csvButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = "2";
            }
        });
        bGroup.add(csvButton);
        buttonPanel.add(csvButton);
        JRadioButton sparqlButton = new JRadioButton("3. User SPARQL query.");
        sparqlButton.addActionListener(new ActionListener() {
        	
        	@Override
            public void actionPerformed(ActionEvent e) {
                userChoice = "3";
            }
        });
        bGroup.add(sparqlButton);
        buttonPanel.add(sparqlButton);

        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter;
                if (userChoice == "1") {
                    filter = new FileNameExtensionFilter("XML files", "xml");
                } else {
                    filter = new FileNameExtensionFilter("CSV files", "csv");
                }
                fc.setFileFilter(filter);
                int returnValue = fc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    inputFileName = selectedFile.getAbsolutePath();
                    System.out.println(selectedFile.getAbsolutePath());
                    String outputFileName = menu.processOption(userChoice, inputFileName);
                    System.out.println(outputFileName);
                }
            }
        });

        JPanel addFilePanel = new JPanel();
//        chooseFileButton.setSize(20, 10);
        addFilePanel.add(chooseFileButton);

        middlePanel.add(buttonPanel);
        middlePanel.add(addFilePanel);





        // Open the save dialog
//        j.showSaveDialog(null);


        // Text Area at the Center
//        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
//        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, middlePanel);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new UserInterface();

    }
}
