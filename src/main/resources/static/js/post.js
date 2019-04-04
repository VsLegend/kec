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
        show_post_comment(data, getUrlParam("id"), post);
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
  // console.log(data);
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