package com.zk.base_project.views.customviews

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ListPopupWindow
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.zk.base_project.R
import com.zk.base_project.data.SpinnerItem
import com.zk.base_project.data.enums.ColorTheme
import com.zk.base_project.utils.Utils
import com.zk.base_project.views.adapters.SpinnerAdapter

class CustomSpinner(context: Context, attributeSet: AttributeSet) :
    AppCompatTextView(context, attributeSet) {

    private var _downArrowIconTint = android.R.color.black
    private var _drawableIconSize = 50
    private var _drawableIcon = -1
    private var _spinnerType: Type = Type.IMAGE
    private var _spinnerDropDownType: Type = Type.TEXT
    var isSpinnerEnabled = true
    private var maxDrawableSize = 60

    private var downArrowDrawable: Drawable? = resources.getDrawable(R.drawable.ic_down, null)

    private var colorTheme: ColorTheme = ColorTheme.NONE
    private var selectedItem: SpinnerItem = SpinnerItem()
    private var itemsList = mutableListOf<SpinnerItem>()
    private var listener: ((Int, SpinnerItem) -> Unit)? = null
    private var dropDownEnabled = true


    init {
        setXmlAttributes(attributeSet)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        drawDropdownDrawable()
        setDropDownTint()
        drawDrawable(_drawableIcon, Gravity.LEFT, _drawableIconSize)

        setOnClickListener(null)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _popup?.dismiss()
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        super.setOnClickListener() {
            if(isSpinnerEnabled){
                if (dropDownEnabled){
                    showPopupMenu()
                }
                listener?.onClick(this)
            }
        }

    }

    private fun setXmlAttributes(attributeSet: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSpinner)
        typedArray.apply {

            if (hasValue(R.styleable.CustomSpinner_spinner_drawable)) {
                _drawableIcon =
                    getResourceId(R.styleable.CustomSpinner_spinner_drawable, _drawableIcon)
            }

            if (hasValue(R.styleable.CustomSpinner_spinner_drawableSize)) {
                _drawableIconSize = getInteger(
                    R.styleable.CustomSpinner_spinner_drawableSize,
                    _drawableIconSize
                )
            }

            if (hasValue(R.styleable.CustomSpinner_spinner_downArrowIconTint)) {
                _downArrowIconTint =
                    getResourceId(
                        R.styleable.CustomSpinner_spinner_downArrowIconTint,
                        _downArrowIconTint
                    )
            }

            if (hasValue(R.styleable.CustomSpinner_spinner_type)) {
                _spinnerType = Type.get(
                    getInteger(
                        R.styleable.CustomSpinner_spinner_type,
                        _spinnerType.id
                    )
                )
            }

            if (hasValue(R.styleable.CustomSpinner_spinner_dropDownType)) {
                _spinnerDropDownType =
                    Type.get(
                        getInteger(
                            R.styleable.CustomSpinner_spinner_dropDownType,
                            _spinnerDropDownType.id
                        )
                    )
            }

            if (hasValue(R.styleable.CustomSpinner_spinner_items)) {
                val arrayResId = getResourceId(R.styleable.CustomSpinner_spinner_items, -1)
                setItems(arrayResId)
            }

            if (hasValue(R.styleable.CustomSpinner_spinner_is_enabled)) {
                isSpinnerEnabled = getBoolean(R.styleable.CustomSpinner_spinner_is_enabled, false)
            }
        }
    }

    //region methods for drawing
    private fun drawDropdownDrawable() {

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, downArrowDrawable, null)
    }

    private fun drawDrawable(drawableId: Int, gravity: Int = Gravity.LEFT, size: Int) {

        var d: Drawable? = null
        if (drawableId > 0) {
            d = if (size > 0) {
                Utils.scaleDrawable(drawableId, maxDrawableSize)
            } else {
                resources.getDrawable(drawableId, null)
            }
        }

        when (gravity) {
            Gravity.LEFT -> setCompoundDrawablesRelativeWithIntrinsicBounds(
                d, null, downArrowDrawable, null
            )

            Gravity.TOP -> setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, d, downArrowDrawable, null
            )

            Gravity.BOTTOM -> setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, downArrowDrawable, d
            )
        }

    }

    private fun setDropDownTint() {
        if (compoundDrawablesRelative[2] != null) {
            compoundDrawablesRelative[2].colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(context, _downArrowIconTint), PorterDuff.Mode.SRC_IN
            )
        }

    }

    private fun drawDrawableTint() {

        if (compoundDrawablesRelative[0] != null) {
            if (colorTheme != ColorTheme.NONE && colorTheme != ColorTheme.BLUE)
                compoundDrawablesRelative[0].colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(context, colorTheme.colorId),
                    PorterDuff.Mode.SRC_IN
                )
        }

    }
    //endregion
    private var _popup: ListPopupWindow?=null

    private fun showPopupMenu() {
        _popup = ListPopupWindow(context)

        val adapter = SpinnerAdapter(itemsList, _spinnerDropDownType, _popup!!           )

        adapter.setOnItemSelectListener() { position, item ->

            updateSpinnerViews(item)

            this.listener?.invoke(position, item)
        }

//        popup.height = ListPopupWindow.WRAP_CONTENT
        _popup?.height = if (itemsList.size > 4) 500 else ListPopupWindow.WRAP_CONTENT
        _popup?.width = Math.max(450, width)
        _popup?.anchorView = this
        _popup?.setAdapter(adapter)
        _popup?.show()
    }

    private fun updateSpinnerViews(spinnerItem: SpinnerItem) {

        when (_spinnerType) {
            Type.IMAGE -> {
                setIcon(spinnerItem.iconId)
                text = ""
                drawDrawableTint()
            }
            Type.TEXT -> {
                setIcon(-1)
                text = spinnerItem.textEng
            }
            Type.IMAGE_TEXT -> {
                setIcon(spinnerItem.iconId)
                text = spinnerItem.textEng
                drawDrawableTint()
            }
        }
    }

    fun setDropDownIconTint(colorId: Int) {

        _downArrowIconTint = colorId
        setDropDownTint()
    }

    fun setDrawableSize(size: Int) {

        _drawableIconSize = size
        drawDrawable(_drawableIcon, Gravity.LEFT, _drawableIconSize)
    }

    private fun setIcon(drawableId: Int) {

        _drawableIcon = drawableId
        drawDrawable(drawableId, Gravity.LEFT, _drawableIconSize)
    }

    fun setItems(
        itemsList: MutableList<SpinnerItem>?,
        spinnerType: Type = _spinnerType,
        dropDownType: Type = _spinnerDropDownType
    ) {
        if (itemsList == null)
            return
        this._spinnerType = spinnerType
        this._spinnerDropDownType = dropDownType

        this.itemsList = itemsList

        if (itemsList.size > 0 && hint.isNullOrEmpty())
            updateSpinnerViews(itemsList[0])
    }

    fun setItems(
        arrayResId: Int,
        spinnerType: Type = _spinnerType,
        dropDownType: Type = _spinnerDropDownType
    ) {

        var stringArray = resources.getStringArray(arrayResId)

        var items: MutableList<SpinnerItem> = mutableListOf()

        stringArray.forEachIndexed { index, value ->
            items.add(
                SpinnerItem(
                    index,
                    _textEng = value
                )
            )
        }

        setItems(items, spinnerType, dropDownType)
    }

    //returns index and selected spinner item
    fun setOnItemSelectListener(listener: (Int, SpinnerItem) -> Unit) {
        this.listener = listener
    }

    fun getSelectedItem(): SpinnerItem {
        return selectedItem
    }

    fun setSelectedItem(item: SpinnerItem?) {
        if (item == null)
            return
        this.selectedItem = item
        updateSpinnerViews(item)
    }

    fun setSelectedItemId(itemId: Int) {

        for (item in this.itemsList) {
            if (item.id == itemId) {
                this.selectedItem = item
                updateSpinnerViews(item)
                break
            }
        }
    }

    fun setDropDownEnabled(enable: Boolean) {
        dropDownEnabled = enable

        downArrowDrawable = if (enable)
            resources.getDrawable(R.drawable.ic_down, null)
        else
            null

        setIcon(_drawableIcon)
    }

    fun setDrawableTint(colorTheme: ColorTheme) {
        this.colorTheme = colorTheme
        drawDrawableTint()
    }

    enum class Type(var id: Int) {

        IMAGE(0),
        TEXT(1),
        IMAGE_TEXT(2);

        companion object {
            open fun get(id: Int): Type {

                for (item in values()) {
                    if (item.id == id) return item
                }

                return TEXT
            }
        }
    }

}