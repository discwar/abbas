package com.major.common.util;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

/**
 * <p>Title: 二维码生成工具         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/29 19:24      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class QrCodeUtils {

    private static final String CHARSET = "UTF-8";
    private static final String FORMAT_NAME = "JPG";

    /**
     * 用于设置图案的颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 用于背景色
     */
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 二维码尺寸
     */
    private static final int QR_CODE_SIZE = 300;
    /**
     * LOGO宽度
     */
    private static final int WIDTH = 60;
    /**
     * LOGO高度
     */
    private static final int HEIGHT = 60;

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));
            }
        }

        return image;
    }

    /**
     * 生成二维码
     * @param content 源内容
     * @param imgPath 生成二维码保存的路径
     * @param needCompress 是否要压缩
     * @return 返回二维码图片
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
        //hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
        // 设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, hints);
        BufferedImage image = QrCodeUtils.toBufferedImage(bitMatrix);

        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }

        // 插入图片
        QrCodeUtils.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 在生成的二维码中插入图片
     * @param matrixImage
     * @param imgPath
     * @param needCompress
     * @throws Exception
     */
    private static void insertImage(BufferedImage matrixImage, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(imgPath + " 该文件不存在！");
            return;
        }

        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);

        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }

            if (height > HEIGHT) {
                height = HEIGHT;
            }

            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }

        // 插入LOGO
        Graphics2D graph = matrixImage.createGraphics();
        int x = (QR_CODE_SIZE - width) / 2;
        int y = (QR_CODE_SIZE - height) / 2;

        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成带logo二维码，并保存到磁盘
     * @param content 二维码存储的信息
     * @param imgPath 要插入的logo图片文件路径
     * @param destPath 二维码图片文件保存路径
     * @param needCompress 是否需要压缩
     * @throws Exception
     */
    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        // 生成随机文件名
        String file = new Random().nextInt(99999999) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
    }

    /**
     * 生成到随机目录下
     * @param content
     * @param imgPath
     * @param needCompress
     * @return
     * @throws Exception
     */
    public static String encodeRandom(String content, String imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, imgPath, needCompress);
        String imgName =new Random().nextInt(99999999)+"";
        File tempFile = File.createTempFile(imgName, "." + "jpg");
        tempFile.deleteOnExit();
        ImageIO.write(image, FORMAT_NAME, new File(tempFile.getPath()));
        return tempFile.getPath();
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir。(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encode(String content, String imgPath, String destPath) throws Exception {
        QrCodeUtils.encode(content, imgPath, destPath, false);
    }

    public static void encode(String content, String destPath, boolean needCompress) throws Exception {
        QrCodeUtils.encode(content, null, destPath, needCompress);
    }

    public static void encode(String content, String destPath) throws Exception {
        QrCodeUtils.encode(content, null, destPath, false);
    }

    public static void encode(String content, String imgPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static void encode(String content, OutputStream output) throws Exception {
        QrCodeUtils.encode(content, null, output, false);
    }

    /**
     * 从二维码中，解析数据
     * @param file 二维码图片文件
     * @return 返回从二维码中解析到的数据值
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    public static String decode(String path) throws Exception {
        return QrCodeUtils.decode(new File(path));
    }

    public static void main(String[] args) throws Exception {
        // 生成带logo的二维码
        String text = "生成带logo的二维码";
        OSSUtils ossUtils = new OSSUtils();
        File file =  ossUtils.download("https://missfreshfruits.oss-cn-hangzhou.aliyuncs.com/img/QR_logo/QR_code_logo.jpg");
        String url = file.getPath();
        QrCodeUtils.encode(text, file.getPath(), "d:/aiguo", true);

//        // 生成不带logo的二维码
//        String content = "http://www.baidu.com";
//        QrCodeUtils.encode(content,"","d:/Lienson",true);

//        // 指定二维码图片，解析返回数据
//        System.out.println(QrCodeUtils.decode("D:/Lienson/3948141.jpg"));
    }

}
