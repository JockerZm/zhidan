package com.zm.zhidan.ypxx.handler;

import com.zm.zhidan.ypxx.dao.YpxxRepository;
import com.zm.zhidan.ypxx.entry.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class YpxxHandler {

    private final YpxxRepository ypxxRepository;

    @Autowired
    public YpxxHandler(YpxxRepository ypxxRepository) {
        this.ypxxRepository = ypxxRepository;
    }

    public Mono<Ypxx> saveOrUpdateYpxx(Ypxx ypxx) {
        return ypxxRepository.save(ypxx);
    }

/*    public Mono<Ypxx> deleteYpxx(Ypxx ypxx) {
        return Mono.create(ypxxMonoSink -> ypxxMonoSink.success(ypxxRepository.save(ypxx)));
    }*/


}
