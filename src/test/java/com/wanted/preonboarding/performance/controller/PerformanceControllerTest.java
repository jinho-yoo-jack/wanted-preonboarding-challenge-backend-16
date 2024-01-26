package com.wanted.preonboarding.performance.controller;

import com.wanted.preonboarding.performance.dto.PerformanceSearchParam;
import com.wanted.preonboarding.performance.service.PerformanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("view 컨트롤러 -  공연 & 전시")
@WebMvcTest(PerformanceController.class)
public class PerformanceControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private PerformanceService performanceService;

    @DisplayName("[view][GET] 공연 & 전시 목록 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingPerformancePage_thenReturnsPerformancePage() throws Exception {
        given(performanceService.getAllPerformanceInfoList(any(PerformanceSearchParam.class), any(Pageable.class))).willReturn(Page.empty());
        mvc.perform(get("/performance")).andExpect(status().isOk());
    }

}
