package com.example.ezorder

import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.listview_popup_whenorder_options.view.*
import org.json.JSONArray
import org.json.JSONObject

class optionAdapter(val context: Context, val optionlist: JSONArray) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.listview_popup_whenorder_options, null)
        val textChoice = view.listview_popup_whenorder_choice
        val option=optionlist.getJSONObject(position)
        textChoice.text=option.getString("text")
        val radioGroup=view.findViewById<RadioGroup>(R.id.listview_popup_whenorder_radiogroup)
        var choices=option.getString("choice")
        choices=choices.substring(1,choices.length-1)
        val parts=choices.split(",")
        for(i in parts){
            val radioButt= RadioButton(context)
            radioButt.id=View.generateViewId()
            radioButt.setText(i)
            radioGroup.addView(radioButt)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return optionlist.getJSONObject(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return optionlist.length()
    }

}