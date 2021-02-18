package com.example.show.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.show.data.AppDataBase
import com.example.show.data.daos.GoodsDao
import com.example.show.data.entities.Goods

@Composable
fun EditStateScreen(onBreak : ()->Unit,
                    goodsDao : GoodsDao = AppDataBase.appDataBase.getGoodsDao()) {
    Column(modifier = Modifier.padding(12.dp)) {

    }
}