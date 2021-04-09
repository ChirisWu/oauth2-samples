package com.tencent.chiris.projectrank.model;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
public class ProjectStarRank implements Serializable {

    private static final long serialVersionUID = 3641437948530301156L;

    public static final int TYPE_PROJ_PUBLIC_60 = 101;              // 所有Public项目的近60天Star、及排名
    public static final int TYPE_PROJ_INTERNAL_60 = 102;            // 所有Public、Internal项目的近60天Star、及排名
    public static final int TYPE_PROJ_PUBLIC_INNER_60 = 103;        // 所有Public的内源项目的近60天Star、及排名
    public static final int TYPE_PROJ_INTERNAL_INNER_60 = 104;      // 所有Public、Internal的内源项目的近60天Star、及排名
    public static final int TYPE_GROUP_PUBLIC_60 = 201;             // 所有项目组的近60天Star、及排名（Public项目）
    public static final int TYPE_GROUP_INTERNAL_60 = 202;           // 所有项目组的近60天Star、及排名（Internal项目）
    public static final int TYPE_TAG_PUBLIC_60 = 301;               // 所有主题的近60天Star、及排名（Public项目）
    public static final int TYPE_TAG_INTERNAL_60 = 302;             // 所有主题的近60天Star、及排名（Internal项目）

    private Integer id;

    private Integer type;

    private Long    categoryId;

    private Integer targetId;

    private Integer star;

    private Integer rank;

    private Date taskAt;

    private Date    createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Date getTaskAt() {
        return taskAt;
    }

    public void setTaskAt(Date taskAt) {
        this.taskAt = taskAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
