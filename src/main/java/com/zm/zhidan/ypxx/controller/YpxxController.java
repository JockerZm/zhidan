package com.zm.zhidan.ypxx.controller;

import com.zm.zhidan.ypxx.entry.Ypxx;
import com.zm.zhidan.ypxx.handler.YpxxHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/ypxx")
public class YpxxController {

    @Autowired
    private YpxxHandler ypxxHandler;

    /**
     * 保存或修改药品信息
     * @param ypxx
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Mono<Ypxx> saveOrUpdateYpxx(@RequestBody Ypxx ypxx){
        return ypxxHandler.saveOrUpdateYpxx(ypxx);
    }

/*    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Mono<Long> deleteYpxx(@RequestBody Ypxx ypxx){
        return ypxxHandler.deleteYpxx(ypxx);
    }*/



}
