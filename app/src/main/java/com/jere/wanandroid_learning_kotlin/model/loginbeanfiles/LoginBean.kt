package com.jere.wanandroid_learning_kotlin.model.loginbeanfiles

class LoginBean {
    /**
     * data : {"admin":false,"chapterTops":[],"collectIds":[12554,13162,9359,13303,13300,13161,12871,13345,13159],"email":"","icon":"","id":47014,"nickname":"Jere_Chen","password":"","publicName":"Jere_Chen","token":"","type":0,"username":"Jere_Chen"}
     * errorCode : 0
     * errorMsg :
     */
    private var data: DataBean? = null
    private var errorCode = 0
    private var errorMsg: String? = null

    fun getData(): DataBean? {
        return data
    }

    fun setData(data: DataBean?) {
        this.data = data
    }

    fun getErrorCode(): Int {
        return errorCode
    }

    fun setErrorCode(errorCode: Int) {
        this.errorCode = errorCode
    }

    fun getErrorMsg(): String? {
        return errorMsg
    }

    fun setErrorMsg(errorMsg: String?) {
        this.errorMsg = errorMsg
    }

    class DataBean {
        /**
         * admin : false
         * chapterTops : []
         * collectIds : [12554,13162,9359,13303,13300,13161,12871,13345,13159]
         * email :
         * icon :
         * id : 47014
         * nickname : Jere_Chen
         * password :
         * publicName : Jere_Chen
         * token :
         * type : 0
         * username : Jere_Chen
         */
        var isAdmin = false
        var email: String? = null
        var icon: String? = null
        var id = 0
        var nickname: String? = null
        var password: String? = null
        var publicName: String? = null
        var token: String? = null
        var type = 0
        var username: String? = null
        var chapterTops: List<*>? = null
        var collectIds: List<Int>? = null

    }

}