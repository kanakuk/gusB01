package com.example.gusb01.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gusb01.dao.CourseDAO;
import com.example.gusb01.dao.TermDAO;
import com.example.gusb01.entities.Course;
import com.example.gusb01.entities.Term;

@Database(entities = {Term.class, Course.class}, version = 2, exportSchema = false)
public abstract class WguDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();

    private static volatile WguDatabaseBuilder INSTANCE;

    static WguDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (WguDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),WguDatabaseBuilder.class, "myDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
