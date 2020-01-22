package com.example.neurocast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.date_list_item.view.*

class DateListAdapter(
    private val items: ArrayList<HashMap<String, String>>,
    private val context: Context,
    private val mListener: OnItemClickListener?
) :
    RecyclerView.Adapter<DateListAdapter.DateListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(today: HashMap<String, String>, yesterday: HashMap<String, String>)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateListViewHolder {
        return DateListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.date_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateListViewHolder, position: Int) {
        holder.today.text = items[position]["date"]

        holder.today.setOnClickListener {
            mListener!!.onItemClick(items[position], items[position + 1])
        }
    }


    class DateListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val today: TextView = view.date
    }
}