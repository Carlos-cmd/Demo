package cn.sias.community.ruike;

import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class LoggerTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);


    @Test
    public void testLogger(){
        System.out.println(logger.getName());

        logger.debug("debugLog");
        logger.info("info log");
        logger.warn("warn log");
        logger.error("error log");
    }
}
