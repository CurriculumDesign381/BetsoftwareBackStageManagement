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
    	swal({
            confirmButtonColor: "#DD6B55",
            type:"info",
            title: "查看学生信息",
            text: "电话:"+row.cellphone+"\n"+"域名:"+row.domain+"\n"+"注册账号:"+row.account+"\n"+"下注账号:"+row.betaccount,
        });
    },

    'click .upData': function (e, value, row, index) {
    	// 将表格中的数据填充进模态框
		$('#cellphone1').val(row.cellphone);
		$("#domain1").val(row.domain);
		$("#account1").val(row.account);
		$("#betaccount1").val(row.betaccount);

        $('#modify').on('click',function(){ // 修改
		        $('#tableTobeFilled').bootstrapTable('updateRow', {
	            index: index,
	            row: {
					cellphone: $('#cellphone1').val(),
					domain: $("#domain1").val(),
					account: $("#account1").val(),
					betaccount: $("#betaccount1").val(),
	            }
				});

				// $.ajax({
				// 	url: "control/DeleteRole",//servlet文件的名称
				// 	type: "POST",
				// 	dataType: "json",
				// 	data: {
				// 		"cellphone": row.cellphone,
				// 		"domain": row.domain,
				// 		"account": row.account,
				// 		"betaccount": row.betaccount,
				// 	},
				// 	success: function (data1) {
				// 		if (data1 == 1) {
				// 			alert("删除成功!");
				// 			$('#roleTable').bootstrapTable('refresh', null);
				// 		}
				// 		else if (data1 == 0) {
				// 			alert("删除失败!");
				// 			$('#roleTable').bootstrapTable('refresh', null);
				// 		}
				// 	},
				// 	error: function (msg) {//ajax请求失败后触发的方法
				// 		alert("请求失败");
				// 		console.log(msg)
				// 	}
				// });

	            $('#tableTobeFilled').bootstrapTable('load',user);
	            index = null;
		})
	    },
	    'click .sinDel': function (e, value, row, index) {
	    	var r=confirm("是否删除")
			if (r==true){
				// $.ajax({
				// 	url: "control/DeleteRole",//servlet文件的名称
				// 	type: "POST",
				// 	dataType: "json",
				// 	data: {
				// 		"cellphone": row.cellphone,
				// 		"domain": row.domain,
				// 		"account": row.account,
				// 		"betaccount": row.betaccount,
				// 	},
				// 	//data:{"questionnaireId":"<%=questionnaireId%>"},
				// 	success: function (data1) {
				// 		if (data1 == 1) {
				// 			alert("删除成功!");
				// 			$('#roleTable').bootstrapTable('refresh', null);
				// 		}
				// 		else if (data1 == 0) {
				// 			alert("删除失败!");
				// 			$('#roleTable').bootstrapTable('refresh', null);
				// 		}
				// 	},
				// 	error: function (msg) {//ajax请求失败后触发的方法
				// 		alert("请求失败");
				// 		console.log(msg)
				// 	}
				// });

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
        field : 'cellphone',
        title : '电话',
        align: 'center',
        valign: 'middle',
    }, {
        field : 'domain',
        title : '域名',
        align: 'center',
        valign: 'middle'
    }, {
        field : 'account',
        title : '软件账号',
        align: 'center',
        valign: 'middle'
    }, {
		field: 'betaccount',
        title : '下注账号',
        align: 'center',
        valign: 'middle',
        }, {
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

