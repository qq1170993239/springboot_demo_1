package com.lix.study.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lix on 2018/11/5/005.
 */
@Controller
@RequestMapping("/test")
public class IndexColler {

    @RequestMapping("/index")// http://localhost:8002/test/index
    public String index(Map<String, Object> data){
        data.put("name", "zhangsan");
        data.put("age", 23);
        data.put("sex", 1);
        List<Map<String, Object>> friends = new ArrayList<>();
        Map<String, Object> friend = new HashMap<>();
        friend.put("name", "xiaoming");
        friend.put("age", 22);
        friends.add(friend);
        data.put("friends", friends);
        return "index";
    }

}
