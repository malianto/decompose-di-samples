package com.example.myapplication.repository

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class DefaultRepository : Repository {

    private val itemMap: Map<String, Item> =
        List(100) { index ->
            Item(
                id = index.toString(),
                title = "Item $index",
                text = "Item $index. ".repeat(1000),
            )
        }.associateBy(Item::id)

    override fun getItems(): List<Item> =
        itemMap.values.toList()

    override fun getItem(id: String): Item =
        itemMap.getValue(key = id)
}
