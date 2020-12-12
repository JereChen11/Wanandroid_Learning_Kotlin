package com.wanandroid.kotlin.utils

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

class RecyclerItemClickListener(
    context: Context?,
    recyclerView: RecyclerView,
    private val mListener: OnItemClickListener?
) : OnItemTouchListener {

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onLongItemClick(view: View?, position: Int)
    }

    private val mGestureDetector: GestureDetector
    override fun onInterceptTouchEvent(
        recyclerView: RecyclerView,
        motionEvent: MotionEvent
    ): Boolean {
        val childView =
            recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(motionEvent)) {
            mListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(
        recyclerView: RecyclerView,
        motionEvent: MotionEvent
    ) {
    }

    override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {}

    init {
        mGestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val childView =
                    recyclerView.findChildViewUnder(e.x, e.y)
                if (childView != null && mListener != null) {
                    mListener.onLongItemClick(
                        childView,
                        recyclerView.getChildAdapterPosition(childView)
                    )
                }
            }
        })
    }
}