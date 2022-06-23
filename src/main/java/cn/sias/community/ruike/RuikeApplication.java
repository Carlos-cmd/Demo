package cn.sias.community.ruike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RuikeApplication {
    //管理bean的声明周期--初始化
    @PostConstruct
    public void init(){
        //解决netty启动冲突的问题
        // see Netty4Utiles.setAvailableProcessors()
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }


    public static void main(String[] args) {
        SpringApplication.run(RuikeApplication.class, args);
    }

}
