package com.dilaratatlisu.javamaps.roomdb;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dilaratatlisu.javamaps.model.Locations;

@Database(entities = {Locations.class}, version = 1)
public abstract class PlaceDataBase extends RoomDatabase {
    public abstract PlaceDao placeDao();
}
