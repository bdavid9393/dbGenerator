package com.bolgar.dbgenerator;

import com.bolgar.dbgenerator.model.GeneradedModel;
import com.bolgar.dbgenerator.model.GeneratedField;

import java.io.*;

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
                while (line != null && line.length() != 0) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                    if (line == null || line.length() == 0) break;
                    GeneratedField generatedField = new GeneratedField();
                    generatedField.setFieldName(getFieldName(line));
                    generatedField.setFieldType(getFieldType(line));
                    generatedField.setParentClass(generadedModel.getClassName());
                    generadedModel.getFields().add(generatedField);

                    // System.out.println(generatedField.getSetter());
                    //   System.out.println(generadedModel.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writeToJava(generadedModel);

    }


    public static String getClassName(String input) {
        String start = "CREATE TABLE";
        Character c = 96;
        String end = c + " (";
        int starIndex = input.indexOf(start) + start.length() + 2;
        int endIndex = input.indexOf(end);

        String className = input.substring(starIndex, endIndex);

        char first = Character.toUpperCase(className.charAt(0));
        return first + className.substring(1);
    }

    public static String getFieldName(String input) {
        Character c = 96;
        String start = c.toString();
        String end = c.toString();
        int starIndex = input.indexOf(start);
        int endIndex = input.substring(starIndex + 1, input.length()).indexOf(end);

        return (input.substring(starIndex + 1, endIndex + starIndex + 1));

    }

    public static String getFieldType(String input) {
        if (input.contains("VARCHAR")) {
            return "String";
        } else if (input.contains("TINYINT") || input.contains("INT")) {
            return "Integer";
        }
        ;
        return "hiba";
    }

    public static void writeToJava(GeneradedModel generadedModel) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(generadedModel.getClassName() + ".java", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("public class " + generadedModel.getClassName() + " extends RealmObject {");
        writer.println();
        for (GeneratedField generatedField : generadedModel.getFields()) {
            writer.println(generatedField.getJSONName());
        }
        writer.println();
        for (GeneratedField generatedField : generadedModel.getFields()) {
            writer.println(generatedField.getDBName());
        }
        writer.println();
        for (GeneratedField generatedField : generadedModel.getFields()) {
            writer.println(generatedField.getField());
        }
        writer.println();
        for (GeneratedField generatedField : generadedModel.getFields()) {
            writer.println(generatedField.getGetter());
            writer.println();
            writer.println(generatedField.getSetter());
        }
        writer.println("\n}");
        writer.close();
    }
}