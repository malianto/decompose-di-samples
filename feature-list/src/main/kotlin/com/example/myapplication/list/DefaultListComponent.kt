package com.example.myapplication.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.myapplication.repository.Item
import com.example.myapplication.repository.Repository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding

@AssistedInject
class DefaultListComponent(
    repository: Repository,
    @Assisted componentContext: ComponentContext,
    @Assisted private val onItemSelected: (id: String) -> Unit,
) : ListComponent, ComponentContext by componentContext {
    override val items: Value<List<Item>> = MutableValue(repository.getItems())

    override fun onItemClicked(id: String) {
        onItemSelected(id)
    }

    @ContributesBinding(AppScope::class)
    @AssistedFactory
    fun interface Factory : ListComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            onItemSelected: (id: String) -> Unit,
        ): DefaultListComponent
    }
}
