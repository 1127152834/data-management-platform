(function(){
    // 验证加载用户信息是否成功，成功才进行下一步操作
    if(loadUserInfo()){

    }
    var num=0;
    var timer=null;
    //点击图片上传按扭
    $(".imgBut").click(function(){
        $(".upload").slideToggle(); //自动收缩和展开
    });
    //找到要拖进去的目标元素
    var oDiv=$(".upload").get(0);
    var op=$(".upload p.tis");

    //当进入
    oDiv.ondragenter=function(){
        op.html("可以释放元素");
    }
    //离开
    oDiv.ondragleave=function(){
        op.html("请将图片拖拽到此区域");
    };
    //在内部移动
    oDiv.ondragover=function(e){
        e.preventDefault();
        e.stopPropagation();
    }
    //释放
    oDiv.ondrop=function(e){
        clearTimeout(timer)
        e.stopPropagation();
        e.preventDefault();
        //获取拖过来的文件
        var fs=e.dataTransfer.files;
        var len=fs.length; //获取文件个数
        for(var i=0;i<len;i++){
            var _type=fs[i].type;
            if(_type.indexOf("image")!=-1){//判断他是不是图片文件
                num++;
                if(num<=2){
                    var fd=new FileReader();
                    fd.readAsDataURL(fs[i]);
                    fd.onload=function(){
                        var oImg=$("<img src=''   " +
                            "" +
                            "" +
                            "" +
                            "" +
                            "" +
                            " width='290' height='290' />");
                        oImg.attr("src",this.result);
                        $(".upload").append(oImg);
                        op.html("");
                    }
                }else{
                    op.html("最多只能上传2张哦");
                    timer=setTimeout(function(){
                        op.html('')
                    },1000)
                }
            }else{
                systemAlert("red","请，上传图片文件！！");
            }
        }
    }

})()

function clearImage() {
    location.reload();
}

/**
 * 将文件上传至服务器
 * @param formData
 */
function uploadImage() {
    if($.cookie(USER_DIGEST_COOKIE) == null){
        systemConfirm("您未登录，是否前往登录？",function () {
            location.href = "login.html?client_url="+urlEncode(location.href);
        })
    }else{
        if (typeof($('.upload img')[0]) == "undefined"){
            systemAlert("red","请传入第一张图片");
            e.stopPropagation();
            return false;
        }

        if (typeof($('.upload img')[1]) == "undefined"){
            systemAlert("red","请传入第二张图片");
            return false;
        }

        const resource = $('.upload img')[0].src;
        const target = $('.upload img')[1].src;

        const data = {
            resource: resource,
            target: target
        }
        $(".similarityScore").text("计算中....");
        $.ajax({
            type:"post",
            async:true,
            data: JSON.stringify(data),
            headers:{
                token:$.cookie(USER_DIGEST_COOKIE),
                "Content-Type": "application/json;charset=utf-8"
            },
            url: HOST + COMMON.PUB_PREFIX + API.FACE + "/oneToOne" +
                "",
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            success:function(res){
                console.log(res);
                res.data
                $(".similarityScore").text(res.data);
                switch (res.status) {
                    case -1:
                        systemAlert("red","系统出错！上传失败，"+res.msg);
                        break;
                    case 0:
                        break;
                    case 100015:
                        systemAlert("red","您的操作太快，请稍后再试哦~");
                        break;
                    case 100016:
                        systemAlert("red","由于你的请求过快，被视为恶意请求，系统已禁用你的请求，2小时后失效。<br>为营造良好上网氛围，请不要进行恶意操作！");
                        break;
                    case 100017:
                        systemAlert("red","你的ip正处于禁用期间，暂无法使用该系统，请解禁后再试（禁用时间2小时）<br>为营造良好上网氛围，请不要进行恶意操作！");
                        break;
                    case 10006:
                        systemAlert("red","您暂无权限上传资源文件！如需开通权限请联系管理员。");
                        break;
                    case 10009:
                        systemAlert("red","文件格式错误！");
                        break;
                    case 100011:
                        systemAlert("red","资源文件太大");
                        break;
                }
            },
            error:function(res){
                systemAlert("red","出错啦，code："+res.status);
            }
        });
    }
}