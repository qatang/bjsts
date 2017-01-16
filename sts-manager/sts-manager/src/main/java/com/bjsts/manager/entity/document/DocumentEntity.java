package com.bjsts.manager.entity.document;

import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangzhiliang on 2017/1/16.
 */
@Entity
@Table(name = "sts_document")
@DynamicInsert
@DynamicUpdate
public class DocumentEntity extends AbstractEntity {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件相对路径
     */
    @Column(nullable = false)
    private String url;

    /**
     * 分组
     */
    @Column(name = "group_key", nullable = false)
    private String groupKey;

    /**
     * 文件关联的对象编码
     */
    @Column(name = "object_id", nullable = false)
    private String objectId;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
