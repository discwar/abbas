package com.major.common.util;


import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.config.OSSConfig;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
@Slf4j
public class OSSUtils {

    private final static String HTTPS_PREFIX = "https://";
    private final static String shopQRList = "shop/qr_code/";
    /**
     * 下载文件
     *
     * @param imageUrl
     * @return
     * @throws IOException
     */
    public static File download(String imageUrl) throws IOException {
        String suffix = imageUrl.substring(imageUrl.lastIndexOf(".") + 1);
        boolean flag = fileIsImg(suffix);
        if (!flag){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"图片格式错误");
        }
        String imgName =new Random().nextInt(999999999)+"";

        File tempFile = File.createTempFile(imgName, "." + suffix);
        tempFile.deleteOnExit();
        URL url = new URL(imageUrl);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(5 * 1000);
        InputStream is = con.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        OutputStream os = new FileOutputStream(tempFile);
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        is.close();
        os.close();
        return tempFile;

    }

    public static String uploadHeadImage(String imageUrl, OSSConfig ossConfig) {
        try {
            File file = download(imageUrl);
            String imgUrl = upload(file, ossConfig,null);
            file.delete();
            return imgUrl;
        } catch (Exception e) {
            throw new RuntimeException("上传失败");
        }
    }

    public static String upload(File file, OSSConfig ossConfig,Long shopId) {
        String bucketName = ossConfig.getBucketName();
        String endPoint = ossConfig.getEndPoint();
        String img = file.getName();
        String suffix = img.substring(img.lastIndexOf(".") + 1);
        OSSClient ossClient = new OSSClient(endPoint, ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        //上传图片
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String imgName = ossConfig.getAgManageFileList()+shopQRList +shopId+"/"+ folderNameYM()  + folderNameD() + shopId + "." + suffix;
        PutObjectResult putObject = ossClient.putObject(bucketName, imgName, inputStream);
        String url = ossConfig.getCdn()+File.separator+imgName;
        // 关闭OSSClient。
        ossClient.shutdown();
        return url;
    }

    /**
     * 判断文件后缀是否为图片
     */
    private static boolean fileIsImg(String fileEnd){
        String imgeArray [][] = {
                {"bmp", "0"}, {"gif", "1"},
                {"jpe", "2"}, {"jpeg", "3"},
                {"jpg", "4"}, {"png", "5"}
        };
        for (int i = 0; i <imgeArray.length ; i++) {
            if(StringUtils.isNotEmpty(fileEnd)
                    && imgeArray [i][0].equals(fileEnd.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    /**
     * 文件夹名称-年月
     *
     * @return
     */
    public static String folderNameYM() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String strDate = formatter.format(new Date());
        return strDate;
    }

    /**
     * 文件夹名称-天
     *
     * @return
     */
    public static String folderNameD() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        String strDate = formatter.format(new Date());
        return strDate;
    }

    /**
     *  只能删除没有中文的图片
     * @MethodName: batchDeleteFiles
     * @Description: 批量文件删除(较快)：适用于相同endPoint和BucketName
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     */
    public static int deleteFile(List<String> fileUrls,OSSConfig ossConfig){
        if(null==fileUrls || fileUrls.size()<=0){
            return 0;
        }
        int deleteCount = 0;
        String bucketName = ossConfig.getBucketName();
        List<String> fileNames = getFileName(fileUrls,ossConfig.getCdn());
        if(bucketName==null||fileNames.size()<=0){
            return 0;
        }
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(ossConfig.getEndPoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
            DeleteObjectsResult result = ossClient.deleteObjects(request);
            deleteCount = result.getDeletedObjects().size();
        } catch (OSSException oe) {
            log.info("OSS服务异常:", oe);
        } catch (ClientException ce) {
            log.info("OSS客户端异常:", ce);
        } catch (IllegalArgumentException e){
            log.info("图片地址不能为空"+fileUrls.toString());
        } finally {
            ossClient.shutdown();
        }
        return deleteCount;

    }
    /**
     *
     * @MethodName: getBucketName
     * @Description: 根据url获取bucketName
     * @param fileUrl 文件url
     * @return String bucketName
     */
    private static String getBucketName(String fileUrl){
        String http = "http://";
        String https = "https://";
        int httpIndex = fileUrl.indexOf(http);
        int httpsIndex = fileUrl.indexOf(https);
        int startIndex  = 0;
        if(httpIndex==-1){
            if(httpsIndex==-1){
                return null;
            }else{
                startIndex = httpsIndex+https.length();
            }
        }else{
            startIndex = httpIndex+http.length();
        }
        int endIndex = fileUrl.indexOf(".oss-");
        return fileUrl.substring(startIndex, endIndex);
    }

    /**
     *
     * @MethodName: getFileName
     * @Description: 根据url获取fileName
     * @param fileUrl 文件url
     * @return String fileName
     */
    private static String getFileName(String fileUrl,String cdn) {
        String str=cdn+"/";
        int beginIndex = fileUrl.indexOf(str);
        if (beginIndex == -1){
            return null;
        }
        return fileUrl.substring(beginIndex + str.length());
    }
    /**
     *
     * @MethodName: getFileName
     * @Description: 根据url获取fileNames集合
     * @param fileUrls 文件url
     * @return List<String>  fileName集合
     */
    private static List<String> getFileName(List<String> fileUrls,String cdn){
        List<String> names = new ArrayList<>();
        for (String url : fileUrls) {
            String name=getFileName(url,cdn);
            if(null==name){
                continue;
            }
            names.add(name);
        }
        return names;
    }

    public static void main(String[] args) throws IOException {
        OSSUtils ossUtils = new OSSUtils();
        ossUtils.download("https://missfreshfruits.oss-cn-hangzhou.aliyuncs.com/img/QR_logo/QR_code_logo.jpg");
    }
}
