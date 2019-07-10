package com.zm.zhidan.ypxx.dao;

import com.zm.zhidan.ypxx.domain.Ypxx;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface YpxxRepository extends ReactiveMongoRepository<Ypxx, Long> {

    Flux<Ypxx> findByBianmaIsLike(Long bianma);

    Mono<Void> deleteById(String id);

    Mono<Boolean> existsById(String id);

}
