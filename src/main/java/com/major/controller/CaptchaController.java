package com.major.controller;

import com.major.common.enums.StatusResultEnum;
import com.major.model.response.BaseResponse;
import com.major.model.response.ResultResponse;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 图片验证码（支持算术形式）
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 验证码生成
     */
    @RequestMapping(value = "/captchaImage")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type)) {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(type)) {
                capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }

            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Desc 验证码验证
     * @param kaptchaCode
     * @param request
     * @return
     */
    @RequestMapping("/captchaVerify")
    @ResponseBody
    public BaseResponse captchaVerify(String kaptchaCode, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            String code = String.valueOf(obj != null ? obj : "");

            if (StringUtils.isNotBlank(kaptchaCode)
                    && kaptchaCode.equalsIgnoreCase(code)) {
                return ResultResponse.success(StatusResultEnum.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultResponse.error(StatusResultEnum.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return ResultResponse.error(StatusResultEnum.CHECK_CODE_ERROR);
    }

}