package com.zk.base_project.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

typealias Validator = ((String, String?,String?) -> String?)?

class EditField(
        scope: CoroutineScope,
        private var validator: Validator = null,
        private var message: String = "",
        private var compareString: LiveData<String>? = null
) {
    val value: MutableLiveData<String> = MutableLiveData("")
    val error: MutableLiveData<String?> = MutableLiveData(null)

    val isNotEmpty get() = !value.value.isNullOrEmpty()
    val valid: Boolean get() = error.value == null
    val forceValue get() = value.value!!

    private var onChanged: ((String) -> Unit)? = null

    init {
        scope.launch {
            value.asFlow().collect {
                onChanged?.invoke(it)
                error.value = validator?.invoke(it, message,compareString?.value)
            }
        }
    }

    fun onChangedText(onChanged: (String) -> Unit): EditField {
        this.onChanged = onChanged
        return this
    }

    fun validator(validator: Validator): EditField {
        this.validator = validator
        return this
    }
}

/**
 * @return LiveData<Boolean> which is false if any EditField is empty
 */
fun List<EditField>.nonEmptyFields(): LiveData<Boolean> {
    val live = MutableLiveData(false)
    forEach { field ->
        field.onChangedText { _ ->
            live.value = all { it.isNotEmpty }
        }
    }
    return live
}

/**
 * @returns true if all fields valid
 */
val List<EditField>.allValid get() = all { it.valid }

fun List<EditField>.getError(): String? {
    forEach {
        if (it.valid.not()) {
            return it.error.value
        }
    }
    return null
}
