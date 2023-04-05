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
import ru.denusariy.plumbing.repository.HouseRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-house-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-house-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class HouseControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    HouseRepository houseRepository;

    @Nested
    class ShowHouseIT {
        @Test
        void should_ReturnValidResponseEntity_When_HouseIsPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.get("/api/v1/house/1");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "address": "Privet Drive, 4",
                                "plumber": null
                            }
                            """));
        }

        @Test
        void should_ReturnErrorResponseEntity_When_HouseIsNotPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.get("/api/v1/house/200");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Дом с id 200 не найден"
                            }
                            """));
        }
    }

    @Nested
    class CreateHouseIT {
        @Test
        void should_SaveNewHouseAndReturnValidResponseEntity() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/house")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                            {
                                "address": "Wall Street, 40, New York"
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
                                "address": "Wall Street, 40, New York"
                            }
                            """));
            assertEquals(7, houseRepository.count());
        }
    }

    @Nested
    class DeleteHouseIT {
        @Test
        void should_DeleteHouseAndReturnValidResponseEntity_When_HouseIsPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/house/1");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                    .andExpect(content().string("Privet Drive, 4"));
            assertEquals(5, houseRepository.count());
        }

        @Test
        void should_ReturnErrorResponseEntity_When_HouseIsNotPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.delete("/api/v1/house/200");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Дом с id 200 не найден"
                            }
                            """));
            assertEquals(6, houseRepository.count());
        }
    }

    @Nested
    class AssignIT {
        @Test
        void should_ReturnValidResponseEntity_When_HouseIsPresentAndPlumberIsPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/house/assign/1")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("plumber=Luigi");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                    .andExpect(content().string("Luigi"));
        }

        @Test
        void should_ReturnErrorResponseEntity_When_HouseIsNotPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/house/assign/200")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("plumber=Luigi");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Дом с id 200 не найден"
                            }
                            """));
        }

        @Test
        void should_ReturnErrorResponseEntity_When_PlumberIsNotPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/house/assign/1")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("plumber=Petrovich");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Сантехник с именем Petrovich не найден"
                            }
                            """));
        }

        @Test
        void should_ReturnErrorResponseEntity_When_ListOfHousesIsOverLimit() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/house/assign/1")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("plumber=Mario");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Сантехник может обслуживать не более 5 домов. Следует назначить другого специалиста."
                            }
                            """));
        }
    }

    @Nested
    class ReleaseIT {
        @Test
        void should_ReturnValidResponseEntity_When_HouseIsPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/house/release/2");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                    .andExpect(content().string("Mario"));
        }

        @Test
        void should_ReturnErrorResponseEntity_When_HouseIsNotPresent() throws Exception {
            //given
            var requestBuilder = MockMvcRequestBuilders.post("/api/v1/house/release/200");
            //when
            mockMvc.perform(requestBuilder)
                    //then
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("""
                            {
                                "message":"Дом с id 200 не найден"
                            }
                            """));
        }
    }

}