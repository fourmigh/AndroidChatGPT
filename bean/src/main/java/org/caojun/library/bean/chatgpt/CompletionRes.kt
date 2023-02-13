package org.caojun.library.bean.chatgpt

class CompletionRes {
    /**
     * {
     *      "id": "cmpl-6Zwmtdz6zmubpieGuVMMz8yvw5ygv",
     *      "object": "text_completion",
     *      "created": 1674025479,
     *      "model": "text-davinci-003",
     *      "choices": [
     *      {
     *          "text": "\n\n《三体》的内容主要以科幻、灾难为主，剧情构思缜密、文笔细腻，涉及到较复杂的物理学、宇宙论外的知识，因此不建议小于18岁的孩子阅读这部作品。若孩子的阅读水平较高，可考虑在15岁以上的年龄看《三体》。",
     *          "index": 0,
     *          "logprobs": null,
     *          "finish_reason": "stop"
     *      }],
     *      "usage": {
     *          "prompt_tokens": 29,
     *          "completion_tokens": 197,
     *          "total_tokens": 226
     *      }
     * }
     */

    /**
     * "error": {
     *      "message": "Incorrect API key provided: sk-PhwL5***************************************fbZM. You can find your API key at https://beta.openai.com.",
     *      "type": "invalid_request_error",
     *      "param": null,
     *      "code": "invalid_api_key"
     * }
     */

    val id = ""
    val created = 0L
    val model = ""
    val choices = ArrayList<Choice>()
    val usage = Usage()

    var error: Error? = null

    var question = ""
    var askTime = 0L
    var answerTime = 0L
}