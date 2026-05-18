package com.example.myapplication.details

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
class DefaultDetailsComponent(
    repository: Repository,
    @Assisted componentContext: ComponentContext,
    @Assisted itemId: String,
    @Assisted private val onFinished: () -> Unit,
) : DetailsComponent, ComponentContext by componentContext {
    override val item: Value<Item> = MutableValue(repository.getItem(id = itemId))

    override fun onCloseClicked() {
        onFinished()
    }

    @ContributesBinding(AppScope::class)
    @AssistedFactory
    fun interface Factory : DetailsComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            itemId: String,
            onFinished: () -> Unit,
        ): DefaultDetailsComponent
    }
}
