package ru.denusariy.plumbing.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.denusariy.plumbing.repository.PlumberRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-plumber-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-plumber-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PlumberControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PlumberRepository plumberRepository;

    @Nested
    class ShowPlumberIT {
        @Test
        void should_ReturnValidResponseEntity_When_PlumberIsPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.get("/api/v1/plumber/1");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "name": "Mario",
                                "houses":[]
                            }
                            """));
        }

        @Test
        void should_ReturnErrorResponseEntity_When_PlumberIsNotPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.get("/api/v1/plumber/200");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Сантехник с id 200 не найден"
                            }
                            """));
        }
    }

    @Nested
    class CreatePlumberIT {
        @Test
        void should_SaveNewPlumberAndReturnValidResponseEntity() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/plumber")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "name": "Luigi"
                            }
                            """);
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "name": "Luigi"
                            }
                            """));
            assertEquals(2, plumberRepository.count());
        }
    }

    @Nested
    class DeletePlumberIT {
        @Test
        void should_DeletePlumberAndReturnValidResponseEntity_When_PlumberIsPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/plumber/1");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                    .andExpect(content().string("Mario"));
            assertEquals(0, plumberRepository.count());
        }

        @Test
        void should_ReturnErrorResponseEntity_When_PlumberIsNotPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/plumber/200");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Сантехник с id 200 не найден"
                            }
                            """));
            assertEquals(1, plumberRepository.count());
        }
    }
}