$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

function send_letter() {
	//先关闭发送弹出框，再向服务器发送数据，服务器返回结果之后提示框做提示

	//关闭发送弹出框
	$("#sendModal").modal("hide");

	//从letter.html页面获取接收人和私信内容
	var toName = $("#recipient-name").val();
	var content = $("#message-text").val();
	//异步地发一个post请求
	$.post(
		//三个参数用,隔开
		//访问路径
		CONTEXT_PATH + "/letter/send",
		//声明要传的数据的参数
		{"toName":toName,"content":content},
		//处理服务端返回的结果
		//接收一个从服务器传过来的参数data,data是一个普通的字符串且满足JSON格式
		function (data){
			//将data转换为js对象
			data = $.parseJSON(data);
			//对服务器返回的结果进行判断，等于0表示成功了
			if (data.code == 0) {
				//给一个提示到提示框
				$("#hintBody").text("发送成功！");
			} else {
				$("#hintBody").text(data.msg);
			}

			//提示框显示2秒后关闭
			$("#hintModal").modal("show");
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				location.reload();
			}, 2000);
		}
	);

}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}