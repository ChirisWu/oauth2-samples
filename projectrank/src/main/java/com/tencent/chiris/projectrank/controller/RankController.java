package com.tencent.chiris.projectrank.controller;


import com.tencent.chiris.projectrank.service.ProcessGroupRankInMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RankController {

    @Resource
    private ProcessGroupRankInMemory rankService;
    @GetMapping("ranks/deleteYear")
    public String delete(){
        rankService.deleteRanksYearAgo();
        return "deleting...";
    }
}
