package ru.denusariy.plumbing.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.denusariy.plumbing.domain.dto.request.PlumberRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.PlumberResponseDTO;
import ru.denusariy.plumbing.domain.entity.Plumber;
import ru.denusariy.plumbing.exception.PlumberNotFoundException;
import ru.denusariy.plumbing.repository.PlumberRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlumberServiceImplTest {
    @InjectMocks
    private PlumberServiceImpl plumberService;
    @Mock
    private PlumberRepository plumberRepositoryMock;
    @Spy
    private ModelMapper modelMapperMock;

    @Nested
    class ConvertToEntityTest {
        @Test
        void should_ConvertPlumberRequestDTOToEntity() {
            //given
            PlumberRequestDTO requestDTO = new PlumberRequestDTO("Petrovich");
            Plumber expected = new Plumber(0, "Petrovich", null);
            //when
            Plumber actual = plumberService.convertToEntity(requestDTO);
            //then
            assertAll(
                    () -> assertEquals(expected.getId(), actual.getId()),
                    () -> assertEquals(expected.getName(), actual.getName()),
                    () -> assertEquals(expected.getHouses(), actual.getHouses())
            );

        }

    }

    @Nested
    class ConvertToDTOTest {
        @Test
        void should_ConvertPlumberToResponseDTO() {
            //given
            Plumber plumber = new Plumber(0, "Petrovich", null);
            PlumberResponseDTO expected = new PlumberResponseDTO("Petrovich", null);
            //when
            PlumberResponseDTO actual = plumberService.convertToDTO(plumber);
            //then
            assertAll(
                    () -> assertEquals(expected.getName(), actual.getName()),
                    () -> assertEquals(expected.getHouses(), actual.getHouses())
            );

        }

    }

    @Nested
    class FindOneTest {
        @Test
        void should_FindPlumberAndReturnResponseDTO_When_IdIsPresent() {
            //given
            Plumber plumber = new Plumber(1, "Petrovich", null);
            PlumberResponseDTO expected = new PlumberResponseDTO("Petrovich", null);
            when(plumberRepositoryMock.findById(1)).thenReturn(Optional.of(plumber));
            //when
            PlumberResponseDTO actual = plumberService.findOne(1);
            //then
            assertAll(
                    () -> assertEquals(expected.getName(), actual.getName()),
                    () -> assertEquals(expected.getHouses(), actual.getHouses())
            );
        }

        @Test
        void should_ThrowPlumberNotFoundException_When_IdIsNotPresent() {
            //given
            when(plumberRepositoryMock.findById(anyInt())).thenThrow(PlumberNotFoundException.class);
            //when
            Executable executable = () -> plumberService.findOne(anyInt());
            //then
            assertThrows(PlumberNotFoundException.class, executable);
        }
    }

    @Nested
    class SaveTest {
        @Test
        void should_SaveNewPlumberAndReturnResponseDTO() {
            //given
            PlumberRequestDTO requestDTO = new PlumberRequestDTO("Petrovich");
            Plumber plumber = new Plumber(0, "Petrovich", null);
            PlumberResponseDTO expected = new PlumberResponseDTO("Petrovich", null);
            when(plumberRepositoryMock.save(plumber)).thenReturn(plumber);
            //when
            PlumberResponseDTO actual = plumberService.save(requestDTO);
            //then
            assertAll(
                    () -> assertEquals(expected.getName(), actual.getName()),
                    () -> assertEquals(expected.getHouses(), actual.getHouses()),
                    () -> verify(plumberRepositoryMock).save(plumber)
            );
        }
    }

    @Nested
    class DeleteTest {
        @Test
        void should_DeletePlumberAndReturnName_When_IdIsPresent() {
            //given
            Plumber plumber = new Plumber(1, "Petrovich", null);
            String expected = "Petrovich";
            when(plumberRepositoryMock.findById(1)).thenReturn(Optional.of(plumber));
            //when
            String actual = plumberService.delete(1);
            //then
            assertAll(
                    () -> assertEquals(expected, actual),
                    () -> verify(plumberRepositoryMock).delete(plumber)
            );
        }

        @Test
        void should_ThrowPlumberNotFoundException_When_IdIsNotPresent() {
            //given
            when(plumberRepositoryMock.findById(anyInt())).thenThrow(PlumberNotFoundException.class);
            //when
            Executable executable = () -> plumberService.delete(anyInt());
            //then
            assertAll(
                    () -> assertThrows(PlumberNotFoundException.class, executable),
                    () -> verify(plumberRepositoryMock, never()).delete(any())
            );
        }
    }

}