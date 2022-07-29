package com.nursyah.bettersleep.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nursyah.bettersleep.R
import com.nursyah.bettersleep.entity.Sleep

class SleepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView
    init { textView = itemView.findViewById(R.id.textview_sleep_recycle) }
}

class SleepListAdapter(context: Context?) : RecyclerView.Adapter<SleepViewHolder>() {
    private var sleepList: List<Sleep>? = null
    private val inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val itemView = inflater.inflate(R.layout.recycleview_sleep, parent, false)
        return SleepViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val res = "${sleepList!![position].date} ${Utils.formatTime(sleepList!![position].duration)}"
        holder.textView.text = res
    }


    override fun getItemCount(): Int {
        return if (sleepList != null) sleepList!!.size else 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSleepList(sleepList: List<Sleep>?) {
        this.sleepList = sleepList
        notifyDataSetChanged()
    }

    init { inflater = LayoutInflater.from(context) }
}