package org.caojun.library.bean.chatgpt

class CompletionReq(private val prompt: String = "你好吗？",
                    private val model: String = "text-davinci-003",
                    private val max_tokens: Int = 2048)