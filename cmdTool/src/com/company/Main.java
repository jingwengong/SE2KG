package com.company;

import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.company.CmdTool;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner reader = new Scanner(System.in);
        System.out.println("Please input file name");
        String inputFileName = reader.nextLine();
        String command = "ping -n 3 google.com";
        CmdTool cmdTool = new CmdTool();
        cmdTool.executeCommand(command);
        try {
            cmdTool.dumpOutput("output.txt");
        } catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
    }


}


