package com.wanted.preonboarding;

import com.wanted.preonboarding.performance.framwork.presentation.QueryController;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest(QueryController.class)
@ActiveProfiles(profiles = "test")
public class ControllerTest {

}
