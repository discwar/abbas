package com.major.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Title: APP分享配置业务类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 9:44      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
public class AppShareConfigBO {

    /**
     * APP分享标题
     */
    private String shareTitle;
    /**
     * APP分享描述
     */
    private String shareDesc;
    /**
     * APP分享图片地址
     */
    private String shareImageUrl;
    /**
     * APP分享链接
     */
    private String shareUrl;

}
