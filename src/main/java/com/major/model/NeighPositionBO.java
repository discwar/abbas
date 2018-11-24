package com.major.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/19 17:39      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
public class NeighPositionBO {

    private Double minLongitude;

    private Double maxLongitude;

    private Double minLatitude;

    private Double maxLatitude;

}
