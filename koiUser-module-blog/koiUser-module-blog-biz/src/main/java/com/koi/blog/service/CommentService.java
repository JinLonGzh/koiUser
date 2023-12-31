package com.koi.blog.service;

import com.koi.blog.domain.vo.request.CommentAddReqVO;
import com.koi.blog.domain.vo.request.CommentQueryReqVO;
import com.koi.blog.domain.vo.request.ReplyQueryReqVO;
import com.koi.blog.domain.vo.response.CommentRespVO;
import com.koi.blog.domain.vo.response.ReplyRespVO;
import com.koi.common.domain.PageResult;

import java.util.List;

/**
 * @Author zjl
 * @Date 2023/12/25 17:11
 */
public interface CommentService {
    PageResult<CommentRespVO> pageComment(CommentQueryReqVO req);

    void addComment(CommentAddReqVO req, Long userId);

    List<ReplyRespVO> pageCommentReply(ReplyQueryReqVO req);
}
