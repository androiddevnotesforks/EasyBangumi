package com.heyanle.easybangumi4.compose.source_manage.source

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heyanle.bangumi_source_api.api.Source
import com.heyanle.easybangumi4.preferences.SourcePreferences
import com.heyanle.easybangumi4.source.SourceLibraryController
import com.heyanle.injekt.core.Injekt
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by HeYanLe on 2023/4/1 21:34.
 * https://github.com/heyanLE
 */
class SourceViewModel : ViewModel() {


    val sourceMapState =
        mutableStateOf<Map<String, Source>>(mapOf())

    val sourceLibraryState =
        mutableStateOf<List<String>>(emptyList())

    val configState =
        mutableStateOf<Map<String, SourcePreferences.SourceConfig>>(mapOf())

    private val sourceController: SourceLibraryController by Injekt.injectLazy()

    init {
        viewModelScope.launch {
            sourceController.sourceLibraryFlow.collectLatest { libs ->
                if (libs.size != sourceLibraryState.value.size) {
                    sourceLibraryState.value = libs.sortedBy {
                        it.second.order
                    }.map { it.first.key }

                    val map = hashMapOf<String, SourcePreferences.SourceConfig>()
                    val sourceMap = hashMapOf<String, Source>()
                    libs.forEach {
                        map[it.second.key] = it.second
                        sourceMap[it.second.key] = it.first
                    }
                    configState.value = map
                    sourceMapState.value = sourceMap
                    return@collectLatest
                }
                var isUpdate = false
                val oldSet = sourceLibraryState.value.toSet()
                libs.forEach {
                    if (!oldSet.contains(it.first.key)) {
                        isUpdate = true
                    }
                }
                if (isUpdate) {
                    sourceLibraryState.value = libs.sortedBy {
                        it.second.order
                    }.map { it.first.key }

                    val map = hashMapOf<String, SourcePreferences.SourceConfig>()
                    val sourceMap = hashMapOf<String, Source>()
                    libs.forEach {
                        map[it.second.key] = it.second
                        sourceMap[it.second.key] = it.first
                    }
                    configState.value = map
                    sourceMapState.value = sourceMap
                }
            }

        }
    }

    fun move(from: Int, to: Int) {
        sourceLibraryState.value = sourceLibraryState.value.toMutableList().apply {
            add(to, removeAt(from))

        }
    }

    fun onDragEnd() {
        val config = configState.value.toMutableMap()
        sourceLibraryState.value.forEachIndexed { i, it ->
            if (config.containsKey(it)) {
                config[it] = config[it]!!.copy(order = i)
            }
        }
        sourceController.newConfig(config)

    }

    fun enable(sourceKey: String) {
        val map = configState.value.toMutableMap()
        map[sourceKey]?.let {
            map[sourceKey] = it.copy(enable = true)
        }
        configState.value = map
        sourceController.enable(sourceKey)
    }

    fun disable(sourceKey: String) {
        val map = configState.value.toMutableMap()
        map[sourceKey]?.let {
            map[sourceKey] = it.copy(enable = false)
        }
        configState.value = map

        // 不会修改数量，这里不会触发刷新
        sourceController.disable(sourceKey)
    }

}