package com.koi.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.koi.blog.constants.ArticleStatusEnum;
import com.koi.blog.constants.HomeListTypeEnum;
import com.koi.blog.domain.entity.HomeList;
import com.koi.blog.domain.vo.request.HomeListQueryReqVO;
import com.koi.blog.domain.vo.response.ArticleRespVO;
import com.koi.blog.domain.vo.response.HomeListRespVO;
import com.koi.blog.domain.vo.response.TalkRespVO;
import com.koi.blog.mapper.mysql.HomeListMapper;
import com.koi.blog.service.ArticleService;
import com.koi.blog.service.HomeService;
import com.koi.blog.service.TalkService;
import com.koi.common.domain.PageResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.common.utils.json.JsonUtils;
import com.koi.framework.mybatis.utils.MyBatisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author zjl
 * @Date 2023/12/17 12:40
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private HomeListMapper homeListMapper;
    @Resource
    private ArticleService articleService;
    @Resource
    private TalkService talkService;

    @Override
    public PageResult<HomeListRespVO> pageHomeList(HomeListQueryReqVO req) {
//        PageResult<HomeList> homeListPageResult = homeListMapper.pageHomeList(reqVO);
//        List<HomeList> homeList = homeListPageResult.getList();
//
//        // 整合相同类型内容
//        Map<Integer, List<Long>> homeMap = new HashMap<>();
//        for (HomeList item : homeList) {
//            if (homeMap.containsKey(item.getType())) {
//                homeMap.get(item.getType()).add(item.getCid());
//            } else {
//                List<Long> list = new ArrayList<>();
//                list.add(item.getCid());
//                homeMap.put(item.getType(), list);
//            }
//        }
//
//        // 执行查询
//        Map<Long, ArticleRespVO> articleMap = new HashMap<>();
//        Map<Long, TalkRespVO> talkMap = new HashMap<>();
//
//        for (Map.Entry<Integer, List<Long>> entry : homeMap.entrySet()) {
//            if (HomeListTypeEnum.ARTICLE.getType().equals(entry.getKey())) {        // 文章列表
//                List<ArticleRespVO> articleRespVOList = articleService.getArticleDetailByIdList(entry.getValue());
//                articleMap = articleRespVOList.stream().collect(Collectors.toMap(ArticleRespVO::getId, o -> o, (front, behind) -> front));
//            } else if (HomeListTypeEnum.TALK.getType().equals(entry.getKey())) {    // 说说列表
//                List<TalkRespVO> talkRespVOList = talkService.getTalkDetailByIdList(entry.getValue());
//                talkMap = talkRespVOList.stream().collect(Collectors.toMap(TalkRespVO::getId, o -> o, (front, behind) -> front));
//            }
//        }
//
//        // 组装数据
//        List<HomeListRespVO> homeListRespVOList = new ArrayList<>();
//        for (HomeList item : homeList) {
//            HomeListRespVO homeListRespVO = new HomeListRespVO();
//            if (HomeListTypeEnum.ARTICLE.getType().equals(item.getType())) {        // 文章列表
//                homeListRespVO.setType(HomeListTypeEnum.ARTICLE.getType());
//                homeListRespVO.setHomeListDTO(articleMap.get(item.getCid()));
//            } else if (HomeListTypeEnum.TALK.getType().equals(item.getType())) {    // 说说列表
//                homeListRespVO.setType(HomeListTypeEnum.TALK.getType());
//                homeListRespVO.setHomeListDTO(talkMap.get(item.getCid()));
//            }
//            homeListRespVOList.add(homeListRespVO);
//        }
//        return new PageResult<>(homeListRespVOList, homeListPageResult.getTotal());

        Page<HomeList> homeListPage = homeListMapper.selectPage(
                MyBatisUtils.buildPage(req),
                new LambdaQueryWrapper<HomeList>()
                        .eq(HomeList::getStatus, ArticleStatusEnum.PUBLIC.getStatus())
        );

        List<HomeListRespVO> result = new ArrayList<>();
        for (HomeList item : homeListPage.getRecords()) {
            HomeListRespVO homeListRespVO = new HomeListRespVO();
            homeListRespVO.setType(item.getType());

            if (HomeListTypeEnum.ARTICLE.getType().equals(item.getType())) {
                ArticleRespVO articleRespVO = ArticleRespVO.builder()
                        .id(item.getId())
                        .articleCover(item.getCover())
                        .articleTitle(item.getTitle())
                        // 主页展示字数限制
                        .articleContent(item.getContent().substring(0, 500))
                        .articleTop(item.getTop())
                        .categoryId(item.getCategoryId())
                        .categoryName(item.getCategoryName())
                        .viewCount(item.getViewCount())
                        .createTime(item.getCreateTime())
                        .updateTime(item.getUpdateTime())
                        .build();
                homeListRespVO.setHomeListDTO(articleRespVO);
            } else if (HomeListTypeEnum.TALK.getType().equals(item.getType())) {
                TalkRespVO talkRespVO = TalkRespVO.builder()
                        .id(item.getId())
                        .content(item.getContent())
                        .images(item.getImages())
                        .talkTop(item.getTop())
                        .status(item.getStatus())
                        .viewCount(item.getViewCount())
                        .createTime(item.getCreateTime())
                        .updateTime(item.getUpdateTime())
                        .build();
                // 处理说说图片格式
                if (Objects.nonNull(talkRespVO.getImages())) {
                    talkRespVO.setImageList(CollectionUtils.castList(JsonUtils.parseObject2(talkRespVO.getImages(), List.class), String.class));
                }
                homeListRespVO.setHomeListDTO(talkRespVO);
            }
            result.add(homeListRespVO);
        }

        return new PageResult<>(result, homeListPage.getTotal());
    }
}
