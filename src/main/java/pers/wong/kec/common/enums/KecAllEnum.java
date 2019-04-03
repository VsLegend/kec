package pers.wong.kec.common.enums;

/**
 * @author Wangjunwei
 * @Date 1/24/2019 3:26 PM
 * @Description
 */
public enum KecAllEnum {

  /**
   * 用户类型
   */
  ROLE_USER("1", "0", "普通用户"),
  ROLE_MODULE_ADMIN("1", "1", "板块管理员"),
  ROLE_ADMIN("1", "2", "系统管理员"),

  /**
   * 公用状态
   */
  STATUS_NORMAL("2", "0", "正常"),
  STATUS_DELETE("2", "1", "删除"),

  /**
   * 消息推送类型
   */
  MESSAGE_TYPE_SYS("3", "0", "系统新闻推送"),
  MESSAGE_TYPE_POST("3", "1", "关联主贴"),
  MESSAGE_TYPE_COMMENT("3", "2", "关联评论"),
  MESSAGE_TYPE_REPLY("3", "3", "关联回复"),

  /**
   * 消息操作类型
   */
  OPERATION_TYPE_INSERT("4", "0", "新增"),
  OPERATION_TYPE_DELETE("4", "1", "删除"),
  OPERATION_TYPE_UPDATE("4", "2", "修改"),

  MESSAGE_READ_FLAG_FALSE("5", "0", "没有查看"),
  MESSAGE_READ_FLAG_TRUE("5", "1", "已查看"),

  /**
   * 板块类型  0校园服务 1学习交流  2娱乐交友  3其他板块
   */

  MODULE_TYPE_SCHOOL("6", "0", "校园服务"),
  MODULE_TYPE_LEARN("6", "1", "学习交流"),
  MODULE_TYPE_ENTERTAINMENT("6", "2", "娱乐交友"),
  MODULE_TYPE_OTHERS("6", "3", "其他板块"),

  /**
   * 帖子类型，0普通 1置顶  2精华  3热门
   */
  POST_TYPE_NORMAL("7", "0", "普通"),
  POST_TYPE_TOPPING("7", "1", "置顶"),
  POST_TYPE_ESSENCE("7", "2", "精华"),
  POST_TYPE_POPULAR("7", "3", "热门"),
  ;

  private String group;
  private String code;
  private String message;

  KecAllEnum(String group, String code, String message) {
    this.group = group;
    this.code = code;
    this.message = message;
  }

  public String getGroup() {
    return group;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
