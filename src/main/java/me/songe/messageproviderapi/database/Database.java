package me.songe.messageproviderapi.database;

import java.sql.Connection;

public interface Database {

    Connection getConnection();

    void close();

    boolean isConnected();

    void init();

}
