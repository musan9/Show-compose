package com.example.show.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.show.data.entities.Goods

@Dao
interface GoodsDao {
    @Query("SELECT * FROM goods")
    fun loadGoods() : LiveData<MutableList<Goods>>

    @Insert
    fun insertGoods(goods: Goods)

    @Update
    fun editGoods(goods: Goods)

    @Delete
    fun deleteGoods(goods: Goods)

//    @Query("SELECT * FROM goods ORDER BY goodsId ASC")
//    fun pagingSource(): PagingSource<Int, Goods>
}