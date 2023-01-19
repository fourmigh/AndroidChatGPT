package org.caojun.chatgpt.demo

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import org.caojun.chatgpt.databinding.ActivityDemoBinding
import org.caojun.library.bean.chatgpt.CompletionReq
import org.caojun.library.bean.chatgpt.CompletionRes
import org.caojun.library.bean.chatgpt.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDemoBinding
    private val adapter = DemoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.etAuthToken.addTextChangedListener {
            checkInput()
        }
        binding.etQuestion.addTextChangedListener {
            checkInput()
        }
        binding.btnAsk.setOnClickListener {
            ask()
        }

        binding.listView.adapter = adapter
    }

    private fun checkInput() {
        val token = binding.etAuthToken.text.toString()
        val question = binding.etQuestion.text.toString()
        binding.btnAsk.isEnabled = !TextUtils.isEmpty(token) && !TextUtils.isEmpty(question)
    }

    private fun setButton(enable: Boolean) {
        binding.btnAsk.isEnabled = enable
        binding.etAuthToken.isEnabled = enable
        binding.etQuestion.isEnabled = enable
    }

    private fun ask() {
        val token = binding.etAuthToken.text.toString()
        val question = binding.etQuestion.text.toString()
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(question)) {
            return
        }
        setButton(false)
        val api = Repository.getApi(token)
        val request = CompletionReq(question)
        api.ask(request).enqueue(object : Callback<CompletionRes> {
            override fun onResponse(call: Call<CompletionRes>, response: Response<CompletionRes>) {
                if (response.isSuccessful) {
                    val body = response.body() ?: return

                    if (body.error == null) {
                        adapter.addData(body, question)
                    }
                }

                setButton(true)
            }

            override fun onFailure(call: Call<CompletionRes>, t: Throwable) {
                Toast.makeText(this@DemoActivity, t.stackTraceToString(), Toast.LENGTH_LONG).show()
                setButton(true)
            }
        })
    }
}