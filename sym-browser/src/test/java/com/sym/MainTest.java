package com.sym;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by 沈燕明 on 2019/6/19.
 */
@SpringBootTest(classes = BrowserApplication.class)
@RunWith(SpringRunner.class)
public class MainTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testOne(){
        System.out.println(dataSource);
        JdbcTemplate t = new JdbcTemplate(dataSource);
        List<Map<String, Object>> maps = t.queryForList("select * from t_student");
        System.out.println(maps);
    }
}
