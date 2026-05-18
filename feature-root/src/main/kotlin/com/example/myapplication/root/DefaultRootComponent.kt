package com.example.myapplication.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.myapplication.details.DetailsComponent
import com.example.myapplication.list.ListComponent
import com.example.myapplication.root.RootComponent.Child.DetailsChild
import com.example.myapplication.root.RootComponent.Child.ListChild
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import kotlinx.serialization.Serializable

@AssistedInject
class DefaultRootComponent(
    private val listFactory: ListComponent.Factory,
    private val detailsFactory: DetailsComponent.Factory,
    @Assisted componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val nav = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = nav,
            initialConfiguration = Config.List,
            serializer = Config.serializer(),
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, context: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.List -> ListChild(listComponent(context))
            is Config.Details -> DetailsChild(detailsComponent(context, config))
        }

    private fun listComponent(context: ComponentContext): ListComponent =
        listFactory(
            componentContext = context,
            onItemSelected = { nav.push(Config.Details(itemId = it)) },
        )

    private fun detailsComponent(context: ComponentContext, config: Config.Details): DetailsComponent =
        detailsFactory(
            componentContext = context,
            itemId = config.itemId,
            onFinished = nav::pop,
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class Details(val itemId: String) : Config
    }

    @ContributesBinding(AppScope::class)
    @AssistedFactory
    fun interface Factory : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): DefaultRootComponent
    }
}
