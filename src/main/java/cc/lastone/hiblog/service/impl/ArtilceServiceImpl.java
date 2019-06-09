package cc.lastone.hiblog.service.impl;

import cc.lastone.hiblog.domain.Article;
import cc.lastone.hiblog.domain.ArticleCategory;
import cc.lastone.hiblog.domain.ArticleCollect;
import cc.lastone.hiblog.domain.Category;
import cc.lastone.hiblog.repository.*;
import cc.lastone.hiblog.service.ArticleService;
import cc.lastone.hiblog.service.CategoryService;
import cc.lastone.hiblog.to.ArticleCategoryTo;
import cc.lastone.hiblog.to.ArticleIndexTo;
import cc.lastone.hiblog.to.CollectArticleTo;
import cc.lastone.hiblog.to.NewestArticleTo;
import cc.lastone.hiblog.utils.ConvertDataToVoUtil;
import cc.lastone.hiblog.utils.MyBeanUtils;
import cc.lastone.hiblog.utils.MyDateUtil;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.vo.ArticleDetailVo;
import cc.lastone.hiblog.vo.ArticleSearchShowVo;
import cc.lastone.hiblog.vo.ArticleSetVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ArtilceServiceImpl implements ArticleService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleCollectRepository articleCollectRepository;

    @Override
    public ArticleSetVo getArticleSetInfo(Integer uid, Integer id) {
        // 1.查询出 博客系统分类
        ArrayList<Category> categoryList = (ArrayList<Category>) categoryRepository.findByUidOrderBySnAsc(0);
        // 查询出个人分类
        ArrayList<Category> myCategoryList = (ArrayList<Category>) categoryRepository.findByUidOrderBySnAsc(uid);
        ArticleSetVo articleSetVo = new ArticleSetVo();
        // 查询出
        if (id != 0) {
            // 查询出博客详情
            Article article = articleRepository.findByIdAndUid(id, uid);
            if (article == null) {
                return null;
            }
            // 查询出当前博客的自选标签
            ArrayList<ArticleCategory> articleCategoryList = (ArrayList<ArticleCategory>) articleCategoryRepository.findAllByArticleId(id);
            BeanUtils.copyProperties(article, articleSetVo);
            if (articleCategoryList != null && articleCategoryList.size() > 0) {
                ArrayList<Integer> myCategoryIds = new ArrayList<Integer>();
                for (ArticleCategory ac : articleCategoryList) {
                    myCategoryIds.add(ac.getCategoryId());
                }
                String[] myCategoryNames = new String[articleCategoryList.size()];
                int i = 0;
                for (Category c : myCategoryList) {
                    if (myCategoryIds.contains(c.getId())) {
                        myCategoryNames[i] = c.getName();
                        i++;
                    }
                }
//                System.out.println(myCategoryNames);
                // 设置个人分类 setArticleCategoryList
                articleSetVo.setCategoryArr(myCategoryNames);
            }
        }
        articleSetVo.setCategoryList(categoryList);
        articleSetVo.setMyCategoryList(myCategoryList);
        return articleSetVo;
    }

    @Override
    @Transactional
    public ResultData setArticleInfo(ArticleSetVo articleSetVo, Integer uid) {
        Article article = null;
        if (articleSetVo.getIsPrivate() == null) {
            articleSetVo.setIsPrivate((byte) 0);
        }
        // 判断是否是修改
        if (articleSetVo.getId() != null && articleSetVo.getId() > 0) {
            // 修改
            article = articleRepository.findByIdAndUid(articleSetVo.getId(), uid);
            // 查询数据
            if (article == null) {
                return new ResultData(ResultData.ERROR_NODATA, "数据不存在");
            }
            // 删除子分类
            articleCategoryRepository.deleteByArticleId(articleSetVo.getId());

            // 加一, 原来不是公开并发布，现在是公开并发布
            if ((article.getStatus() != 2 || article.getIsPrivate() == 1) && articleSetVo.getIsPrivate() == 0 && articleSetVo.getStatus() == 2) {
                userRepository.updateUserArticleNum(uid, 1);
            }
            //减一，原来是公开并发布，现在是不公开或不发布
            if (article.getStatus() == 2 && article.getIsPrivate() == 0 && (articleSetVo.getIsPrivate() == 1 || articleSetVo.getStatus() != 2)) {
                userRepository.updateUserArticleNum(uid, -1);
            }
        } else {
            article = new Article();
            // 添加
            article.setUid(uid);
            // 判断是否是公开并且是原创的博客
            if ((articleSetVo.getIsPrivate() == null || articleSetVo.getIsPrivate() == 0) && articleSetVo.getStatus() == 2) {
                // 加一
                userRepository.updateUserArticleNum(uid, 1);
            }
        }

        // 修改时间
        article.setCreatedAt(MyDateUtil.date());
        MyBeanUtils.copyProperties(articleSetVo, article);

//        System.out.println(article);

        // 保存数据
        article = articleRepository.save(article);
        if (article == null) {
            return new ResultData(ResultData.ERROR_SAVE, "保存博客失败");
        }

        // 保存分类
        String[] categoryArr = articleSetVo.getCategoryArr();
        if (categoryArr != null && categoryArr.length > 0) {
            Category category = null;
            ArticleCategory ac = new ArticleCategory();
            ac.setArticleId(article.getId());
            for (String name : categoryArr) {
                category = categoryRepository.findByNameAndUid(name, uid);
                if (category == null) {
                    Category categoryTemp = new Category();
                    categoryTemp.setUid(uid);
                    categoryTemp.setName(name);
                    ResultData<Category> rd = categoryService.add(categoryTemp);
                    if (rd.getState() != 0) {
                        return rd;
                    }
                    // 添加分类
                    ac.setCategoryId(rd.getData().getId());
                } else {
                    ac.setCategoryId(category.getId());
                }
                // 添加分类
                articleCategoryRepository.save(ac);
            }
        }

        HashMap hm = new HashMap();
        hm.put("id", article.getId());
        return new ResultData(hm, "保存成功");
    }

    @Override
    public Map searchArticle(String startDate, String endDate, int type, int status, int is_private, String title, Integer uid, int categoryId, Pageable pageable) {
        Page<Object[]> objects = null;
        if (categoryId > 0) {
            objects = articleRepository.searchArticleWithCategoryPaging(startDate, endDate, type, status, is_private, title, uid, categoryId, pageable);
        } else {
            objects = articleRepository.searchArticleWithoutCategoryPaging(startDate, endDate, type, status, is_private, title, uid, pageable);
        }
        HashMap hm = new HashMap();
        if (objects != null) {
            List<ArticleSearchShowVo> articleSearchShowVos = ConvertDataToVoUtil.convert(objects.getContent(), ArticleSearchShowVo.class);
            hm.put("nowPage", objects.getPageable().getPageNumber() + 1);
            hm.put("totalPage", objects.getTotalPages());
            hm.put("totalNum", objects.getTotalElements());
            hm.put("articleList", articleSearchShowVos);
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
            hm.put("articleList", new ArrayList<ArticleSearchShowVo>());
        }
        return hm;
    }

    @Override
    public Map searchArticle(String title, Pageable pageable) {
        Page<Article> objects = articleRepository.findAllByTitleLikeAndIsPrivateAndStatus(title, (byte)0, (byte)2, pageable);
        HashMap hm = new HashMap();
        if (objects != null) {
            hm.put("nowPage", objects.getPageable().getPageNumber() + 1);
            hm.put("totalPage", objects.getTotalPages());
            hm.put("totalNum", objects.getTotalElements());
            hm.put("articleList", objects.getContent());
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
            hm.put("articleList", new ArrayList<Article>());
        }
        return hm;
    }

    @Override
    @Transactional
    public ResultData deleteMyArtilce(Integer id, Integer uid) {
        int i = articleRepository.deleteByIdAndUid(id, uid);
        if (i == 0) {
            return new ResultData(ResultData.ERROR_NODATA, "博客不存在");
        }
        // 删除子分类
        articleCategoryRepository.deleteByArticleId(id);
        return new ResultData("删除博客成功");
    }

    @Override
    public ResultData editArticle(Article article, Integer uid) {
        // 修改
        Article oldArticle = articleRepository.findByIdAndUid(article.getId(), uid);
        // 查询数据
        if (oldArticle == null) {
            return new ResultData(ResultData.ERROR_NODATA, "数据不存在");
        }
        MyBeanUtils.copyProperties(article, oldArticle);
        // 保存数据
        oldArticle = articleRepository.save(oldArticle);
        if (oldArticle == null) {
            return new ResultData(ResultData.ERROR_SAVE, "修改博客失败");
        }
        return new ResultData("修改博客成功");
    }

    /**
     * 获得展示博客详情
     *
     * @param id
     * @return
     */
    @Override
    public ArticleDetailVo getArticleShowInfo(Integer id, Integer currentUid) {
        // 获得博客详情
        // 查询出博客详情
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }
        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        MyBeanUtils.copyProperties(article, articleDetailVo);
        // 查询出是否已收藏
        if (currentUid == 0 || currentUid.equals(articleDetailVo.getUid())) {
            articleDetailVo.setCollectType(-1);
        } else {
            int i = articleCollectRepository.countByUidAndArticleId(currentUid, id);
            articleDetailVo.setCollectType(i > 0 ? 1 : 0);
        }

        //查询出个人分类
        List<ArticleCategoryTo> articleCategoryToList =
                ConvertDataToVoUtil.convert(categoryRepository.findArticleCategory(id), ArticleCategoryTo.class);
        articleDetailVo.setArticleCategoryList(articleCategoryToList);
        return articleDetailVo;
    }

    @Override
    public List<NewestArticleTo> getNewstArticleList(Integer uid, Integer size) {
        return ConvertDataToVoUtil.convert(articleRepository.findNewestArticle(uid, size), NewestArticleTo.class);
    }

    @Override
    public ResultData collectArticle(Integer uid, Integer articleId) {
        int i = articleCollectRepository.countByUidAndArticleId(uid, articleId);
        if (i == 0) {
            ArticleCollect articleCollect = new ArticleCollect();
            articleCollect.setUid(uid);
            articleCollect.setArticleId(articleId);
            articleCollectRepository.save(articleCollect);
            if (articleCollect == null) {
                return new ResultData(ResultData.ERROR_SAVE, "收藏失败");
            }
        }
        return new ResultData("收藏博客成功");
    }

    @Override
    @Transactional
    public ResultData cancelCollectArticle(Integer uid, Integer articleId) {
        int i = articleCollectRepository.countByUidAndArticleId(uid, articleId);
        if (i == 1) {
            i = articleCollectRepository.deleteByUidAndArticleId(uid, articleId);
            if (i == 0) {
                return new ResultData(ResultData.ERROR_SAVE, "取消收藏失败");
            }
        }
        return new ResultData("取消收藏成功");
    }

    @Override
    public Map getCollectArticle(Integer uid, Pageable pageable) {
        Page<Object[]> objects = articleCollectRepository.getCollectArticle(uid, pageable);
        HashMap hm = new HashMap();
        if (objects != null) {
            List<CollectArticleTo> collectArticleList = ConvertDataToVoUtil.convert(objects.getContent(), CollectArticleTo.class);
            hm.put("nowPage", objects.getPageable().getPageNumber() + 1);
            hm.put("totalPage", objects.getTotalPages());
            hm.put("totalNum", objects.getTotalElements());
            hm.put("collectArticleList", collectArticleList);
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
            hm.put("collectArticleList", new ArrayList<CollectArticleTo>());
        }
        return hm;
    }

    @Override
    public Map getIndexArticle(int categoryId, Pageable pageable) {
        Page<Object[]> objects = articleRepository.findIndexArticle(categoryId, pageable);
        HashMap hm = new HashMap();
        if (objects != null) {
            List<ArticleIndexTo> articleSearchShowVos = ConvertDataToVoUtil.convert(objects.getContent(), ArticleIndexTo.class);
            hm.put("nowPage", objects.getPageable().getPageNumber() + 1);
            hm.put("totalPage", objects.getTotalPages());
            hm.put("totalNum", objects.getTotalElements());
            hm.put("articleList", articleSearchShowVos);
        } else {
            hm.put("nowPage", 1);
            hm.put("totalPage", 1);
            hm.put("totalNum", 0);
            hm.put("articleList", new ArrayList<ArticleIndexTo>());
        }
        return hm;
    }

    @Override
    public List<Article> getPopularArticle(Integer size) {
        return articleRepository.findPopularArticle(size);
    }
}
