package com.koi.blog.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName blog_talk
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="blog_talk")
@Data
public class Talk implements Serializable {
    /**
     * 说说id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 说说内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 是否置顶
     */
    private Integer talkTop;

    /**
     * 状态 0公开 1关闭
     */
    private Integer status;

    /**
     * 阅读量
     */
    private Integer viewCount;

    /**
     * 发表时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}