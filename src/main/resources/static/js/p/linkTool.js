$(function () {
    init();
})

var tempValue = {
    linkList:[],
    editLink:{},    // 修改用户信息当前用户
    saveLink:false  // true，保存；false，新增
}

/**
 * 初始化函数
 */
function init() {
    // 验证加载用户信息是否成功，成功才进行下一步操作
    if(loadUserInfo()){
        getUserPermissionList();
        renderPage();   //根据权限渲染页面
    }
    // 初始化Table
    var oTable = new TableInit();
    oTable.Init();
}

/**
 * 表格初始化对象
 * @returns {Object}
 * @constructor
 */
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#linkTable').bootstrapTable({
            url: HOST + COMMON.PUB_PREFIX + "/link" + "/get_linklist",         //请求后台的URL（*）
            ajaxOptions:{
                headers:{
                    token:$.cookie(USER_DIGEST_COOKIE)
                }
            },
            method: 'get',                      //请求方式（*）
            undefinedText: "空",//当数据为 undefined 时显示的字符
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5,10, 25, 50, 100],        //可供选择的每页的行数（*）
            paginationPreText: '‹',//指定分页条中上一页按钮的图标或文字,这里是<
            paginationNextText: '›',//指定分页条中下一页按钮的图标或文字,这里是>
            search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: false,                //是否启用点击选中行
            height: 700,                        //行高
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            contentType: 'application/json',
            dataType: 'json',
            refresh:true,
            responseHandler: function (res) {
                console.log(res);
                switch (res.status) {
                    case -1:
                        systemAlert("red","获取列表错误，"+res.msg);
                        break;
                    case 0:
                        tempValue.linkList = res.data.rows;
                        return {
                            "total": res.data.total,//总页数
                            "rows": res.data.rows   //数据
                        }
                    case 10006:
                        systemAlert("red","您暂无查询用户的权限！");
                        break;
                }
            },
            columns: [{
                field: 'linkName',
                title: '链接名称'
            }, {
                field: 'linkAddress',
                title: '链接地址'
            }, {
                field: 'linkType',
                title: '链接类型',
                formatter:function (value) {
                    if (value == 1) {
                        return "工具网站";
                    }else if (value == 2){
                        return "文章链接"
                    }else if (value == 3){
                        return "其他链接"
                    }
                }
            },{
                field:'linkImage',
                title:'图片',
            },{
                field: 'id',
                title: '操作',
                formatter: function(value, row, index) {
                    return [
                        '<div style="display: flex;justify-content: space-around"><button class="btn active btn-sm btn-warning" onclick="editLink(\''+value+'\','+index+')">修改</button>' +
                        '<button class="btn active btn-sm btn-danger" onclick="deleteLink(\''+value+'\')">删除</button></div>'
                    ].join('');
                }
            } ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            page_size: params.limit,   //页面大小
            page: params.offset,  //页码
        };
        return temp;
    };
    return oTableInit;
};

/**
 * 删除用户
 * @param id
 */
function deleteLink(id) {
    systemConfirm("确定删除该链接吗？", function () {
        $.ajax({
            url: HOST + COMMON.PUB_PREFIX + "/link" + "/del_link",
            type: "delete",
            data: {
                id: id
            },
            dataType: "json",
            success: function (res) {
                console.log(res);
                switch (res.status) {
                    case 0:
                        systemAlert("green", "删除成功");
                        $('#linkTable').bootstrapTable("refresh");
                        break;
                    case -1:
                        systemAlert("red", "未知错误，" + res.msg);
                        break;
                    default:
                        systemAlert("red", res.msg);
                }
            },
            error: function (res) {
                systemAlert("red", "出错了，code：" + res.status);
            }
        })
    })
}


    /**
     * 修改链接
     * @param id
     */
    function editLink(id, index) {
        var link = tempValue.linkList[index];
        tempValue.editLink = link;
        $("#linkForm input[name='linkName']").val(link.linkName);
        $("#linkForm input[name='linkAddress']").val(link.linkAddress);
        if (link.isView == 1) {
            $("#yesRadio").attr("checked", true);
        } else {
            $("#noRadio").attr("checked", true);
        }
        if (link.linkType == 1) {
            $("#toolRadio").attr("checked", true);
        } else if (link.linkType == 2) {
            $("#articleRadio").attr("checked", true);
        } else if (link.linkType == 3) {
            $("#otherRadio").attr("checked", true);
        }
        $("#linkForm input[name='Image']").val(link.Image);
        tempValue.saveLink = true;
        $("#linkModal").modal("show");
    }

    $('#chooseImage').on('change', function () {//当chooseImage的值改变时，执行此函数
        var filePath = $(this).val(), //获取到input的value，里面是文件的路径
            fileFormat = filePath.substring(filePath.lastIndexOf(".")).toLowerCase(),
            src = window.URL.createObjectURL(this.files[0]); //转成可以在本地预览的格式
        // 检查是否是图片
        if (!fileFormat.match(/.png|.jpg|.jpeg/)) {
            error_prompt_alert('上传错误,文件格式必须为：png/jpg/jpeg');
            return;
        } else {
            $('#cropedBigImg').css('display', 'block');
            $('#cropedBigImg').attr('src', src);
        }
    });

    //添加链接
    function addLink() {
        $("#linkForm")[0].reset();
        tempValue.saveLink = false;
        $("#linkModal").modal("show");
    }


/**
 * 保存链接
 */
function saveLink() {
    var formData = new FormData();
    var linkFormData = $("#linkForm").serializeJson();
    if(linkFormData.linkName.trim()==""){
        systemAlert("red","链接名称不能为空");
        return;
    };
    if(linkFormData.linkAddress.trim()==""){
        systemAlert("red","链接地址不能为空");
        return;
    };
    if(null == $('#chooseImage').get(0).files[0]){
        systemAlert("red","请为链接选择一张封面图片");
        return;
    };
    linkFormData.id = tempValue.editLink.id;
    linkFormData.isView = parseInt(linkFormData.isView);
    linkFormData.linkType = parseInt(linkFormData.linkType);
    formData.append("Image",$('#chooseImage').get(0).files[0]);
    formData.append("id",linkFormData.id);
    formData.append("isView",linkFormData.isView);
    formData.append("linkType",linkFormData.linkType);
    formData.append("linkName",linkFormData.linkName);
    formData.append("linkAddress",linkFormData.linkAddress);
    if (tempValue.saveLink) {
        // 修改链接
        $.ajax({
            url:HOST + COMMON.PUB_PREFIX + "/link" + "/update_link",
            type:"post",
            headers:{
                token:$.cookie(USER_DIGEST_COOKIE),
            },
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            data:formData,
            success:function (res) {
                console.log(res);
                switch (res.status) {
                    case 0:
                        systemAlert("green","修改成功",function () {
                            $("#linkModal").modal("hide");
                            $('#linkTable').bootstrapTable("refresh");
                        });
                        break;
                    case -1:
                        systemAlert("red","未知错误，"+res.msg);
                        break;
                    default:
                        systemAlert("red",res.msg);
                }
            },
            error:function (res) {
                systemAlert("red","出错了，code："+res.status);
            }
        })
    } else {
        $.ajax({
            url:HOST + COMMON.PUB_PREFIX + "/link" + "/add_link",
            type:"post",
            headers:{
                token:$.cookie(USER_DIGEST_COOKIE),
            },
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            data:formData,
            success:function (res) {
                console.log(res);
                switch (res.status) {
                    case 0:
                        systemAlert("green","添加成功",function () {
                            $("#linkModal").modal("hide");
                            $('#linkTable').bootstrapTable("refresh");
                        });
                        break;
                    case -1:
                        systemAlert("red","未知错误，"+res.msg);
                        break;
                    default:
                        systemAlert("red",res.msg);
                }
            },
            error:function (res) {
                systemAlert("red","出错了，code："+res.status);
            }
        })
    }
}
