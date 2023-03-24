package com.eck.compose_migration.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(coroutineContext: CoroutineContext) : ViewModel() {
    private val parentJob = Job()

    protected val scope = CoroutineScope(parentJob + coroutineContext)
    val isLoading = MutableLiveData(false)

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}