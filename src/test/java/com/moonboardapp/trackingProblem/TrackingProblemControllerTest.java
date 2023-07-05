package com.moonboardapp.trackingProblem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonboardapp.problem.grades.Grade;
import com.moonboardapp.problem.model.Problem;
import com.moonboardapp.trackingProblems.controller.TrackingProblemController;
import com.moonboardapp.trackingProblems.dto.ShortTrackingProblemDto;
import com.moonboardapp.trackingProblems.dto.TrackingProblemDto;
import com.moonboardapp.trackingProblems.mapper.TrackingProblemMapper;
import com.moonboardapp.trackingProblems.model.TrackingProblem;
import com.moonboardapp.trackingProblems.service.TrackingProblemService;
import com.moonboardapp.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrackingProblemController.class)
public class TrackingProblemControllerTest {

    private TrackingProblemDto trackingProblemDto;
    private ShortTrackingProblemDto shortTrackingProblemDto;
    private Problem problem;
    private User user;
    private Grade grade;
    private List<String> hooksList = List.of("somth");

    @Autowired
    ObjectMapper mapper;

    @MockBean
    TrackingProblemService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        Timestamp publishedDate = Timestamp.valueOf("2023-06-19 12:34:56");

        grade = new Grade(1L, "6b");
        user = new User(1L, "UserName1", "UserEmail@mail.ru", "UserCity", 5L);
        problem = new Problem(1L, 1L, "SomeName", "SomeDisc",
                grade, 5, hooksList, "videoUrl", 4, "6b", publishedDate);
        trackingProblemDto = TrackingProblemMapper.TRACKING_PROBLEM_MAPPER.toTrackingProblemDto(
                new TrackingProblem(1L, problem, user, true,4L, 4, "videoUrl",
                        LocalDateTime.now()));
        shortTrackingProblemDto = TrackingProblemMapper.TRACKING_PROBLEM_MAPPER
                .toShortTrackingProblemDto(new TrackingProblem(1L, problem, user, true,
                        4L, 4L, "videoUrl", LocalDateTime.now()));
    }

    @Test
    void createTrackingProblem() throws Exception {
        when(service.create(any(ShortTrackingProblemDto.class))).thenReturn(trackingProblemDto);

        mockMvc.perform(post("/trackingProblem")
                .content(mapper.writeValueAsString(shortTrackingProblemDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(shortTrackingProblemDto)));
    }


    @Test
    void getTrackingProblemById() throws Exception {
        when(service.getById(anyLong())).thenReturn(trackingProblemDto);

        mockMvc.perform(get("/trackingProblem/1")
                .content(mapper.writeValueAsString(trackingProblemDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(trackingProblemDto)));
    }

    @Test
    void getTrackingProblemListByUserId() throws Exception {
        List<TrackingProblemDto> trackingProblemDtoList = List.of(trackingProblemDto);
        when(service.getByUserId(anyLong(), any())).thenReturn(trackingProblemDtoList);

        mockMvc.perform(get("/trackingProblem/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getTrackingProblemByUserId() throws Exception {
        when(service.getByUserId(anyLong(), any(Pageable.class))).thenReturn(List.of(trackingProblemDto));

        mockMvc.perform(get("/trackingProblem/1")
                .content(mapper.writeValueAsString(trackingProblemDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTrackingProblemById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/trackingProblem/1?userId=1"))
                .andExpect(status().isNoContent());
    }
}
