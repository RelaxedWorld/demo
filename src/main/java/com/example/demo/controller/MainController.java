package com.example.demo.controller;

import com.example.demo.dao.model.User;
import com.example.demo.domains.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {
    @RequestMapping("pub")
    public Object showPubArticleList() {
        User user = new User();
        user.setUserName("hahah");
        Map resultMap = new HashMap();
        resultMap.put("user", user);
        List userlist = new ArrayList();
        userlist.add(user);
        userlist.add(user);
        resultMap.put("userList", userlist);
        return Result.getSuccessResult(resultMap);
    }
}
