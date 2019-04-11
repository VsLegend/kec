//展示主贴以及其评论，回复
function user_show_post_comment(user, data, postId, post) {
  load_post_content(post, user);
  //展示评论以及其回复
  var comment = data.list;
  // console.log(comment);
  //展示评论
  for (var i = 0; i < comment.length; i++) {
    var commentId = comment[i].commentId;
    var owner = '';
    if (post.userId === comment[i].userId) {
      owner += '<span class="badge child-like-red white-text">楼主</span>';
    }
    var operaion = '';
    operaion = getCommentBottomInfo(post, comment[i], user, '0', i + 2);
    // 在翻页前依次插入
    $('#pagination-li').before(
        '  <li class="collection-item">'
        + '  <div class="row" style="margin: 0; padding-top:5px">'
        + '    <div class="col s2 center">'
        + '      <div class="row">'
        + '        <img src="/static/image/9.jpg" width="64" height="64"'
        + '             class="circle child-like-border-blue">'
        + '      </div>'
        //用户姓名
        + '      <a href="#user-modal-detail" class="modal-trigger child-like-text-blue" id="'
        + comment[i].userId + '">' + comment[i].userName + '</a>' + owner
        + '    </div>'
        + '    <div class="col s10" style="border-left: #eceff1 solid 1px">'
        + '      <!--主贴内容-->'
        + '      <div class="col s12"><p>' + comment[i].content + '<br></p></div><br>'
        + '      <!--操作-->'
        + '      <div class="col s12"><p class="right">' + operaion + '</p></div>'
        + '      <!--回复-->'
        + '      <div class="col s12">'
        + '        <ul class="collection with-header" >'
        + '          <li class="collection-item" id="li_' + commentId + '">'
        + '            <textarea  placeholder="Placeholder" id="reply_' + commentId + '"></textarea>'
        + '            <a class="btn-small waves-effect waves-light right child-like-blue white-text" '
        + '               onclick="send_reply_p(this.id)" '
        + '               id="reply_button' + commentId + '">'
        + '              发送'
        + '            </a>'
        + '          </li>'
        + '        </ul>'
        + '      </div>'
        + '    </div>'
        + '  </div>'
        + '</li>');
    //展示回复
    var reply = comment[i].replyResponseDTOS;
    if (reply.length != 0) {
      for (var j = 0; j < reply.length; j++) {
        var replyOwn = '';
        replyOwn = getReplyBottomInfo(post, reply[j], user, '1');
        $('#li_' + commentId).before('<li class="collection-item">'
            + '<div class="" id="' + reply[j].replyId + '"><a href="#user-modal-detail"'
            + 'id="' + reply[j].userId + '" class="modal-trigger blue-text text-lighten-3"> '
            + reply[j].userName + '</a> 回复: '
            + reply[j].content
            + '<p class="right" style="margin: 3px">' + replyOwn + '</p>'
            + '</div>'
            + '</li>');
      }
    }
    view_user_detail();
  }
}


// 主贴内容加载Ajax
function post_content_ajax(size, num, post) {
  var postContentRequestDTO = {
    pageSize: size,
    pageNum: num,
    postId: getUrlParam("id")
  };
  var list = "";
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/post/getPostAndComment",
    data: JSON.stringify(postContentRequestDTO),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
        list = data.data;
      } else {
        showMessage(data.data);
      }
    },
    error: function (e) {
      showMessage("服务器异常");
    }
  });
  // console.log(list);
  return list;
}

//展示主贴以及其评论，回复
function show_post_comment(data, postId, post) {
  if (post.status === "1") {
    $("#add_new_comment").attr("disabled", true);
    return;
  }
  $('#post_div').find('a').text(post.userName);
  $('#post_div').find('h5').html(post.title);
  $('#post_div').find('span').html(post.content);
  //展示评论以及其回复
  var comment = data.list;
  // console.log(comment);
  //展示评论
  for (var i = 0; i < comment.length; i++) {
    var commentId = comment[i].commentId;
    $('#post_card_show').append('<div class="row valign-wrapper" id="post_' + postId + '"> '
        + '<div class="col s2">'
        + '<img src="/static/image/chrome.png" alt="" class="circle-clipper responsive-img">'
        + '<br><a href="#!">' + comment[i].userName + '</a>'
        + '</div>'
        + '<div class="col s10">'
        + '<p class="right">' + comment[i].createTime + ' ' +  (i+2) +'楼 </p><br><br>'
        + '<span id="' + comment[i].commentId + '">' + comment[i].content + '</span>'
        //回复栏
        + '<ul class="collection" id="coll_' + commentId + '">'
        + '<li class="collection-header">回复：</li>'
        + '</ul>'
        + '</div>'
        + '</div>'
        + '<br><hr color="#f0f4c3"><br>');
    //展示回复
    var reply = comment[i].replyResponseDTOS;
    if (reply.length != 0) {
      for (var j = 0; j < reply.length; j++) {
        $('#coll_' + commentId).append('<li class="collection-item avatar">'
            + '<img src="/static/image/apple.png" alt="" class="circle">'
            + '<pre><p id="' + reply[j].replyId + '">&#9;<a href="#!"> '
            + reply[j].userName + '</a> 回复:'
            + reply[j].content +
            '</p></pre></li>');
      }
    }
    //回复按钮
    $('#coll_' + commentId).append('<li class="collection-item avatar">'
        + '<form>'
        + '<div class="col s10"> '
        + '<input size="60%" placeholder="回复内容" id="reply_' + commentId + '" type="text"></div>'
        + '<div class="col s2"> <button id="reply_button' + commentId
        + '" class="btn-small btn waves-effect waves-light light-blue lighten-3" '
        + 'onclick="send_reply_p(this.id)" type="button" name="action">回复'
        + '<i class="material-icons right">send</i></button></div>'
        + '</form>'
        + '</li>');
  }
}

//获取单个主贴的详细内容
function get_post_detail_ajax(postId) {
  var list = null;
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/post/getPost/" + postId,
    data: JSON.stringify(),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        // console.log(data.data);
        list = data.data;
      } else {
        showMessage("CODE：" + data.code+ + " 原因：" + data.data);
      }
    },
    error: function (e) {
      showMessage("服务器异常");
    }
  });
  return list;
}

//回复内容
function send_reply_p(commentId) {
  var commentId = commentId.substring(12);
  var text = $('#reply_' + commentId).val();
  if (text === null || text === '') {
    alert("回复内容不能为空");
    return ;
  }
  send_reply_ajax(commentId, '1', text)
}

// 评论内容
function send_comment_p(postId) {
  var content = ue.getContent();
  if (content === null || content === '') {
    alert("回复内容不能为空");
    return ;
  }
  send_reply_ajax(postId, '0', content);
}

//回复评论
function send_reply_ajax(relationId, type, text) {
  // console.log(id);
  // console.log(text);
  var commentRequestDTO = {
    //0评论主贴  1回复评论
    relationType: type,
    relationId: relationId,
    content: text,
    postId: getUrlParam("id")
  };
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/comment/insertComment",
    data: JSON.stringify(commentRequestDTO),
    datatype: 'json',
    async: false,
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        showMessage('评论发布成功');
        //定时刷新
        setInterval("location.reload()", 2000);
      } else {
        showMessage(data.data);
      }
    },
    error: function (e) {
      showMessage("服务器异常，回复失败");
    }
  });
}

//加载主贴信息
function load_post_content(post, user) {
  if (post.status === "1") {
    $("#add_new_comment").attr("disabled", true);
    return;
  }
  var operation = null;
  operation = getPostBottomInfo(post, user);
  $('#user-name').text(post.userName);
  $('#user-name').attr("id", user.id);
  $('#post-title').text(post.title);
  $('#post-comment-content').html(post.content);
  $('#operation-date-content').html(operation);
}

//返回 主贴是否有删除按钮
function getPostBottomInfo(post, user) {
  // console.log(post);
  var info = "";
  //可以删除本人发布的主贴、评论或回复
  if (post.userId === user.id) {
    info += ('<a href="#!" onclick="delete_post_ajax(\'' + post.postId + '\')">删除</a>' + ' | ')
  }
  info +=(" 1楼 " + post.createTime + " | ");
  return info;
}

//返回 评论是否有删除按钮
function getCommentBottomInfo(post, comment, user, relationType, i) {
  // 类型 0评论  1回复
  // console.log(post);
  var info = "";
  //可以删除本人发布的主贴、评论或回复
  if (post.userId === user.id || comment.userId === user.id) {
    info += ('<a href="#!" onclick="delete_comment_ajax(\'' + post.postId + '\',\'' + comment.commentId + '\',\'' + relationType + '\')">删除</a>' + ' | ')
  }
  info +=(" "+ i + "楼 " + comment.createTime + " | ");
  return info;
}

//返回 回复是否有删除按钮
function getReplyBottomInfo(post, reply, user, relationType) {
  // 类型 0评论  1回复
  // console.log(post);
  var info = "";
  //可以删除本人发布的主贴、评论或回复
  if (post.userId === user.id || reply.userId === user.id) {
    info += ('<a href="#!" onclick="delete_comment_ajax(\'' + post.postId + '\',\'' + reply.replyId + '\',\'' + relationType + '\')">删除</a>' + ' | ')
  }
  info +=(" " + reply.createTime + " | ");
  info +=('<a href="#!" class="">回复</a>');
  return info;
}

//用户删除主贴Ajax
function delete_post_ajax(postId) {
  $.ajax({
    type: 'GET',
    contentType: "application/json",
    url: "/userPostOperation/deletePost/" + postId,
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        showMessage("删除成功");
        setInterval("go_back();", 1500);
      } else {
        showMessage("删除失败：" + data.message);
      }
    },
    error: function (e) {
      showMessage("服务器异常");
    }
  });
}

//删除评论或回复Ajax 类型 0评论  1回复
function delete_comment_ajax(postId, commentId, relationType) {
  var dto = {
    commentId: commentId,
    postId: postId,
    relationType: relationType
  };
  $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/userPostOperation/deleteComment",
    data: JSON.stringify(dto),
    datatype: 'json',
    cache: false,
    timeout: 99999,
    success: function (data) {
      if (data.code === 1000) {
        showMessage("删除成功");
        setInterval("window.location.reload();", 1500);
      } else {
        showMessage("删除失败：" + data.message);
      }
    },
    error: function (e) {
      showMessage("服务器异常");
    }
  });
}