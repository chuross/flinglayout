package com.github.chuross.flinglayout

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

class FlingLayout : FrameLayout {

    var isDragEnabled: Boolean = true
    var isDismissEnabled: Boolean = true
    var positionChangeListener: PositionChangeListener? = null
    var dismissListener: DismissListener? = null
    private val threshold: Int = 1500
    private var dragHelper: ViewDragHelper? = null
    private var defaultChildX: Int? = null
    private var defaultChildY: Int? = null
    private var positionRangeRate: Float? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        dragHelper = ViewDragHelper.create(this, 1F, object: ViewDragHelper.Callback() {
            override fun tryCaptureView(child: View?, pointerId: Int): Boolean {
                return isDragEnabled && child?.visibility == View.VISIBLE
            }

            override fun getViewVerticalDragRange(child: View?): Int {
                return 1
            }

            override fun clampViewPositionVertical(child: View?, top: Int, dy: Int): Int {
                return top
            }

            override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
                super.onViewPositionChanged(changedView, left, top, dx, dy)

                val defaultChildY = defaultChildY ?: return
                val rangeY = (measuredHeight / 2)
                val distance = Math.abs(top - defaultChildY)

                positionRangeRate = Math.min(1F, distance.toFloat() / rangeY)
                positionChangeListener?.onPositionChanged(top, left, positionRangeRate ?: 0F)
            }

            override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
                releasedChild?.let { this@FlingLayout.onViewReleased(it, yvel) }
            }
        })

        if (attrs == null) return

        context.obtainStyledAttributes(attrs, R.styleable.FlingLayout, defStyleAttr, 0).also {
            isDragEnabled = it.getBoolean(R.styleable.FlingLayout_fl_isDragEnabled, true)
            isDismissEnabled = it.getBoolean(R.styleable.FlingLayout_fl_isDismissEnabled, true)
        }.recycle()
    }

    private fun onViewReleased(target: View, yvel: Float) {
        positionRangeRate = null

        val x = defaultChildX ?: return
        val y = defaultChildY ?: return

        if (Math.abs(yvel) < threshold) {
            dragHelper?.settleCapturedViewAt(x, y)
            invalidate()
            return
        }

        if (!isDismissEnabled) return

        val targetY = if (yvel > 0) measuredHeight else -target.measuredHeight

        dragHelper?.smoothSlideViewTo(target, x, targetY)
        invalidate()

        dismissListener?.onDismiss()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return dragHelper?.shouldInterceptTouchEvent(ev) ?: false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        dragHelper?.processTouchEvent(event)
        return true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (childCount == 0) return
        if (childCount > 1) throw IllegalStateException("child must be single: current child count=$childCount")

        defaultChildX = getChildAt(0)?.left
        defaultChildY = getChildAt(0)?.top
    }

    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper?.continueSettling(true) == true) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    interface PositionChangeListener {

        fun onPositionChanged(top: Int, left: Int, positionRangeRate: Float)
    }

    interface DismissListener {

        fun onDismiss()
    }

}