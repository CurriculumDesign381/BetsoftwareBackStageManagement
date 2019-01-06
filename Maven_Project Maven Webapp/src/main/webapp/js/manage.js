var count = 1;

//checkbox 全选/取消全选
var isCheckAll = false;
function swapCheck() {
	if (isCheckAll) {
		$("input[type='checkbox']").each(function() {
			this.checked = false;
		});
		isCheckAll = false;
	} else {
		$("input[type='checkbox']").each(function() {
			this.checked = true;
		});
		isCheckAll = true;
	}
}

/**添加数据的确认*/
$('#add').click(function(){
	var cellphone = $('#cellphone').val();
	var account = $("#account").val();     // 获取前台的专业
	var username = $("#username").val();         // 获取前台的年级
	var password = $("#password").val();  
		
	var result = "{" + "\"" + "cellphone" + "\"" + ":" + "\"" + cellphone + "\"" + "," + "\"" + "account" 
	+ "\"" + ":" + "\"" + account + "\"" + "," + "\"" 
	+ "username" + "\"" + ":" + "\"" + username + "\"" +"}";

	$.ajax({
		url: "/Maven_Project/user/addAccount",//servlet文件的名称
		type: "GET",
		dataType: "json",
		data: {
			"account": account,
			"cellphone": cellphone,
			"username": username,
			"password": password,
		},
		success: function (data1) {
			if (data1 == 1) {
				alert("新增成功!");
				//字符串转json，并添加在数组的第一个
				user.unshift(JSON.parse(result));
				$('#tableTobeFilled').bootstrapTable('load',user);
				$('#tableTobeFilled').bootstrapTable('refresh', null);
				// fill(num,name,institute,specialty,grade,className,age);
				// primaryAssets();
			}
			else if (data1 == 0) {
				alert("新增失败!");
				$('#tableTobeFilled').bootstrapTable('refresh', null);
			}
		},
		error: function (msg) {//ajax请求失败后触发的方法
			alert("请求失败");
			console.log(msg)
		}
	});

});

/** 检测是否已经填写*/
function whetherFilled(){
	var cellphone = $('#cellphone').val();
	var domain = $("#domain").val();               // 获取前台的姓名
	var account = $("#account").val();     // 获取前台的专业
	var betaccount = $("#betaccount").val();             // 获取前台的年级


	if (cellphone == ""){
		swal({
			confirmButtonColor: "#DD6B55",
			type:"info",
			title: "错误",
			text: "请填写手机号",
		});
		return false;
	} else if (domain == ""){
		swal({
			confirmButtonColor: "#DD6B55",
			type:"info",
			title: "错误",
			text: "请填写域名",
		});
		return false;
	} else if (account == ""){
		swal({
			confirmButtonColor: "#DD6B55",
			type:"info",
			title: "错误",
			text: "请填写软件账号",
		});
		return false;
	} else if (betaccount == ""){
		swal({
			confirmButtonColor: "#DD6B55",
			type:"info",
			title: "错误",
			text: "请填写注册账号",
		});
		return false;
	}else{
		return true;
	}
}

/** 插入表格*/
function fill(num,cellphone,username,account){
	var temp = "<td>"+count+"</td>"+"<td>"+account+"</td>"+"<td>"+username+"</td>"+"<td>"+cellphone+"</td>";
	var content = "<tr><th><input type=\"checkbox\"></th>"+temp+"</tr>";
	count++;
	$('#tableTobeFilled').children().children().last().after(content);
}

var num = 1;  //计数器
function primaryAssets(){
	var data = {   //要插入的数据，这里要和table列名一致
			序号 : num,
			电话 : $('#cellphone').val(),  //获取模态框input的值
			用户名 : $('#username').val(),
			软件账号 : $('#account').val(),
	}
	$('#tableTobeFilled').bootstrapTable('insertRow',{
		index : num,
		row : data
	});
	num++;
}

