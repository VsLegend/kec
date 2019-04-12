$(document).ready(function () {
  $('#user_signup').submit(function (event) {
    event.preventDefault();
    if (check_info()
        && password_check($('#password').val(), $('#password2').val())) {
      sign_submit();
    }
  });
});


//获取消息推送的内容
function message_push_ajax(size, num) {
  var message = null;
  var page ={
    pageNum: num,
    pageSize: size
  };
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/messagePush/selectUserMessage",
    data: JSON.stringify(page),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
        message = data.data;
        //页面加载信息
      } else {
        showMessage('错误码：' + data.data.code + '。 异常：' + data.data.message);
      }
    },
    error: function (e) {
      showMessage('服务器异常，页面加载错误');
    }
  });
  return message;
}

//消息已读ajax
function messageRead(messageId) {
  $.ajax({
    type: 'GET',
    contentType: "application/json",
    url: "/messagePush/messageRead/" + messageId,
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
      } else {
        showMessage('错误码：' + data.data.code + '。 异常：' + data.data.message);
      }
    },
    error: function (e) {
      showMessage('服务器异常，页面加载错误');
    }
  });
}

//保存修改的密码
function save_password() {
  //新密码校验
  var oldPass = $('#old_password').val();
  var newPass = $('#new_password').val();
  var reNewPass = $('#re_new_password').val();
  if (oldPass.length === 0 || newPass.length ===0 || reNewPass.length ===0) {
    showMessage('填写未完成');
    return ;
  }
  if ( !password_check(newPass, reNewPass)) {
    return ;
  }
  var UserResponseDTO = {
    oldPassword: oldPass,
    newPassword: newPass,
    reNewPassword: reNewPass
  };
  //密码正确性校验以及修改密码
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/user/changePassword",
    data: JSON.stringify(UserResponseDTO),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        showMessage('密码修改成功');
        setInterval("window.location.href = '/'", 1500);
      } else {
        showMessage(data.data);
      }
    },
    error: function (e) {
      showMessage("服务器异常，登录失败");
    }
  });
}

//修改用户信息Ajax
function change_user_info_ajax() {
  var user = {};
  user["name"] = $('#username').val();
  user["email"] = $('#email').val();
  user["gender"] = $("input[name='gender']:checked").val();
  // console.log(user.name);
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/user/changeUserInfo",
    data: JSON.stringify(user),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.code);
        M.toast({html: '基本信息修改成功!'});
        //定时刷新
        setInterval("location.reload()", 1500);
      } else {
        alert(data.data)
      }
    },
    error: function (e) {
      alert("服务器异常，修改失败");
    }
  });
}

// 用户板块加载Ajax
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
    url: "/user/getModuleList",
    data: JSON.stringify(moduleDTO),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        list = data.data;
        load_module_for_post(data.data);
      } else {
        showMessage(data.data);
      }
    },
    error: function (e) {
      showMessage("服务器异常，获取失败");
    }
  });
  // console.log(list);
  return list;
}

//用户各个板块展示
function load_module_for_post(data) {
  var list = data.list;
  //板块展示
  for (var i = 0; i < list.length; i++) {
    if (list[i].type === '0') {
      $('#campus-service').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          //拼接URL，跳转到板块详情
          + "<a target='_blank' href='/entrance/postDisplay?moduleId=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    } else if (list[i].type === '1') {
      $('#study').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          + "<a target='_blank' href='/entrance/postDisplay?moduleId=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    } else if (list[i].type === '2') {
      $('#game').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          + "<a target='_blank' href='/entrance/postDisplay?moduleId=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    } else if (list[i].type === '3') {
      $('#others').append("<li>"
          + "<div class='col s12 m4'>" + "<div class='card z-depth-0'>"
          + "<div class='card-content'>"
          + "<a target='_blank' href='/entrance/postDisplay?moduleId=" +list[i].id + "'>" + list[i].name + "</a>"
          + "</div>" + "</div>" + "</div>"
          + "</li>")
    }
  }
}

//用户获取主贴内容
function user_get_post(num, moduleId) {
  var post_list = null;
  $.ajaxSettings.async = false;
  $.get("/user/getModuleDetail/" + moduleId, function (data, status) {
    if (status === 'success') {
      //展示板块信息
      var detail =  data.data;
      //设置卡片下的元素内容
      var userContent = "<a href='#user-modal-detail' id='" + detail.userId + "' "
      + "class='modal-trigger blue-text text-lighten-3'>"+ detail.userName +"</a>";
      $('#post-name').text(detail.name);
      $('#post-admin').html(userContent);
      $('#module-card').append(detail.summary);
      //帖子展示
      //参数
      var searchRequestDTO = {
        pageSize: 20,
        pageNum: num,
        moduleId: moduleId,
        status: '0'
      };
      post_list = get_module_post_ajax(searchRequestDTO);
      //设置信息
      load_post_table("show-post",post_list);
      //点击查看用户详情
      view_user_detail();
    } else {
      showMessage(data.message);
    }
  });
  $.ajaxSettings.async = true;
  return post_list;
}

//点击查看用户详情
function view_user_detail() {
  $("[href='#user-modal-detail']").click(function () {
    //同步请求，get post默认异步
    $.ajaxSettings.async = false;
    $.get('/user/getUserDetail/' + this.id, function (data, status) {
      if (status === 'success') {
        if (data.code === 1000) {
          //成功
          // console.log(data.data);
          var info = data.data;
          $('#user-modal-detail').find("h4").text(info.name);
        } else {
          showMessage("状态码：" + data.code + " 异常信息：" + data.data);
        }
      } else {
        showMessage("服务器异常，查询失败");
      }
    });
  })
}

//获取板块下的全部主贴内容
function get_module_post_ajax(searchRequestDTO) {
  var post_list = null;
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/user/getPostList",
    data: JSON.stringify(searchRequestDTO),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
        post_list = data.data;
      } else {
      }
    },
    error: function (e) {
    }
  });
  return post_list;
}

//【主贴】展示主贴列表
function load_post_table(table ,data) {
  var list = data.list;
  $("#" + table + " tbody").empty();
  for (var i = 0; i < list.length; i++) {
    //赋值
    $("#show-post tbody").append("<tr>" +
        "<td width='30px'><i class='material-icons red-text'>" + post_type(list[i].type) + "</i></td>"
        + "<td width='100px'>"+ "评论数" +"</td>" +
        //点击查看主贴内容
        "<td><a href='/entrance/needInfo/postDetail?id=" + list[i].postId +"' "
        + "class='blue-text text-lighten-3'>"+ list[i].title+"</a></td>"
        + "<td width='200px'>"
        //点击查看用户信息
        + "<a href='#user-modal-detail' id='" + list[i].userId + "' "
        + "class='modal-trigger blue-text text-lighten-3'>"+ list[i].userName +"</a></td>"
        + "<td width='200px'>" + list[i].createTime + "</td>"
        + "</tr>");
  }
  if (list.length === 0) {
    $("#show-post tbody")
    .append('<tr><td colspan="5" class="center"><br><p class="red-text">暂无内容</p></td></tr>');
  }
}

//访问量增加Ajax
function visit_num_plus_ajax(postId) {
  $.ajax({
    type: 'GET',
    contentType: "application/json",
    url: "/visit/insertVisit/" + postId,
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
      }
    },
    error: function (e) {
    }
  });
}

//登录Ajax
function login() {
  var user = {
    studentno: $('#studentno').val(),
    password: $('#password').val(),
    type: 0
  };
  // console.log(user.studentno);
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/user/login",
    data: JSON.stringify(user),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        alert("你好 "+ data.data.name + "!");
        var h = "/";
        window.location.href = h;
      } else {
        alert(data.data);
      }
    },
    error: function (e) {
      showMessage("服务器异常，登录失败");
    }
  });
}

//加载用户关注的主贴ajax
function get_focus_post_list(size, num) {
  var focus_post_list = null;
  var page ={
    pageNum: num,
    pageSize: size
  };
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/userAttention/getUserAttentionList",
    data: JSON.stringify(page),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
        focus_post_list = data.data;
        //页面加载信息
        load_focus_list('my_focus_post',data.data.list);
      } else {
      }
    },
    error: function (e) {
    }
  });
  return focus_post_list;
}

//我的关注-》主贴展示/我发布的帖子展示
function load_focus_list(position, focus_list){
  for (var i = 0; i < focus_list.length; i++){
    $('#' + position).after('<br>' +
        '<li class="collection-header"><span class="new badge red lighten-3">4</span>'
        + '<a href="/entrance/postDisplay?moduleId=' + focus_list[i].moduleId +'">'
        + focus_list[i].moduleName
        + '</a> &nbsp;&nbsp;&nbsp;&nbsp;--->&nbsp;&nbsp;&nbsp;&nbsp; <a href="/entrance/needInfo/postDetail?id='
        + focus_list[i].postId +'">'
        + focus_list[i].title + '</a></li><br>');
  }
  if (focus_list.length === 0) {
    $('#' + position).after('<li class="center"><br><p class="red-text">暂无内容</p></li>');
  }
}

//注册基本信息检验
function check_info() {
  var username = $('#username').val();
  var email = $('#email').val();
  var studentno = $('#studentno').val();
  var studentno_pattern = /^\d{10}$/;
  if (isNull(username) || isNull(email) || isNull(studentno)) {
    msg = "用户名、邮箱、学号不能为空";
    showMessage(msg);
    return false;
  }
  if(!studentno_pattern.test(studentno)) {
    showMessage('学号输入错误，学号由10位数字组成');
    return false;
  }
  return true;
}

//注册密码检验
function password_check(pass1, pass2) {
  var usern = /^[a-zA-Z0-9_]{1,}$/i;
  if (!usern.test(pass1)) {
    msg = "密码只能由字母数字下划线组成";
    showMessage(msg);
    return false;
  }
  if (pass1.value !== pass2.value) {
    msg = '两次输入密码不一致';
    showMessage(msg);
    pass1.value = '';
    pass2.value = '';
    return false;
  }
  return true;
}

//关注主贴Ajax
function focus_post_ajax(postId) {
  var result = null;
  var postContentRequestDTO = {
    postId: postId
  };
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/userPostOperation/focusPost",
    data: JSON.stringify(postContentRequestDTO),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        console.log(data.data);
        showMessage(data.data);
        result = data.data;
      } else {
        showMessage(data.code + data.data);
      }
    },
    error: function (e) {
      showMessage(e)
    }
  });
  return result;
}

// 注册提交Ajax
function sign_submit() {
  var user = {};
  user["name"] = $('#username').val();
  user["email"] = $('#email').val();
  user["password"] = $('#password').val();
  user["studentno"] = $('#studentno').val();
  user["gender"] = $("input[name='gender']:checked").val();
  // console.log(user.name);
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/user/signUp",
    data: JSON.stringify(user),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        M.toast({html: '注册成功!'});
        //定时刷新
        setInterval("location.reload()", 1500);
      } else {
        alert(data.data)
      }
    },
    error: function (e) {
      alert("服务器异常，注册失败");
    }
  });
}

//学员登录成功后，加载信息的页面
function get_user_loginInfo_ajax() {
  var user = null;
  $.ajax({
    type: 'GET',
    contentType: "application/json",
    url: "/user/getLoginInfo",
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
        user = data.data;
        //设置信息
        $('#user_login_info').empty();
        $('#user_login_info').append('<li style="width: 120px">'
            + '<a class="center waves-red active red-text text-lighten-3 tooltipped" '
            + 'data-position="bottom" data-tooltip="点击修改个人信息"'
            + 'href="/entrance/needInfo/personalCenter">' + data.data.name + '</a><'
            + '/li>'
            +'<li style="width: 120px"><a class="center waves-red black-text" href="/logout">退出登录</a></li>')
      } else {
      }
    },
    error: function (e) {
    }
  });
  return user;
}

//学员登录后，展示未读消息
function show_unread_message() {
  $.ajaxSettings.async = false;
  $.get('/messagePush/getNumOfUnreadMessage', function (data, status) {
    if (status === 'success') {
      if (data.code === 1000) {
        //成功
        // console.log(data.data);
        if (data.data > 0) {
          $('#unread-message').append('+' + data.data);
        }
      } else {
      }
    } else {
      showMessage("服务器异常，查询失败");
    }
  });
  $.ajaxSettings.async = true;
}

// 注册信息方法
function showMessage(message) {
  M.toast({html: message});
}

