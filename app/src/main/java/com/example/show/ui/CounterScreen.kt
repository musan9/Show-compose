package com.example.show.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.ui.tooling.preview.Preview
import com.example.show.data.AppDataBase
import com.example.show.data.daos.GoodsDao
import com.example.show.data.entities.Goods
import com.example.show.ui.theme.GoodsTheme

@Composable
fun CounterScreen(
    screen: Screen,
    viewModel : NavigationViewModel,
    goodsDao : GoodsDao = AppDataBase.appDataBase.getGoodsDao()
) {
    val navigate = viewModel::navigateTo
    val goodsList = goodsDao.loadGoods().observeAsState()
    val selectMap = mutableMapOf<Goods,MutableState<Int>>()
    val selectSpecies = mutableStateOf(0)
    Column {
        CounterAppBar(navigate)
        Box(modifier = Modifier.weight(1f)) {
            showGoodsList(
                goodsList = goodsList,
                selectSpecies,
                selectMap = selectMap,
                navigate = navigate
            )
            if (screen.back.value) {
                showDialogSelect(selectMap = selectMap) {
                    screen.back.value = false
                }
            }
        }
        Row {
            Button(onClick = {
                screen.back.value = !screen.back.value
            }) {
                if (screen.back.value) {
                    Text(text = "hint it")
                } else {
                    Text(text = "show it")
                }
            }
            Text(text = "${selectMap.entries.fold(0.0){a,b-> 
                a+ b.key.price * b.value.value 
            }}")
        }
    }
}

@Composable
fun showGoodsList(
    goodsList : State<MutableList<Goods>?>,
    selectSpecies : MutableState<Int>,
    selectMap : MutableMap<Goods,MutableState<Int>>,
    navigate: (Screen) -> Unit
) {
    val list = goodsList.value
    if (list == null) {
        Button(onClick = {
            navigate(Screen.AddOrUpdate())
        }) {
            Text(text = "添加")
        }
    } else {
        LazyColumnFor(items = list) {goods : Goods->
            CounterItem(
                goods = goods,
                selectSpecies = selectSpecies,
                selectSum = selectMap.getOrPut(goods){ mutableStateOf(0)}
            )
        }
    }
}

@Composable
fun showDialogSelect(
    selectMap : MutableMap<Goods,MutableState<Int>>,
    hintIt : ()->Unit
) {
    Column {
        Button(onClick = {
            hintIt()
        }) {
            Text(text = "隐藏")
        }
        LazyColumnFor(items = selectMap.entries.toList()) {items->
            val (goods,selectNum) = items
            Row(modifier = Modifier.padding(4.dp)) {
                Text(text = goods.goodsName)
                Text(text = "  × ${selectNum.value}")
                Text(text = " = ${goods.price*selectNum.value}")
            }
        }
    }
}

@Composable
fun CounterAppBar(navigate : (Screen)->Unit) {
    TopAppBar {
        Row {
            Text(text = "计算")
            Button(onClick = {
                navigate(Screen.AddOrUpdate())
            }) {
                Text(text = "添加")
            }
            Button(onClick = {
                navigate(Screen.Edit)
            }) {
                Text(text = "编辑")
            }
        }
    }
}

@Composable
fun CounterItem(goods: Goods,
                selectSpecies: MutableState<Int>,
                selectSum : MutableState<Int>) {
    Column(modifier = Modifier.height(80.dp)) {
        Text(text = goods.goodsName)
        Row(modifier = Modifier.padding(4.dp)) {
            Button(
                onClick = {
                if (selectSum.value>0) {
                    selectSum.value--
                    if (selectSum.value == 0) {
                        selectSpecies.value--
                    }
                }
            }) {
                Text(text = "减少")
            }
            Text(modifier = Modifier.padding(6.dp),text = selectSum.value.toString())
            Button(onClick = {
                if (selectSum.value == 0) {
                    selectSpecies.value++
                }
                selectSum.value++
            }) {
                Text(text = "增加")
            }
        }
    }
}

@Preview(" Counter card")
@Composable
fun PreCounterItem() {
    GoodsTheme(false) {
        CounterItem(
            goods = Goods(0L,"test","this is test",12,12.5,""),
            selectSpecies = mutableStateOf(0),
            selectSum = mutableStateOf(0)
        )
    }
}

@Preview("Counter Screen")
@Composable
fun PreviewCounter() {
    CounterScreen(Screen.Counter,
        viewModel(modelClass = NavigationViewModel::class.java),
        object : GoodsDao{
            override fun loadGoods(): LiveData<MutableList<Goods>> {
                return liveData {
                    emit(mutableListOf<Goods>())
                }
            }

            override fun insertGoods(goods: Goods) {

            }

            override fun editGoods(goods: Goods) {

            }

            override fun deleteGoods(goods: Goods) {

            }

        }

    )
}