package com.tyf.bookreaderplus.common.constant;

/**
 * @Description TODO
 * @Author shallow
 * @Date 2023/4/1 18:26
 */

public class RedisConstants {

    public static final Long COMMON_CACHE_TIME = 600L;

    /**
     * 分布式锁
     */
    public static final Long REDISSON_LOCK_WAIT = 5L;

    public static final Long REDISSON_LOCK_RELEASE = 10L;

    public static final String REDISSON_LOCK_VISIT="redissonLockVisit";

    public static final String REDISSON_LOCK_COMMENT = "redissonLockComment";

    public static final String REDISSON_LOCK_COMMENT_MODIFY = "redissonLockCommentModify";

    public static final String REDISSON_LOCK_PAY_ORDER = "redissonLockPayOrder";

    /**
     * 验证码相关
     */
    public static final String IMG_VERIFY_CODE_CACHE_KEY = "imgVerifyCodeCache::";

    public static final Long IMG_VERIFY_CODE_CACHE_TIME = 60L;

    /**
     * 图书类型相关
     */
    public static final String BOOK_CATEGORY_LIST_CACHE_KEY = "bookCategoryListCache";

    public static final String CATEGORY_LAST_UPDATE_CACHE_KEY = "categoryListUpdateCacheKey";

    public static final Long CATEGORY_LAST_UPDATE_CACHE_TIME = 1800L;

    /**
     * 小说信息缓存
     */
    public static final String BOOK_INFO_CACHE_KEY = "bookInfoCache";
    /**
     * 图书访问量和点赞数相关
     */
    public static final String BOOK_VISIT_COUNT = "bookVisitCount";

    /**
     * 章节相关
     */
    public static final String CHAPTER_INFO_CACHE_KEY = "chapterInfoCacheKey";

    public static final String CHAPTER_CONTENT_CACHE_KEY = "chapterContentCacheKey";

    /**
     * 评论相关
     */
    public static final String COMMENT_STAR_NUM_CACHE_KEY = "commentStarNumCacheKey";

    public static final String COMMENT_STAR_RELATION_CACHE_KEY = "commentStarRelationCacheKey";

    /**
     * 热门书籍相关
     */
    public static final String HOT_BOOK_CACHE_KEY = "hotBookCacheKey";
    /**
     * 登录相关
     */
    public static final long LOGIN_USER_TTL = 300000L ;

    /**
     * 订单相关
     */
    public static final String ORDER_KEY = "orderKey";
}
