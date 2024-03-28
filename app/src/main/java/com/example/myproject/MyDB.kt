package com.example.myproject
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyEntity::class], version = 1)
abstract class MyDB:RoomDatabase(){
    abstract fun myDao():MyDAOInterface
}
