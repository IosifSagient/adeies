package com.adeies.adeies.enterprise.tests;

import com.adeies.adeies.enterprise.entities.DaysOffDefinition;
import com.adeies.adeies.enterprise.repository.DaysOffDefinitionRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DaysOffDefinitionRepoTest {

    @Autowired
    private DaysOffDefinitionRepo daysOffDefinitionRepo;

    @Test
    public void dayOffDefinitionRepoTest() {
        DaysOffDefinition daysOffDefinition = new DaysOffDefinition();
        daysOffDefinition.setId(1L);
        daysOffDefinition.setDescription("desc");
        daysOffDefinition.setWording("words");

        daysOffDefinitionRepo.save(daysOffDefinition);

        DaysOffDefinition daysOffDefinitionServiceFound = daysOffDefinitionRepo.findById(1L).get();

        assertThat(daysOffDefinitionServiceFound).isNotNull();
        assertThat(daysOffDefinitionServiceFound.getDescription().equals("desc"));

    }

}
