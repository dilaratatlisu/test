package com.dilaratatlisu.javamaps.roomdb;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dilaratatlisu.javamaps.model.Locations;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM Locations")
    Flowable<List<Locations>> getAll();

    @Insert
    Completable insert(Locations locations);

    @Delete
    Completable delete(Locations locations);

}
