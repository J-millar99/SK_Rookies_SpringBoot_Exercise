package com.rookies3.myspringbootlab.runner;

import com.rookies3.myspringbootlab.properties.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {

    @Autowired
    private MyPropProperties myPropProperties;

    @Value("${myprop.username}")
    private String userName;
    @Value("${myprop.port}")
    private int port;


//    @Override
//    // 테스트 1 : @Vaule 어노테이션으로 가져오기
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("userName = " + userName);
//        System.out.println("port = " + port);
//    }

//    @Override
//    // 테스트 2 : myPropProperties에서 getter로 가져오기
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("userName: " + myPropProperties.getUserName());
//        System.out.println("port: " + myPropProperties.getPort());
//    }

    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);
    @Override
    // 테스트 3 : System.out.println을 logger 메서드로 변경하기
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("userName: " + myPropProperties.getUserName());
        logger.info("port: " + myPropProperties.getPort());
    }
}