package com.example.myapplication.android

import com.example.myapplication.root.RootComponent
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
interface AppGraph {

    val rootComponentFactory: RootComponent.Factory
}
