package com.huiluczP.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiluczP.bean.AccountInfo;
import com.huiluczP.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    AccountMapper accountMapper;

    @RequestMapping("/getAccountInfo")
    @ResponseBody
    public String getAccountInfo() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AccountInfo> accounts  = accountMapper.getAccountInfoList();
        return objectMapper.writeValueAsString(accounts);
    }

    @RequestMapping("/setPermission")
    @ResponseBody
    public String setPermission(HttpServletRequest request, String permission){
        request.getSession().setAttribute("permission", permission);
        return permission;
    }
}
