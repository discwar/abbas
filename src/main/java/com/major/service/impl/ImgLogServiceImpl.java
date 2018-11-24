package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.util.ImgUtils;
import com.major.common.util.OSSUtils;
import com.major.config.OSSConfig;
import com.major.entity.ImgLog;
import com.major.mapper.ImgLogMapper;
import com.major.service.IImgLogService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片记录表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-13
 */
@Service
public class ImgLogServiceImpl extends ServiceImpl<ImgLogMapper, ImgLog> implements IImgLogService {

    @Autowired
    private OSSConfig ossConfig;

    @Override
    public boolean addImgLogList(List<String>imgUrls,String fromTable,Long fromId,String fromField,Long operId) {
        List<ImgLog> imgLogList=new ArrayList<>();
        for(String img:imgUrls){
            ImgLog imgLog=new ImgLog();
            imgLog.setOperId(operId);
            imgLog.setOperType(Constants.TARGET_TYPE_BUSINESS);
            imgLog.setFromTable(fromTable);
            imgLog.setFromId(fromId.intValue());
            imgLog.setFromField(fromField);
            imgLog.setImgUrl(img);
            imgLog.setImgSize(ImgUtils.getImageInfoByUrl(img));
            imgLog.setStatus(Constants.STATUS_NORMAL);
            imgLogList.add(imgLog);
        }
        return super.insertBatch(imgLogList);
    }

    @Override

    public boolean addImgLog(String imgUrl,String fromTable,Long fromId,String fromField,Long operId) {
        if(StringUtils.isEmpty(imgUrl)){
            return true;
        }
        ImgLog imgLog=new ImgLog();
        imgLog.setOperId(operId);
        imgLog.setOperType(Constants.TARGET_TYPE_BUSINESS);
        imgLog.setFromTable(fromTable);
        imgLog.setFromId(fromId.intValue());
        imgLog.setFromField(fromField);
        imgLog.setImgUrl(imgUrl);
        imgLog.setImgSize(ImgUtils.getImageInfoByUrl(imgUrl));
        imgLog.setStatus(Constants.STATUS_NORMAL);
        return super.insert(imgLog);
    }

    @Override
    public boolean updateImgLogsByTableAndDelect(String imgUrls,String fromTable,Long fromId,String reqImgUrls,String fromField) {
        //防止修改时，有些时候没有修改图片，而只是修改其它选项，所以把修改时传的图片地址和数据库中查的图片地址单条对比，只有不相等的情况删除oss
        if (StringUtils.isNotBlank(imgUrls) && StringUtils.isNotBlank(reqImgUrls)) {
            String imgs[]=imgUrls.split(",");
            String reqImgs[]=reqImgUrls.split(",");
            List<String> fileUrls=new ArrayList<>();
            //已新传入的地址为主，对比数据库中的地址
            List<String> list = compare(reqImgs,imgs);
            for (String String : list) {
                fileUrls.add(String);
            }
            List<ImgLog> imgLogList=baseMapper.selectImgLogsByTable(fromTable,fromId,fromField);
            //根据查找出来的数据删除
            if(imgLogList!=null && imgLogList.size()>0){
                for(ImgLog imgLog : imgLogList){
                    //对比业务表中的地址与记录表中的地址，只有相等的时候会操作
                        if(fileUrls.contains(imgLog.getImgUrl())){
                            //删除原oss对应数据
                            OSSUtils.deleteFile(fileUrls,ossConfig);
                            //把之前的数据修改成伪删除
                            imgLog.setStatus(Constants.STATUS_DELETE);
                            super.updateById(imgLog);
                            return true;
                        }
                }
            }
        }
        return true;
    }

    @Override
    public boolean deleteImgLogsByTable(String imgUrls,String fromTable,Long fromId,Long operId,String fromField) {
        if (StringUtils.isNotBlank(imgUrls)) {
            String imgs[]=imgUrls.split(",");
            List<String> fileUrls=new ArrayList<>();
            for(String s :imgs){
                        fileUrls.add(s);
            }
            //删除oss对应数据
            OSSUtils.deleteFile(fileUrls,ossConfig);
            List<ImgLog> imgLogList=baseMapper.selectImgLogsByTable(fromTable,fromId,fromField);
            //根据查找出来的数据删除
            if(imgLogList!=null && imgLogList.size()>0){
                for(ImgLog imgLog : imgLogList){
                            imgLog.setOperId(operId);
                            imgLog.setStatus(Constants.STATUS_DELETE);
                            super.updateById(imgLog);
                }
            }
        }
        return true;
    }

    @Override
    public Page<Map<String, Object>> selectImgLogPage(Page<Map<String, Object>> page,String fromTable,String fromField,Integer operType) {
        return  page.setRecords(baseMapper.selectImgLogPage(page,fromTable,fromField,operType));
    }

    public static <T> List<T> compare(T[] t1, T[] t2) {
        //将t1数组转成list数组
        List<T> list1 = Arrays.asList(t1);
        //用来存放2个数组中不相同的元素
        List<T> list2 = new ArrayList<T>();
        for (T t : t2) {
            if (!list1.contains(t)) {
                list2.add(t);
            }
        }
        return list2;
    }

    @Override
    public boolean updateImgLogsByTable(String imgUrls,String fromTable,Long fromId,String reqImgUrls,String fromField) {
        if (StringUtils.isNotBlank(imgUrls) && StringUtils.isNotBlank(reqImgUrls)) {
            String [] imgs=imgUrls.split(",");
            String [] reqImgs =reqImgUrls.split(",");
            List<String> fileUrls=new ArrayList<>();
            //已新传入的地址为主，对比数据库中的地址
            List<String> list = compare(reqImgs,imgs);
            for (String String : list) {
                fileUrls.add(String);
            }
            List<ImgLog> imgLogList=baseMapper.selectImgLogsByTable(fromTable,fromId,fromField);
            if(imgLogList!=null && imgLogList.size()>0){
                for(ImgLog imgLog : imgLogList){
                   //删除不一致的数据
                    OSSUtils.deleteFile(fileUrls,ossConfig);
                    imgLog.setImgUrl(reqImgUrls);
                    super.updateById(imgLog);
                    return true;
                }
            }
        }
        return true;
    }

}
