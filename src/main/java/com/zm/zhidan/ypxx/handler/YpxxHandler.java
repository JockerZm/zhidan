package com.zm.zhidan.ypxx.handler;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.zm.zhidan.ypxx.dao.YpxxRepository;
import com.zm.zhidan.ypxx.domain.Ypxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class YpxxHandler {

    private final YpxxRepository ypxxRepository;

    @Autowired
    public YpxxHandler(YpxxRepository ypxxRepository) {
        this.ypxxRepository = ypxxRepository;
    }

    public Mono<Ypxx> addOrUpadteYpxx(Ypxx ypxx) {
        Mono<Boolean> booleanMono = ypxxRepository.existsById(ypxx.getId());
        if (ypxx.getId() == 0) {
            ypxx.setId(new Date().getTime());
        } else {
            ypxxRepository.deleteById(ypxx.getId());
        }
        return ypxxRepository.save(ypxx);
    }

    public Mono<Ypxx> updateYpxx(Ypxx ypxx) {
//        ypxxRepository.findById(ypxx.getId());
        ypxxRepository.findById(ypxx.getId());
        return ypxxRepository.save(ypxx);
    }

    public Flux<Ypxx> queryYpxxList(Integer pageNo, Integer pageCount) {
        long startNo = (pageNo - 1) * pageCount;
        //按照更新时间排序
        Sort orders = new Sort(Sort.Direction.DESC, "updateTime");
        return ypxxRepository.findAll(orders).skip(startNo).limitRequest(pageCount);
    }

    public Mono<Void> deleteYpxx(List<Ypxx> list) {
//        for (Ypxx ypxx : list) {
//            Mono<Void> voidMono = ypxxRepository.deleteById(ypxx.getId());
//        }
        return ypxxRepository.deleteAll(list);
    }


    public Flux<Ypxx> queryYpxxByBianMa(long id) {
        return ypxxRepository.findByBianmaIsLike(id);
    }


}
