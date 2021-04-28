package com.tencent.chiris.projectrank.controller;

import com.tencent.chiris.projectrank.annotations.ValidateDate;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;

@RestController
public class TestController {

    @GetMapping("/ok")
    @ValidateDate
    public String hello() {
        return "ok";
    }

    @GetMapping("/val")
    @ValidateDate
    public String valid(@RequestParam(name = "org_id") Integer orgId, @RequestParam(name = "limit") int limit,
                        @RequestParam(name = "begin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin,
                        @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end){

        return "val";
    }

    @GetMapping("/set")
    public Set<Integer> setArgs(@RequestParam(name = "set") Set<Integer> set){
         return set;
    }
}
