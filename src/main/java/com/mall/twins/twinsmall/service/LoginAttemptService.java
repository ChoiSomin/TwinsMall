package com.mall.twins.twinsmall.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS_COUNT = 5; //로그인 실패 횟수

    private LoadingCache<String, Integer> loginAttemptCache; //사용자 mid가 키이고 로그인 실패 횟수가 값인 캐시를 정의

    private LoginAttemptService(){ //생성하고 1일이 지나면 만료되는 캐시 생성
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void loginSuccess(String mid){
        loginAttemptCache.invalidate(mid);
    }

    public void loginFiled(String mid){ //로그인에 실패하면 캐시 해당 사용자의 로그인 실패 횟수를 증가시키고 다시 캐시에 저장
        int failedAttemptCounter = 0;

        try{
            failedAttemptCounter = loginAttemptCache.get(mid);

        } catch (ExecutionException e){
            failedAttemptCounter = 0;
        }

        failedAttemptCounter ++;
        loginAttemptCache.put(mid, failedAttemptCounter);
    }//loginFiled

    public boolean isBlocked(String mid){ //정해진 로그인 실패 횟수를 초과하면 isBlocked에 true가 할당되면 해당 계정으로 로그인 할 수 없음
        try{
            return loginAttemptCache.get(mid) >= MAX_ATTEMPTS_COUNT;
        } catch (ExecutionException e){
            return false;
        }
    }//isBlocked



}
