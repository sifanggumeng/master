$(function(){
    $("#topBtn").click(setTop);
    $("#wonderfulBtn").click(setWonderful);
    $("#deleteBtn").click(setDelete);
});

function like(btn, entityType, entityId, entityUserId, postId) {
    $.post(
        CONTEXT_PATH + "/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $(btn).children("i").text(data.likeCount);
                $(btn).children("b").text(data.likeStatus==1?'已赞':"赞");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 置顶
function setTop() {
    $.post(
        CONTEXT_PATH + "/discuss/top",  //设置路径
        {"id":$("#postId").val()},  //将id值传给服务器
        function(data) {       //处理服务器响应的结果
            data = $.parseJSON(data);
            if(data.code == 0) {
                $("#topBtn").attr("disabled", "disabled");  //成功了将topBtn按钮的disabled属性设置为disabled
            } else {
                alert(data.msg);    //失败了进行提示
            }
        }
    );
}

// 加精
function setWonderful() {
    $.post(
        CONTEXT_PATH + "/discuss/wonderful",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                $("#wonderfulBtn").attr("disabled", "disabled");
            } else {
                alert(data.msg);
            }
        }
    );
}

// 删除
function setDelete() {
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id":$("#postId").val()},
        function(data) {
            data = $.parseJSON(data);
            if(data.code == 0) {
                location.href = CONTEXT_PATH + "/index";
            } else {
                alert(data.msg);
            }
        }
    );
}