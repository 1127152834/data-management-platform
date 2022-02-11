$(function () {
    loadLinkList() ;
})


/**
 * 加载链接
 */
function loadLinkList() {
    $.ajax({
        url: HOST + COMMON.PUB_PREFIX + "/link" + "/view_link",
        type:"get",
        dataType:"json",
        async:false,
        success:function (res) {
            console.log(res);
            switch (res.status) {
                case -1:
                    systemAlert("red","未知错误，"+res.msg);
                    break;
                case 0:
                    for (var i = 0; i < res.data.length; i++) {
                        switch (res.data[i].linkType) {
                            case 1:
                                $("#toolLink").append("<div class=\"col-md-2\" align=\"center\">\n" +
                                    "                                    <a target='_blank' href=\""+res.data[i].linkAddress+"\">\n" +
                                    "                                    <img src=\""+res.data[i].linkImage+"\" class=\"website-link\">" +
                                    "                                    </a>\n" +
                                    "                                    <p>"+res.data[i].linkName+"</p>\n" +
                                    "                                    </div>\n");
                            break;
                            case 2:
                                $("#articleLink").append("<div class=\"col-md-2\" align=\"center\">\n" +
                                    "                                    <a target='_blank' href=\""+res.data[i].linkAddress+"\">\n" +
                                    "                                    <img src=\""+res.data[i].linkImage+"\" class=\"website-link\">" +
                                    "                                    </a>\n" +
                                    "                                    <p>"+res.data[i].linkName+"</p>\n" +
                                    "                                    </div>\n");
                                break;
                            case 3:
                                $("#otherLink").append("<div class=\"col-md-2\" align=\"center\">\n" +
                                    "                                    <a target='_blank' href=\""+res.data[i].linkAddress+"\">\n" +
                                    "                                    <img src=\""+res.data[i].linkImage+"\" class=\"website-link\">" +
                                    "                                    </a>\n" +
                                    "                                    <p>"+res.data[i].linkName+"</p>\n" +
                                    "                                    </div>\n");
                                break;
                        }

                    }
                    break;
                case 100015:
                    if(digest==null){
                        systemAlert("red","您的操作太快，请稍后再试哦~");
                    }
                    break;
                case 100016:
                    if(digest==null){
                        systemAlert("red","由于你的请求过快，被视为恶意请求，系统已禁用你的请求，2小时后失效。<br>为营造良好上网氛围，请不要进行恶意操作！");
                    }
                    break;
                case 100017:
                    if(digest==null){
                        systemAlert("red","你的ip正处于禁用期间，暂无法使用该系统，请解禁后再试（禁用时间2小时）<br>为营造良好上网氛围，请不要进行恶意操作！");
                    }
                    break;
            }
        },
        error:function (res) {
            console.log(res);
        }
    })
}
