package sym.test;

import com.sym.entity.SymSecurityProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 沈燕明 on 2019/6/4 9:21.
 */
@SpringBootTest(classes = MainTest.class)
@RunWith(SpringRunner.class)
@EnableConfigurationProperties(SymSecurityProperties.class)
public class MainTest {

    @Autowired
    private SymSecurityProperties symSecurityProperties;
    
    @Test
    public void test(){
        System.out.println(symSecurityProperties);
    }

}
