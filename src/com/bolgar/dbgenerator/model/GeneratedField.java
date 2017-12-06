package com.bolgar.dbgenerator.model;

/**
 * Created by david on 2017.12.06..
 */
public class GeneratedField {
    String fieldName;
    String fieldType;
    String parentClass;

    public GeneratedField() {

    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getParentClass() {
        return parentClass;
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    public String toString() {
        return "GeneratedField{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }

    public String getJSONName() {
        return "public static final String JSON_" + fieldName.toUpperCase() + "=\"" + fieldName + "\";";
    }

    public String getDBName() {
        return "public static final String DB_" + fieldName.toUpperCase() + "=\"" + getUpperCasedFieldName() + "\";";
    }

    public String getField() {
        return "@SerializedName(" + "JSON_" + fieldName.toUpperCase() + ")\n"
                + "private " + fieldType + " " + getUpperCasedFieldName() + ";";
    }

    public String getGetter() {
        return "public " + fieldType + " get" + getUpperCasedFieldNameUpperStart() + "(){\nreturn this." + getUpperCasedFieldName() + ";\n}";
    }

    public String getSetter() {


        return "public void set" + getUpperCasedFieldNameUpperStart() + "(final " + fieldType + " " + getUpperCasedFieldName() + "){" +
                "\n if (isManaged()) {\n" +
                "Realm realm = DataManager.getRealm();\n" +
                "if (!realm.isClosed()) {\n" +
                "realm.executeTransaction(new Realm.Transaction() {\n" +
                "@Override" +
                " public void execute(Realm realm) {" +
                getParentClass() + ".this." + getUpperCasedFieldName() + "=" + getUpperCasedFieldName() + ";}});}}\nelse{" +
                "this." + getUpperCasedFieldName() + "=" + getUpperCasedFieldName() + ";}}";
    }

    public String getUpperCasedFieldNameUpperStart() {
        StringBuffer buff = new StringBuffer();
        Character bigchar = fieldName.charAt(0);
        buff.append(bigchar.toString().toUpperCase());
        for (int i = 1; i != fieldName.length(); i++) {
            Character c = fieldName.charAt(i);
            if (c == '_') {
                Character c2 = fieldName.charAt(i + 1);
                buff.append(c2.toString().toUpperCase());
                i++;
            } else {
                buff.append(c);
            }
        }
        return buff.toString();

    }

    public String getUpperCasedFieldName() {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i != fieldName.length(); i++) {
            Character c = fieldName.charAt(i);
            if (c == '_') {
                Character c2 = fieldName.charAt(i + 1);
                buff.append(c2.toString().toUpperCase());
                i++;
            } else {
                buff.append(c);
            }
        }
        return buff.toString();
    }
}
