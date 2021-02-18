package com.example.show.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.show.ShowApplication
import com.example.show.data.daos.GoodsDao
import com.example.show.data.entities.Goods

@Database(
    entities = [
        Goods::class
    ],
    version = 0
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getGoodsDao() : GoodsDao
    companion object {
        val appDataBase by lazy(this) {
            Room.databaseBuilder(
                ShowApplication.applicationContext(),
                AppDataBase::class.java, "goods.db"
            ).build()
        }
    }
}