package pers.wong.kec.domain.entity;

import java.util.Date;
import javax.persistence.*;

public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 访问主贴id
     */
    @Column(name = "post_id")
    private String postId;

    /**
     * 访问人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 状态 0 正常 1 删除
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新用户id
     */
    @Column(name = "update_user")
    private String updateUser;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取访问主贴id
     *
     * @return post_id - 访问主贴id
     */
    public String getPostId() {
        return postId;
    }

    /**
     * 设置访问主贴id
     *
     * @param postId 访问主贴id
     */
    public void setPostId(String postId) {
        this.postId = postId;
    }

    /**
     * 获取访问人id
     *
     * @return user_id - 访问人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置访问人id
     *
     * @param userId 访问人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取状态 0 正常 1 删除
     *
     * @return status - 状态 0 正常 1 删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 0 正常 1 删除
     *
     * @param status 状态 0 正常 1 删除
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新用户id
     *
     * @return update_user - 更新用户id
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新用户id
     *
     * @param updateUser 更新用户id
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}