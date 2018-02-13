package com.bolgar.dbgenerator.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2017.12.06..
 */
public class GeneradedModel {
    String className;
    String primrayField;
    List<GeneratedField> fields= new ArrayList<>();

    public GeneradedModel(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<GeneratedField> getFields() {
        return fields;
    }

    public void setFields(List<GeneratedField> fields) {
        this.fields = fields;
    }

    public String getPrimrayField() {
        return primrayField;
    }

    public void setPrimrayField(String primrayField) {
        this.primrayField = primrayField;
    }

    public String getDbGetAll(){

        return "   public static RealmResults<"+className+"> getAll() {\n" +
                "        Realm realm = DataManager.getRealm();\n" +
                "        if (!realm.isClosed()) {\n" +
                "            return realm\n" +
                "                    .where("+className+".class)\n" +
                "                    .findAll();\n" +
                "        }\n" +
                "        return null;\n" +
                "    }";
    }

    @Override
    public String toString() {
        return "GeneradedModel{" +
                "className='" + className + '\'' +
                ", fields=" + fields +
                '}';
    }
}
