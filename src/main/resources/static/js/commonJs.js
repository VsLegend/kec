//获取用户详情
function get_user_detail_ajax(userId) {
  var detail = null;
  $.ajaxSettings.async = false;
  $.get("/userDisplay/getUserDetail/" + userId ,function (data) {
    if (data.code === 1000) {
      // console.log(data.data);
      detail = data.data;
    } else {
      showMessage(data.data);
    }
  });
  return detail;
}

// 新增主贴Ajax
function add_new_post_ajax(moduleId) {
  var content = ue.getContent();
  // console.log(content);
  var postDTO = {
    title: $('#post_name').val(),
    content: content,
    type: $('#post_type').val(),
    moduleId: moduleId
  };
  // console.log(moduleDTO.name);
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/post/insertPost",
    data: JSON.stringify(postDTO),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data.list);
        M.toast({html: '主贴发布成功!'});
        //定时刷新
        setInterval("location.reload()", 1500);
      } else {
        alert(data.data);
      }
    },
    error: function (data) {
      // console.log(data.responseJSON);
      alert("服务器异常，主贴发布失败。");
    }
  });
}

// 0校园服务 1学习交流  2娱乐交友  3其它板块
function module_type(type) {
  switch (type) {
    case '0':
      return '校园服务';
    case '1':
      return '学习交流';
    case '2':
      return '娱乐交友';
    case '3':
      return '其它板块';
    default:
      return '其他';
  }
}

// 帖子类型，0普通 1置顶  2精华  3热门
function post_type(type) {
  switch (type) {
    case '0':
      return '';
    case '1':
      return 'vertical_align_top';
    case '2':
      return 'invert_colors';
    case '3':
      return 'whatshot';
    default:
      return '其他';
  }
}

//年月日 格式化日期
function formatDatePure(dateObject) {
  var d = new Date(Number(dateObject));
  console.log(d);
  var minutes = d.getMinutes();
  var hours = d.getHours();
  var day = d.getDate();
  var month = d.getMonth() + 1;
  var year = d.getFullYear();
  if (day < 10) {
    day = "0" + day;
  }
  if (month < 10) {
    month = "0" + month;
  }
  var date = year + "-" + month + "-" + day + " " +hours + ":" + minutes;
  // console.log(date);
  return date;
}

//返回上一页
function go_back() {
  history.go(-1);
}

//获取当前链接中的参数
function getUrlParam(name) {
  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
  var r = window.location.search.substr(1).match(reg);  //匹配目标参数
  if (r!=null) return unescape(r[2]); return null; //返回参数值
}

//字符串是否为空或空格
function isNull(str) {
  if (str === "") {
    return true;
  }
  var regu = "^[ ]+$";
  var re = new RegExp(regu);
  return re.test(str);
}

// 注册信息方法
function showMessage(message) {
  M.toast({html: message});
}

//预加载内容
function reloading() {
  $("body").before('<div class="page-loading fixed">'
      + '  <div class="preloader-wrapper big active">'
      + '    <div class="spinner-layer spinner-blue">'
      + '      <div class="circle-clipper left">'
      + '        <div class="circle"></div>'
      + '      </div><div class="gap-patch">'
      + '      <div class="circle"></div>'
      + '    </div><div class="circle-clipper right">'
      + '      <div class="circle"></div>'
      + '    </div>'
      + '    </div>'
      + ''
      + '    <div class="spinner-layer spinner-red">'
      + '      <div class="circle-clipper left">'
      + '        <div class="circle"></div>'
      + '      </div><div class="gap-patch">'
      + '      <div class="circle"></div>'
      + '    </div><div class="circle-clipper right">'
      + '      <div class="circle"></div>'
      + '    </div>'
      + '    </div>'
      + ''
      + '    <div class="spinner-layer spinner-yellow">'
      + '      <div class="circle-clipper left">'
      + '        <div class="circle"></div>'
      + '      </div><div class="gap-patch">'
      + '      <div class="circle"></div>'
      + '    </div><div class="circle-clipper right">'
      + '      <div class="circle"></div>'
      + '    </div>'
      + '    </div>'
      + ''
      + '    <div class="spinner-layer spinner-green">'
      + '      <div class="circle-clipper left">'
      + '        <div class="circle"></div>'
      + '      </div><div class="gap-patch">'
      + '      <div class="circle"></div>'
      + '    </div><div class="circle-clipper right">'
      + '      <div class="circle"></div>'
      + '    </div>'
      + '    </div>'
      + '  </div>'
      + '</div>');
  ban_scroll();
}

function un_reloading() {
  $(".page-loading").hide();
  open_scroll();
}

//禁用滚动条
function ban_scroll() {
  var top = $(document).scrollTop();
  $(document).on('scroll.unable',function (e) {
    $(document).scrollTop(top);
  })
}

//启用滚动条
function open_scroll() {
  $(document).unbind("scroll.unable");
}