package com.rahuls.spacex;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MemberDao {

    @Query("SELECT * FROM task")
    List<Member> getAll();

    @Insert
    void insert(Member member);

    @Query("DELETE FROM task")
    void delete();

}
