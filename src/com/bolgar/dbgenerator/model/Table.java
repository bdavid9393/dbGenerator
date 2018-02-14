package com.bolgar.dbgenerator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2017.12.06..
 */
public class Table {
    String className;
    String primrayField;
    boolean isIgnored;
    List<Field> fields = new ArrayList<>();

    public Table(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getPrimrayField() {
        return primrayField;
    }

    public void setPrimrayField(String primrayField) {
        this.primrayField = primrayField;
    }

    public boolean isIgnored() {
        return isIgnored;
    }

    public void setIgnored(boolean ignored) {
        isIgnored = ignored;
    }

    public void checkIgnore() {
        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("created_user_id");
        ignoreList.add("created_at");
        ignoreList.add("updated_at");
        ignoreList.add("updated_user_id");
        ignoreList.add("valid");
        ignoreList.add("createdUserId");
        ignoreList.add("createdAt");
        ignoreList.add("updatedAt");
        ignoreList.add("updatedUserId");
        ignoreList.add("valid");
        ignoreList.add("reopen_at");
        ignoreList.add("reopenAt");
        ignoreList.add("reopen_user_id");
        ignoreList.add("reopenUserId");
        for (Field field : fields) {
            for (String ignoredTable : ignoreList) {
                if (field.getFieldName().equalsIgnoreCase(ignoredTable)) {
                    field.setIgnored(true);
                }

            }
        }

    }


    public String getDbGetAll() {

        return "   public static RealmResults<" + className + "> getAll() {\n" +
                "        Realm realm = DataManager.getRealm();\n" +
                "        if (!realm.isClosed()) {\n" +
                "            return realm\n" +
                "                    .where(" + className + ".class)\n" +
                "                    .findAll();\n" +
                "        }\n" +
                "        return null;\n" +
                "    }";
    }

    @Override
    public String toString() {
        return "Table{" +
                "className='" + className + '\'' +
                ", fields=" + fields +
                '}';
    }

    public boolean isPrimaryKey(String field) {
        return field.equalsIgnoreCase(primrayField);
    }
}
