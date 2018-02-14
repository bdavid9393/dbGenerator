package com.bolgar.dbgenerator;

import com.bolgar.dbgenerator.model.Db;
import com.bolgar.dbgenerator.model.Field;
import com.bolgar.dbgenerator.model.Table;

import java.io.*;
import java.util.Scanner;

/**
 * Created by david on 2017.12.06..
 */

public class Main {

    private static Db db;

    public static void main(String[] args) throws IOException {

        String entireFileText = new Scanner(new File("input.txt"))
                .useDelimiter("\\A").next();

        db = new Db();

        getRawTables(entireFileText, db);
        System.out.println(db.toString());
        for (String rawTabel : db.getRawTables()) {
            generateTables(rawTabel);
        }
    }


    public static void generateTables(String data) {
        Table table = null;
        BufferedReader br = null;
        String[] lines = data.split(System.getProperty("line.separator"));
        br = new BufferedReader(new StringReader(data));

        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            table = new Table(getClassName(line));
            db.addTable(table);
            while (line != null && line.length() != 0) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
                if (isPrimaryKeyLine(line)) {
                    table.setPrimrayField(getFieldName(line));
                }
                if (line.charAt(2) != 96) {
                    return;
                }
                if (line == null || line.length() == 0) break;
                Field generatedField = new Field();
                generatedField.setFieldName(getFieldName(line));
                generatedField.setFieldType(getFieldType(line));
                generatedField.setParentClass(table.getClassName());
                table.getFields().add(generatedField);


                // System.out.println(generatedField.getSetter());
                //   System.out.println(generadedModel.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            db.checkIgnore();
            if (!table.isIgnored()) {
                writeToJava(table);
            }
        }

    }


    public static boolean isPrimaryKeyLine(String input) {
        if (input.contains("PRIMARY KEY")) {
            return true;
        } else {
            return false;
        }

    }

    public static void getRawTables(String input, Db db) {
        String start = "CREATE TABLE IF NOT EXISTS";
        Character c = 96;
        String end = "ENGINE=";
        int starIndex = input.indexOf(start) + start.length() + 2;
        int endIndex = input.indexOf(end);

        if (endIndex == -1) {
            return;
        }

        String raw = input.substring(starIndex, endIndex);
        db.addRawTable(raw);
        //     System.out.println(raw);
        String rest = input.substring(endIndex + 6);
        getRawTables(rest, db);


    }


    public static String getClassName(String input) {
        String start = "CREATE TABLE";
        Character c = 96;
        String end = c + " (";
        // int starIndex = input.indexOf(start) + start.length() + 2;
        int starIndex = 0;
        int endIndex = input.indexOf(end);
        String className = input.substring(starIndex, endIndex);
        char first = Character.toUpperCase(className.charAt(0));
        String newString = first + className.substring(1);
        StringBuffer stringBuffer = new StringBuffer();
        boolean isPrevUnderline = false;
        for (char cc : newString.toCharArray()) {
            if (isPrevUnderline) {
                stringBuffer.append(Character.toUpperCase(cc));
                isPrevUnderline = false;
            } else if (cc == '_') {
                isPrevUnderline = true;
            } else {
                stringBuffer.append(cc);
            }
        }
        return stringBuffer.toString();
    }

    public static String getFieldName(String input) {
        Character c = 96;
        String start = c.toString();
        String end = c.toString();
        int starIndex = input.indexOf(start);
        int endIndex = input.substring(starIndex + 1, input.length()).indexOf(end);

        if (endIndex == -1) {
            return "qwer";
        }

        return (input.substring(starIndex + 1, endIndex + starIndex + 1));

    }

    public static String getFieldType(String input) {
        input = input.toUpperCase();
        if (input.contains("VARCHAR") || input.contains("CHAR")) {
            return "String";
        } else if (input.contains("TINYINT") || input.contains("INT")) {
            return "Integer";
        } else if (input.contains("FLOAT")) {
            return "Float";
        }
        ;
        return "hiba";
    }

    public static void writeToJava(Table table) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(table.getClassName() + ".java", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        table.checkIgnore();

        writer.println("public class " + table.getClassName() + " extends RealmObject {");
        writer.println();

        //JSON mezők
        for (Field generatedField : table.getFields()) {
            if (!generatedField.isIgnored()) {
                writer.println(generatedField.getJSONName());
            }
        }
        writer.println();
        //DB mezők
        for (Field generatedField : table.getFields()) {
            if (!generatedField.isIgnored()) {
                writer.println(generatedField.getDBName());
            }
        }
        writer.println();
        // mezők
        for (Field generatedField : table.getFields()) {
            if (!generatedField.isIgnored()) {
                if (table.isPrimaryKey(generatedField.getFieldName())) {
                    writer.println("@PrimaryKey");
                }
                writer.println(generatedField.getField());
            }
        }
        writer.println();
        //getterek setterek
        for (Field generatedField : table.getFields()) {
            if (!generatedField.isIgnored()) {
                writer.println(generatedField.getGetter());
                writer.println();
                writer.println(generatedField.getSetter());
            }
        }
        writer.println();
        //getall
        writer.println(table.getDbGetAll());
        writer.println("\n}");

        writer.close();
    }
}