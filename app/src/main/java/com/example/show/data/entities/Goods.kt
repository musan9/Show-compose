package com.example.show.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goods(
    @PrimaryKey
    var goodsId : Long = 0,
    var goodsName : String = "",
    var goodsDescription : String = "",
    var inventory : Int = 0,
    var price : Double = 0.0,
    var thumbnail : String? = null
) {
    fun getIcon() {

    }
}