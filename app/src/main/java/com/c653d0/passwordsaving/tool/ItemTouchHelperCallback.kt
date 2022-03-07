package com.c653d0.passwordsaving.tool

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.c653d0.passwordsaving.PasswordViewModel
import com.c653d0.passwordsaving.database.PasswordInfo

class ItemTouchHelperCallback : ItemTouchHelper.Callback() {
    private val TAG = "ItemTouchHelpLog"
    private var list:List<PasswordInfo> = ArrayList()
    private var viewModel:PasswordViewModel? = null
    
    fun setList(list:List<PasswordInfo>?){
        list?.let {
            this.list = it
        }
    }

    fun setViewModel(viewModel:PasswordViewModel){
        this.viewModel = viewModel
    }
    
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlag = ItemTouchHelper.LEFT
        val swipeFlag = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlag,swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.d(TAG, "getMovementFlags: onMoved")
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val password = list[position]
        viewModel?.delete(password)
    }
}