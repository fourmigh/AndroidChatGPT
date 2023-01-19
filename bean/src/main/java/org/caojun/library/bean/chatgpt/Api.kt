package org.caojun.library.bean.chatgpt

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    companion object {
        private const val URL_COMPLETIONS = "v1/completions"
    }

    @POST(URL_COMPLETIONS)
    fun ask(@Body req: CompletionReq): Call<CompletionRes>
}