package com.example.gallery.helper


import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout


internal class SquareLayout : RelativeLayout {
    constructor(context: Context?) : super(context)

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    )

    // этот конструктор мб и не пригодится
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}