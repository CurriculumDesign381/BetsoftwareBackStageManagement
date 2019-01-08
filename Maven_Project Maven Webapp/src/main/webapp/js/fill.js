var user = [
	// { "cellphone": "15102346379", "domain": "李建飞", "account": "116888","betaccount":"下注账号"}
	{ "betaccount": "a10126052", "domain": "https://www.hgxin99.com", "cellphone": "17723617215", "account": "888", "username": "FAYMONG" },
	];

function operateFormatter(value, row, index) {
	return [
		'<button type="button" class="watch btn btn-primary  btn-sm" style="margin-right:5px;">查看</button>',
		'<button type="button" class="upData btn btn-primary  btn-sm" data-toggle="modal" data-target="#myModal1">修改</button>',
		'<button type="button" class="sinDel btn btn-primary  btn-sm" style="margin-left:5px;">删除</button>'
		].join('');
}

window.operateEvents = {
		'click .watch': function (e, value, row, index) {
			$.ajax({
				url: '/Maven_Project/user/getAllBetUser',//servlet文件的名称
				type: "GET",
				dataType: "json",
				data: {
					"account": row.account,
					"username": row.username,
					"cellphone": row.cellphone,
//					"password": row.password,
				},
				success: function (data1) {
					var json = eval(data1); //数组        
					var domain = "\n";
					var betaccount = "\n";
					var betamount = "\n";
					$.each(json, function (index, item) {  
						//循环获取数据    
//						domain += json[index].domain + " \n";  
//						betaccount += json[index].betaccount+"\t"+json[index].betamount+"\n";  
						var temp = "<tr><td>"+json[index].domain+"</td><td>"+json[index].betaccount+"</td><td>"
								+json[index].betamount+"</td></tr>";
						$("#tableble").append(temp);
//						document.getElementById("tableble").appendChild(temp); 
					});  
					$('#watchSth').modal('toggle');

//				swal({
//				confirmButtonColor: "#DD6B55",
//				type:"info",
//				title: "查看用户信息",
//				text: "域名:"+domain+"\n"+"下注账号以及下注金额: "+betaccount,
//				});
			},
			error: function (msg) {//ajax请求失败后触发的方法
				alert("请求失败");
				console.log(msg)
			}
		});
},

'click .upData': function (e, value, row, index) {
	// 将表格中的数据填充进模态框
	$('#account1').val(row.account);
	$("#username1").val(row.username);
	$("#cellphone1").val(row.cellphone);
	$("#password1").val(row.password);

	$('#modify').on('click',function(){ // 修改
		$.ajax({
			url: "/Maven_Project/user/updateUser",//servlet文件的名称
			type: "GET",
			dataType: "json",
			data: {
				"account": $('#account1').val(),
				"cellphone": $("#cellphone1").val(),
				"username": $("#username1").val(),
				"password": $("#password1").val(),
				"oldAccount" : row.account,
			},
			success: function (data1) {
				if (data1 == 1) {
					alert("修改成功!");
					$('#tableTobeFilled').bootstrapTable('updateRow', {
						index: index,
						row: {
							account: $('#account1').val(),
							username: $('#username1').val(),
							cellphone: $('#cellphone1').val(),
							password: $("#password1").val(),
						}
					});
					$('#roleTable').bootstrapTable('refresh', null);
					index = null;
				}
				else if (data1 == 0) {
					alert("修改失败!");
					$('#roleTable').bootstrapTable('refresh', null);
				}
			},
			error: function (msg) {//ajax请求失败后触发的方法
				alert("请求失败");
				console.log(msg)
			}
		});
	})
},
'click .sinDel': function (e, value, row, index) {
	var r=confirm("是否删除")
	if (r==true){
		$.ajax({
			url: "/Maven_Project/user/deleteUser",//servlet文件的名称
			type: "GET",
			dataType: "json",
			data: {
				"cellphone": row.cellphone,
				"account": row.account,
				"username":row.username,
			},
			success: function (data1) {
				if (data1 == 1) {
					$('#roleTable').bootstrapTable('refresh', null);
					alert("删除成功!");	
				}
				else if (data1 == 0) {
					$('#roleTable').bootstrapTable('refresh', null);
					alert("删除失败!");
				}
			},
			error: function (msg) {//ajax请求失败后触发的方法
				alert("请求失败");
				console.log(msg)
			}
		});

		$('#tableTobeFilled').bootstrapTable('remove', {
			field: 'num',
			values: [row.num]
		});
	}
}
};

$('#tableTobeFilled').bootstrapTable({
	url: '/Maven_Project/user/getAllUser',
	dataType: "json",
	// 是否分页
	pagination: true,
	pageSize: 10,
	// 可供选择的分页行数
	pageList: [10,25,50,100,'All'],
	search: true,
	showRefresh: true,
	clickToSelect: false,
	toolbar : '#toolbar', //工具按钮用哪个容器
	// showFooter: true,  //表格脚部，用来计算统计总和
	// classes: "table-no-bordered",  //表格无边框
	striped : true, //是否显示行间隔色
	cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	// pagination : true, //是否显示分页（*）
	// sortable : false, //是否启用排序
	// sortOrder : "asc", //排序方式
	// queryParams : oTableInit.queryParams,//传递参数（*）
	// sidePagination : "client", //分页方式：client客户端分页，server服务端分页（*）
	// pageNumber : 1, //初始化加载第一页，默认第一页
	// pageSize : 10, //每页的记录行数（*）
	// pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
	// search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
	// strictSearch : true,
	// showColumns : false, //是否显示所有的列
	// showRefresh : false, //是否显示刷新按钮
	// minimumCountColumns : 2, //最少允许的列数
	// clickToSelect : true, //是否启用点击选中行
	// height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
	// uniqueId : "ID", //每一行的唯一标识，一般为主键列
	// showToggle : false, //是否显示详细视图和列表视图的切换按钮
	// cardView : false, //是否显示详细视图
	// detailView : false, //是否显示父子表
	columns:[{
		checkbox: true
	},{
		title: '序号',
		align: 'center',
		formatter: function(value,row,index){
			return index+1;
		}
	}, {
		field : 'account',
		title : '账号',
		align: 'center',
		valign: 'middle',
	}, {
		field : 'username',
		title : '用户名',
		align: 'center',
		valign: 'middle'
	}, {
		field : 'cellphone',
		title : '电话',
		align: 'center',
		valign: 'middle'
	},{
		field : 'password',
		title : '密码',
		align: 'center',
		valign: 'middle'
	},{
		field: 'operate',
		title: '操作',
		align: 'center',
		valign: 'middle',
		width : 200,
		events: operateEvents,
		formatter: operateFormatter
	}],
});

//复选框响应事件
$('#tableTobeFilled').on('check.bs.table uncheck.bs.table ' + 'check-all.bs.table uncheck-all.bs.table', function () {
	$('#delete').prop('disabled', !$('#tableTobeFilled').bootstrapTable('getSelections').length);
	selections = getIdSelections();
});

$('#delete').click(function () {
	var ids = getIdSelections();
	// console.log(ids);
	$('#tableTobeFilled').bootstrapTable('remove', {
		field: 'num',
		values: ids
	});
	$('#delete').prop('disabled', true);
});

$('#btn_delete').click(function () {
	var r=confirm("是否删除")
	if (r==true){
		var checkedAll = $('#tableTobeFilled').bootstrapTable('getSelections');
		for(var i =0; i < checkedAll.length;i++){
			var key = checkedAll[i];
			$('#tableTobeFilled').bootstrapTable('remove', {
				field: 'num',
				values: [key.num]
			});
		}
	}
});

function getIdSelections() {
	return $.map($('#tableTobeFilled').bootstrapTable('getSelections'), function (row) {
		return row.number
	});
}

