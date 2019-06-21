package com.zm.zhidan.ypxx.dao;

import com.zm.zhidan.ypxx.entry.Ypxx;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class YpxxRepository implements ReactiveMongoRepository {

    /*private ConcurrentMap<Long, Ypxx> repository = new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator = new AtomicLong(0);

    public Long save(Ypxx ypxx) {
        Long id = idGenerator.incrementAndGet();
        ypxx.setId(id);
        repository.put(id, ypxx);
        return id;
    }*/


    @Override
    public Mono insert(Object o) {
        return null;
    }

    @Override
    public Flux insert(Iterable iterable) {
        return null;
    }

    @Override
    public Flux insert(Publisher publisher) {
        return null;
    }

    @Override
    public Flux findAll(Example example) {
        return null;
    }

    @Override
    public Flux findAll(Example example, Sort sort) {
        return null;
    }

    @Override
    public Mono findOne(Example example) {
        return null;
    }

    @Override
    public Mono<Long> count(Example example) {
        return null;
    }

    @Override
    public Mono<Boolean> exists(Example example) {
        return null;
    }

    @Override
    public Flux findAll(Sort sort) {
        return null;
    }

    @Override
    public Mono save(Object o) {
        return null;
    }

    @Override
    public Flux saveAll(Iterable iterable) {
        return null;
    }

    @Override
    public Flux saveAll(Publisher publisher) {
        return null;
    }

    @Override
    public Mono findById(Object o) {
        return null;
    }

    @Override
    public Mono findById(Publisher publisher) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Object o) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher publisher) {
        return null;
    }

    @Override
    public Flux findAll() {
        return null;
    }

    @Override
    public Flux findAllById(Iterable iterable) {
        return null;
    }

    @Override
    public Flux findAllById(Publisher publisher) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Object o) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher publisher) {
        return null;
    }

    @Override
    public Mono<Void> delete(Object o) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable iterable) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher publisher) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
