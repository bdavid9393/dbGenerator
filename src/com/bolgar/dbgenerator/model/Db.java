package com.bolgar.dbgenerator.model;

import javafx.scene.control.Tab;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2017.12.06..
 */
public class Db {
    List<Table> tables = new ArrayList<>();
    List<String> rawTables = new ArrayList<>();

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<String> getRawTables() {
        return rawTables;
    }

    public void addRawTable(String rawTable) {
        getRawTables().add(rawTable);
    }

    public void addTable(Table table) {
        getTables().add(table);
    }

    public void setRawTables(List<String> rawTables) {
        this.rawTables = rawTables;
    }

    public ArrayList<String> ignoredTables() {
        ArrayList<String> ignoreTableList = new ArrayList<>();
        ignoreTableList.add("migrate");
        return ignoreTableList;

    }

    public void checkIgnore() {
        List<String> ignoreList = new ArrayList<>();
        ignoreList.add("migrations");
        ignoreList.add("oauth_access_tokens");
        ignoreList.add("oauth_auth_codes");
        ignoreList.add("oauth_clients");
        ignoreList.add("oauth_personal_access_clients");
        ignoreList.add("oauth_refresh_tokens");
        ignoreList.add("migrations");
        ignoreList.add("oauthAccessTokens");
        ignoreList.add("oauthAuthCodes");
        ignoreList.add("oauthClients");
        ignoreList.add("oauthPersonalAccessClients");
        ignoreList.add("oauthRefreshTokens");
        for (Table table : tables) {
            for (String ignoredTable : ignoreList) {
                if (table.getClassName().equalsIgnoreCase(ignoredTable)) {
                    table.setIgnored(true);
                }

            }
        }

    }


    @Override
    public String toString() {
        return "Db{" +
                "tables=" + tables +
                ", rawTables=" + rawTables +
                '}';
    }



}
