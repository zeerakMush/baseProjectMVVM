package com.zk.base_project.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.zk.base_project.BR

data class SpinnerItem(
        private var _id: Int = 0,
        private var _iconId: Int = 0,
        private var _textEng: String = "",
        private var _textSpanish: String = ""

) : BaseObservable() {
    @get:Bindable
    var id: Int = _id
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @get:Bindable
    var iconId: Int = _iconId
        set(value) {
            field = value
            notifyPropertyChanged(BR.iconId)
        }

    @get:Bindable
    var textEng: String = _textEng
        set(value) {
            field = value
            notifyPropertyChanged(BR.textEng)
        }

    @get:Bindable
    var textSpanish: String = _textSpanish
        set(value) {
            field = value
            notifyPropertyChanged(BR.textSpanish)
        }
}