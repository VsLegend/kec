// 板块加载Ajax
function module_ajax(size, num, status) {
  var moduleDTO = {
    // 选择状态 0上线 1下线
    // 选择类型 0校园服务 1学习交流  2娱乐交友  3其它板块
    page: {
      pageSize: size,
      pageNum: num
    },
    status: status
  };
  var list = "";
  // console.log(user.studentno);
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/module/getModuleList",
    data: JSON.stringify(moduleDTO),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        list = data.data;
        //板块管理页面，加载各个板块
        load_module_table(data.data);
        load_module_for_post(data.data);
      } else {
        alert(data.data);
      }
    },
    error: function (e) {
      alert("服务器异常，注册失败");
    }
  });
  // console.log(list);
  return list;
}

//帖子管理中的板块展示
function load_module_for_post(data) {
  var list = data.list;
  for (var i = 0; i < list.length; i++) {
    if (list[i].type === '0') {
      $('#campus-service').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          //拼接URL，跳转到板块详情
          + "<a href='/backstage/controlPost?id=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    } else if (list[i].type === '1') {
      $('#study').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          + "<a href='/backstage/controlPost?id=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    } else if (list[i].type === '2') {
      $('#game').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          + "<a href='/backstage/controlPost?id=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    } else if (list[i].type === '3') {
      $('#others').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          + "<a href='/backstage/controlPost?id=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    }
  }


}

//获取当前选中板块的详情，以及板块下的帖子
function getPostDetail() {
  var id = getUrlParam("id");
  var post_list = null;
  $.ajaxSettings.async = false;
  $.get("/module/getModuleDetail/" + id, function (data, status) {
    if (status === 'success') {
      //展示板块信息
      var detail =  data.data;
      // console.log($('#module-card').find("b").text());
      //设置卡片下的元素内容
      $('#module-card').children("div.card-image").find("b").text(detail.name);
      $('#module-card').children("div.card-image").find("a").attr("id", detail.userId);
      $('#module-card').children("div.card-image").find("a").append(detail.userName);
      $('#module-card').children("div.card-image").find("p").append(detail.summary);

      //帖子展示
      post_list = get_post_ajax(1, id);
      $("#admin-add-post").click(function () {
        // console.log("===================" + id);
        //在当前板块添加主贴
        var h = "/backstage/addNewPost?id=" + id;
        window.location.href = h;
      })
    } else {
      alert(data.message);
    }
  });
  $.ajaxSettings.async = true;
  return post_list;
}

//帖子查询
function get_post_ajax(num,id) {
  var post_list = null;
  var searchRequestDTO = {
    pageSize: 20,
    pageNum: num,
    moduleId: id
  };
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/post/getPostList",
    data: JSON.stringify(searchRequestDTO),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        //展示
        post_list = data.data;
        load_post_table(data);
        //点击查看用户详情
        $("[href='#user-modal-detail']").click(function () {
          //用户详情
          var detail = get_user_detail_ajax(this.id);
          $('#user-modal-detail').find("h4").text(detail.name);
        });
      } else {
        alert(data.data.code);
      }
    },
    error: function (data) {
      console.log(data.responseJSON);
      alert("查询异常");
    }
  });
  return post_list;
}


//获取某个板块的详细信息
function get_module_detail_ajax(moduleId) {
  var module = null;
  $.ajaxSettings.async = false;
  $.get("/module/getModuleDetail/" + moduleId, function (data, status) {
    if (status === 'success') {
      // console.log(data.data);
      module = data.data;
    } else {
      showMessage(data.message);
    }
  });
  return module;
}

//【主贴】管理中将值赋给table
function load_post_table(data) {
  var list = data.data.list;
  $("#show-post tbody").empty();
  for (var i = 0; i < list.length; i++) {
    //赋值
    $("#show-post tbody").append("<tr>" +
        "<td width='30px'><i class='material-icons red-text'>" + post_type(list[i].type) + "</i></td>" +
        "<td width='100px'>"+ "评论数" +"</td>" +
        //点击查看主贴内容
        "<td><a href='/backstage/postDetail?id=" + list[i].postId +"' "
        + "class='blue-text text-lighten-3'>"+ list[i].title+"</a></td>" +
        "<td width='200px'>"
        //点击查看用户信息
        + "<a href='#user-modal-detail' id='" + list[i].userId + "' "
        + "class='modal-trigger blue-text text-lighten-3'>"+ list[i].userName +"</a></td>" +
        "<td width='200px'><a href='#!' onclick='change_post_status_ajax(\"" + list[i].postId + "\")'> "+ (list[i].status === '0'? '删除' : '恢复') +"</a></td>" +
        // "<td width='100px'>"+ "恢复" +"</td>" +
        "</tr>");
  }
  if (list.length === 0) {
    $("#show-post tbody")
      .append('<tr><td colspan="5" class="center"><br><p class="red-text">暂无内容</p></td></tr>');
  }
}

//【板块】管理中将值赋给table
function load_module_table(data) {
  // console.log(list[0].status === '0'? '上线' : '下线');
  // 选择状态 0上线 1下线
  // 选择类型 0校园服务 1学习交流  2娱乐交友  3其它板块
  var list = data.list;
  $("#module_table tbody").empty();
  for (var i = 0; i < list.length; i++) {
    var moduleId = list[i].id;
    $("#module_table tbody").append("<tr>" +
        //板块名称
        "<td id='" + list[i].id +"'>"+ list[i].name+"</td>" +
        //版主
        "<td><a href='#user-modal-in-module' class='modal-trigger blue-text text-lighten-3' id=\'" +
        list[i].userId + "\'>"+ list[i].userName +"</a></td>" +
        "<td>"+ module_type(list[i].type) +"</td>" +
        "<td>"+ (list[i].status === '0'? '上线' : '下线') +"</td>" +
        "<td>"+ list[i].createTime+"</td>" +
        "<td>"
        + "<a href='/backstage/addNewModule?moduleId=" + list[i].id + "' class='black-text'>编辑</a> &nbsp;&nbsp;"
        + "<a href='#!' onclick='change_module_status_ajax(\""
        + moduleId + "\")' >"+ (list[i].status === '0'? '删除' : '恢复') +"</a>"
        + "</td>" +
        "</tr>");
  }
  if (list.length === 0) {
    $("#module_table tbody").append('<tr><td colspan="7"><br><p class="red-text">暂无内容</p></td></tr>')
  }

  $("[href='#user-modal-in-module']").click(function () {
    var detail = get_user_detail_ajax(this.id);
    $('#user-modal-in-module').find("h4").text(detail.name);
  });
}

//获取用户详情
function get_user_detail_ajax(userId) {
  var detail = null;
  $.ajaxSettings.async = false;
  $.get("/userControl/getUserDetail/" + userId ,function (data) {
    if (data.code === 1000) {
      // console.log(data.data);
      detail = data.data;
    } else {
      alert(data.data);
    }
  });
  return detail;
}

//修改板块状态
function change_post_status_ajax(postId) {
  $.ajax({
    type: 'GET',
    contentType: "application/json",
    url: "/post/updatePost/" + postId,
    data: JSON.stringify(),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        M.toast({html: '修改成功!'});
        setInterval("window.location.reload()", 1500);
      } else {
        alert(data.data);
      }
    },
    error: function (data) {
      // console.log(data.responseJSON);
      alert("服务器异常，修改失败。");
    }
  });
}

//修改板块状态
function change_module_status_ajax(moduleId) {
  $.ajax({
    type: 'GET',
    contentType: "application/json",
    url: "/module/deleteModule/" + moduleId,
    data: JSON.stringify(),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        M.toast({html: '修改成功!'});
        location.reload();
      } else {
        alert(data.data);
      }
    },
    error: function (data) {
      // console.log(data.responseJSON);
      alert("服务器异常，修改失败。");
    }
  });
}

// 新增板块Ajax
function new_module_ajax() {
  var moduleDTO = {
    name: $('#module_name').val(),
    summary: $('#module_summary').val(),
    type: $('#module_type').val(),
    userId: choose_user.id
  };
  // console.log(moduleDTO.name);
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/module/insertModule",
    data: JSON.stringify(moduleDTO),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        M.toast({html: '新增成功'});
        setInterval("location.reload()", 2000);
      } else {
        alert(data.data);
      }
    },
    error: function (data) {
      console.log(data.responseJSON);
      alert("服务器异常，板块添加失败。");
    }
  });
}

// 更新板块Ajax
function update_module_ajax() {
  var moduleDTO = {
    id: getUrlParam("moduleId"),
    name: $('#module_name').val(),
    summary: $('#module_summary').val(),
    type: $('#module_type').val(),
    userId: choose_user.id
  };
  // console.log(moduleDTO.name);
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/module/updateModule",
    data: JSON.stringify(moduleDTO),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        M.toast({html: '修改成功'});
        setInterval("window.location.href = '/backstage/moduleManeger'", 2000);
      } else {
        alert(data.data);
      }
    },
    error: function (data) {
      console.log(data.responseJSON);
      alert("服务器异常，板块添加失败。");
    }
  });
}

//返回主贴详情页
function get_back() {
  // console.log("===================" + id);
  //在当前板块添加主贴
  var h = "/backstage/controlPost?id=" + getUrlParam("id");
  window.location.href = h;
}

// 注册信息方法
function showMessage(message) {
  M.toast({html: message});
}
