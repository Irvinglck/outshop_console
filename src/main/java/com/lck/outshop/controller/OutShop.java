package com.lck.outshop.controller;

import com.alibaba.fastjson.JSON;
import com.lck.outshop.entity.User;
import com.lck.starter.SendMsgService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api")
public class OutShop {
    private SendMsgService sendMsgService;

    @Autowired
    public OutShop(SendMsgService sendMsgService) {
        this.sendMsgService = sendMsgService;
    }

    @RequestMapping(path = "/index_category", method = RequestMethod.GET)
    public ResultMessage index_category() {
        Object result = null;
        try {
            String str = IOUtils.toString(new ClassPathResource("file_out_shop/index_category.json").getInputStream(), "utf-8");
            result = JSON.parse(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultMessage.getDefaultResultMessage(0, "", result);
//        return str;
    }

    @RequestMapping(path = "/position", method = RequestMethod.GET)
    public ResultMessage getGeohash(
            @RequestParam(name = "geohash") String geohash
    ) throws IOException {
        String str = IOUtils.toString(new ClassPathResource("file_out_shop/position.json").getInputStream(), "utf-8");
        Object result = JSON.parse(str);
        return ResultMessage.getDefaultResultMessage(0, "", result);
    }

    @RequestMapping(path = "/shops", method = RequestMethod.GET)
    public ResultMessage getShops(
            @RequestParam(name = "longitude") String longitude,
            @RequestParam(name = "latitude") String latitude
    ) throws IOException {
        String str = IOUtils.toString(new ClassPathResource("file_out_shop/shops.json").getInputStream(), "utf-8");
        Object result = JSON.parse(str);
        return ResultMessage.getDefaultResultMessage(0, "", result);
    }

    @RequestMapping(path = "/getMsg", method = RequestMethod.GET)
    public ResultMessage getMsg(
            @RequestParam(name = "phone") String phone
    ) {
        String code = sendMsgService.getCode(phone);
        return ResultMessage.getDefaultResultMessage(0, JSON.parse(code));
    }

    /**
     * 验证码登陆
     */
    @RequestMapping(path = "/login_sms", method = RequestMethod.POST)
    public ResultMessage loginByCode(
            @RequestBody User user,
            HttpServletRequest request
    ) {
        if ("123456".equals(user.getCode())) {
            request.getSession().setAttribute("user", user);
            return ResultMessage.getDefaultResultMessage(0, "登陆成功", user);
        }
        return ResultMessage.getDefaultResultMessage(-1, "验证码错误");
    }

    /**
     * 密码登陆
     */
    @RequestMapping(path = "/login_pwd", method = RequestMethod.POST)
    public ResultMessage reqPwdLogin(
            @RequestBody User user,
            HttpServletRequest request
    ) {
        if ("123456".equals(user.getPwd())) {
            request.getSession().setAttribute("user", user);
            return ResultMessage.getDefaultResultMessage(0, "登陆成功", user);
        }
        return ResultMessage.getDefaultResultMessage(-1, "账号或者密码错误");
    }

    /**
     * 获取session中用户信息
     */
    @GetMapping(path = "/user_info")
    public ResultMessage getUserInfo(HttpServletRequest request) {
        if(request.getSession().getAttribute("user")!=null){
            return ResultMessage.getDefaultResultMessage(0,"用户信息",request.getSession().getAttribute("user"));
        }
        return ResultMessage.getDefaultResultMessage(-1,"暂无该用户");
    }

    /**
     * 重置session中用户信息
     */
    @GetMapping(path = "/logout")
    public ResultMessage logout(HttpServletRequest request) {
        if(request.getSession().getAttribute("user")!=null){
            request.getSession().setAttribute("user",null);
            return ResultMessage.getDefaultResultMessage(0,"已经将用户信息重置");
        }
        return ResultMessage.getDefaultResultMessage(-1,"暂无该用户");
    }

}
