package com.adeies.adeies.enterprise.tests;

import com.adeies.adeies.enterprise.service.DaysOffDefinitionService;
import com.adeies.adeies.enterprise.service.DaysOffDefinitionServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaysOffDefinitionServiceImplTest {

    @Autowired
    private DaysOffDefinitionService daysOffDefinitionService;


    @TestConfiguration
    static class DaysOffImplTestContextConfiguration {
        @Bean
        public DaysOffDefinitionService daysOffService() {
            return new DaysOffDefinitionServiceImpl();
        }


    }
}
