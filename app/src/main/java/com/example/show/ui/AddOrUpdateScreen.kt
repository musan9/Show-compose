package com.example.show.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.ui.tooling.preview.Preview
import com.example.show.data.AppDataBase
import com.example.show.data.daos.GoodsDao
import com.example.show.data.entities.Goods
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


@Composable
fun AddOrUpdateGoodsScreen(onBreak : ()->Unit, goods : Goods? = null,
                           goodsDao : GoodsDao = AppDataBase.appDataBase.getGoodsDao()) {
    val newGoods = goods?.copy()?: Goods()
    Column(modifier = Modifier.padding(12.dp)) {
        var goodsName = TextFieldValue(newGoods.goodsName)
        var goodsPrice = newGoods.price
        TextField(
            value = goodsName,
            onValueChange = {
                goodsName = it
            }
        )
        TextField(
            value = goodsPrice.toString(),
            onValueChange = {
                goodsPrice = it.toDoubleOrNull()?:0.0
            }
        )
        Button(onClick = {}) {
            newGoods.goodsName = goodsName.text
            newGoods.price = goodsPrice

            LaunchedEffect(Unit) {
                withContext(IO) {
                    goodsDao.insertGoods(newGoods)
                }
                onBreak()
            }
        }
    }
}

@Preview("add goods")
@Composable
fun PreAddGoods() {
    AddOrUpdateGoodsScreen(onBreak = {})
}

@Preview("edit goods")
@Composable
fun PreEditGoods() {
    AddOrUpdateGoodsScreen(onBreak = {}, Goods(0,goodsName = "test","this is test",100,2.2))
}

