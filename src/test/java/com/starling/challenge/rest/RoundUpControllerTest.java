package com.starling.challenge.rest;

import com.starling.challenge.service.RoundUpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RoundUpController.class)
public class RoundUpControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoundUpService roundUpService;

    @Test
    public void executeRoundUp() throws Exception {
        mockMvc.perform(get("/starling/roundup/"))
                .andExpect(status().isOk());

        verify(roundUpService, times(1)).roundUp();
    }
}
