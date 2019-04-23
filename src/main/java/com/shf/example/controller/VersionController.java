package com.shf.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @Author: songhaifeng
 * @Date: 2019/4/22 13:07
 */
@Slf4j
@RestController
public class VersionController {

    @Autowired(required = false)
    private BuildProperties buildProperties;

    @GetMapping("/version")
    public String version() {
        if (null == buildProperties) {
            return "UnKnow";
        }
        return buildProperties.getVersion();
    }

}
