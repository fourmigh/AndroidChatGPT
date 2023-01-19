package org.caojun.library.bean.chatgpt

import org.caojun.library.net.RetrofitManager

object Repository {

    fun getApi(authorization: String, url: String = "https://api.openai.com/"): Api {
        return RetrofitManager.getInstance(url, authorization).getServiceApi(Api::class.java)
    }
}