package com.bolgar.dbgenerator.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2017.12.06..
 */
public class GeneradedModel {
    String className;
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



    @Override
    public String toString() {
        return "GeneradedModel{" +
                "className='" + className + '\'' +
                ", fields=" + fields +
                '}';
    }
}
