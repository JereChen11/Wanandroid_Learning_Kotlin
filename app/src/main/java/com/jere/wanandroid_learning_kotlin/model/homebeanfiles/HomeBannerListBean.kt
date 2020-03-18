package com.jere.wanandroid_learning_kotlin.model.homebeanfiles

class HomeBannerListBean {
    /**
     * data : [{"desc":"享学~","id":29,"imagePath":"https://wanandroid.com/blogimgs/99cdb2f0-5482-4ec0-81d6-2d8e00c34e0b.png","isVisible":1,"order":0,"title":"你的APP，性能优化了吗？","type":0,"url":"https://mp.weixin.qq.com/s/W8wq0QgjvEHLIC5vaGIEbg"},{"desc":"","id":6,"imagePath":"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png","isVisible":1,"order":1,"title":"我们新增了一个常用导航Tab~","type":1,"url":"https://www.wanandroid.com/navi"},{"desc":"一起来做个App吧","id":10,"imagePath":"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png","isVisible":1,"order":1,"title":"一起来做个App吧","type":1,"url":"https://www.wanandroid.com/blog/show/2"},{"desc":"","id":20,"imagePath":"https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png","isVisible":1,"order":2,"title":"flutter 中文社区 ","type":1,"url":"https://flutter.cn/"}]
     * errorCode : 0
     * errorMsg :
     */
    var errorCode = 0
    var errorMsg: String? = null
    var data: ArrayList<DataBean>? =
        null

    class DataBean {
        /**
         * desc : 享学~
         * id : 29
         * imagePath : https://wanandroid.com/blogimgs/99cdb2f0-5482-4ec0-81d6-2d8e00c34e0b.png
         * isVisible : 1
         * order : 0
         * title : 你的APP，性能优化了吗？
         * type : 0
         * url : https://mp.weixin.qq.com/s/W8wq0QgjvEHLIC5vaGIEbg
         */
        var desc: String? = null
        var id = 0
        var imagePath: String? = null
        var isVisible = 0
        var order = 0
        var title: String? = null
        var type = 0
        var url: String? = null

    }
}