package com.major.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018/7/31.
 */
@Getter
@Setter
public class TipsRequest {
    private Long tipsCategoryId;

    private String title;

    private String summary;

    private String content;

    private String coverUrl;

    private Integer status;
}
