(function($){
  $(function(){
    //滑块
    $('.slider').slider();
    //图片视差
    $('.parallax').parallax();
    //tabs
    $('.tabs').tabs();
    //图片
    $('.materialboxed').materialbox();
    //固定悬浮按钮
    $('.fixed-action-btn').floatingActionButton();
    //左边导航
    $('.sidenav').sidenav();
    //输入框的字数限制
    $('input#post_name, textarea#textarea2').characterCounter();
    //小贴士工具
    $('.tooltipped').tooltip();
    //Select jQuery
    $('select').formSelect();
    //collapsibel
    $('.collapsible').collapsible();
    //对话框初始化
    $('.modal').modal();
    //传送带初始化
    $('.carousel').carousel();
    //右侧固定导航
    $('.scrollspy').scrollSpy();
    //新闻
    $('.carousel.carousel-slider').carousel({
      //宽度是否为全屏宽度
      fullWidth: false,
      indicators: true
    });
  }); // end of document ready
})(jQuery); // end of jQuery name space
