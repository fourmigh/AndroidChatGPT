package org.caojun.chatgpt.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.caojun.chatgpt.R
import org.caojun.library.bean.chatgpt.CompletionRes
import org.caojun.library.clipboard.ClipboardUtils
import java.text.SimpleDateFormat

class DemoAdapter(private val context: Context) : BaseAdapter() {

    private val list = ArrayList<CompletionRes>()
    private val sdfAsk = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val sdfAnswer = SimpleDateFormat("HH:mm:ss")

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): CompletionRes {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        var convertView = view

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_demo, null)

            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val item = getItem(position)

        val askTime = sdfAsk.format(item.askTime)
        val answerTime = sdfAnswer.format(item.answerTime)
        holder.tvTitle.hint = "$askTime - $answerTime (${item.answerTime - item.askTime}ms)"
        holder.tvAnswer.setText("${item.question}${item.choices[0].text}")
        holder.btnCopy.setOnClickListener {
            ClipboardUtils.copy(context, holder.tvAnswer.text.toString(), holder.tvAnswer.text.toString())
        }

        return convertView!!
    }

    private inner class ViewHolder(root: View) {
        val tvTitle = root.findViewById<TextInputLayout>(R.id.tvTitle)
        val tvAnswer = root.findViewById<TextInputEditText>(R.id.tvAnswer)
        val btnCopy = root.findViewById<Button>(R.id.btnCopy)
    }

    fun addData(askTime: Long, completionRes: CompletionRes, question: String) {
        completionRes.askTime = askTime
        completionRes.answerTime = System.currentTimeMillis()
        completionRes.question = question
        list.add(0, completionRes)
        notifyDataSetChanged()
    }
}