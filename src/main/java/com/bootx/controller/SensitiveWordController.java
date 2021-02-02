package com.bootx.controller;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.SensitiveWord;
import com.bootx.service.SensitiveWordService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author black
 */
@RestController("pddSensitiveWordController")
@RequestMapping("/pdd/sensitive_word")
@CrossOrigin
public class SensitiveWordController extends BaseController {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @PostMapping("/list")
    @JsonView(BaseEntity.PageView.class)
    public Result list(Pageable pageable){
        return Result.success(sensitiveWordService.findPage(pageable));
    }

    @PostMapping("/save")
    public Result save(SensitiveWord sensitiveWord){
        String[] words = sensitiveWord.getWord().split("；");
        for (String word:words) {
            if(sensitiveWordService.wordExist(word)){
                continue;
            }

            SensitiveWord sensitiveWord1 = new SensitiveWord();
            sensitiveWord1.setWord(word);
            sensitiveWord1.setIsEnabled(true);
            sensitiveWordService.save(sensitiveWord1);
        }
        return Result.success("ok");
    }
}
