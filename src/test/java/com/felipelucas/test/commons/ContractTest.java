package com.felipelucas.test.commons;

import com.felipelucas.commons.api.BaseRestController;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public abstract class ContractTest {

    protected MockMvc mockMvc;

    public void setUp(BaseRestController controller){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .build();
    }
}