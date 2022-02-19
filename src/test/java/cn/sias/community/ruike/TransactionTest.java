package cn.sias.community.ruike;


import cn.sias.community.ruike.service.serviceimpl.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class TransactionTest {

    @Autowired
    private AlphaService alphaService;


    @Test
    public  void testSave1(){
        Object savel = alphaService.savel();
        System.out.println(savel);
    }  @Test
    public  void testSave2(){
        Object savel = alphaService.save2();
        System.out.println(savel);
    }
}
