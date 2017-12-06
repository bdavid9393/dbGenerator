package com.bolgar.dbgenerator;

import com.bolgar.dbgenerator.model.GeneradedModel;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by david on 2017.12.06..
 */

public class Main {

    public static void main(String[] args) throws IOException {


        PrintWriter writer = null;
        String input = null;
//        try {
//            input = readFile("input.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        GeneradedModel generadedModel = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("input.txt"));

            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                generadedModel = new GeneradedModel(getClassName(line));
                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                    generadedModel.addField(getFieldName(line), "satj");
                    System.out.println(generadedModel.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(generadedModel.toString());

    }


    public static String getClassName(String input) {
        String start = "CREATE TABLE";
        Character c = 96;
        String end = c + " (";
        int starIndex = input.indexOf(start) + start.length() + 2;
        int endIndex = input.indexOf(end);

        return input.substring(starIndex, endIndex);
    }

    public static String getFieldName(String input) {
        Character c = 96;
        String start = c.toString();
        String end = c.toString();
        int starIndex = input.indexOf(start);
        int endIndex = input.substring(starIndex+1,input.length()).indexOf(end);

        return input.substring(starIndex, endIndex);
    }
}