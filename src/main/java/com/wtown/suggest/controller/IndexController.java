package com.wtown.suggest.controller;

import com.wtown.suggest.service.SuggestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    public final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SuggestService service;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public @ResponseBody
    String uploads(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String description = request.getParameter("desc");
        List<MultipartFile> imgs = multipartRequest.getFiles("file[]");
        if (description.isEmpty()) {
            return "请输入描述";
        }
        if (imgs == null || imgs.size() <= 0) {
            return "请上传图片";
        }
        service.submitSuggest(description, imgs);
        return "提交成功！";
    }
}
