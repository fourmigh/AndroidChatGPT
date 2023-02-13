package org.caojun.library.bean.chatgpt

import org.caojun.library.net.RetrofitManager

object Repository {

    fun getApi(authorization: String, timeout: Int, url: String = "https://api.openai.com/"): Api {
        return RetrofitManager.getInstance(url, authorization, timeout).getServiceApi(Api::class.java)
    }
}