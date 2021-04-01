package com.rahuls.spacex;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Member.class} ,  version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MemberDao memberDao();
}
