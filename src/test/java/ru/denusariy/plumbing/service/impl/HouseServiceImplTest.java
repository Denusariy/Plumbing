package ru.denusariy.plumbing.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.denusariy.plumbing.domain.dto.request.HouseRequestDTO;
import ru.denusariy.plumbing.domain.dto.response.HouseResponseDTO;
import ru.denusariy.plumbing.domain.entity.House;
import ru.denusariy.plumbing.domain.entity.Plumber;
import ru.denusariy.plumbing.exception.HouseNotFoundException;
import ru.denusariy.plumbing.exception.OutOfHousesLimitException;
import ru.denusariy.plumbing.exception.PlumberNotFoundException;
import ru.denusariy.plumbing.repository.HouseRepository;
import ru.denusariy.plumbing.repository.PlumberRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {
    @InjectMocks
    private HouseServiceImpl houseService;
    @Mock
    private HouseRepository houseRepositoryMock;
    @Mock
    private PlumberRepository plumberRepositoryMock;
    @Spy
    private ModelMapper modelMapperMock;

    @Nested
    class ConvertToEntityTest {
        @Test
        void should_ConvertHouseRequestDTOToEntity() {
            //given
            HouseRequestDTO requestDTO = new HouseRequestDTO("221b, Baker street");
            House expected = new House(0, "221b, Baker street", null);
            //when
            House actual = houseService.convertToEntity(requestDTO);
            //then
            assertAll(
                    () -> assertEquals(expected.getId(), actual.getId()),
                    () -> assertEquals(expected.getAddress(), actual.getAddress()),
                    () -> assertEquals(expected.getPlumber(), actual.getPlumber())
            );
        }
    }

    @Nested
    class ConvertToDTOTest {
        @Test
        void should_ConvertHouseToResponseDTO() {
            //given
            House house = new House(0, "221b, Baker street", null);
            HouseResponseDTO expected = new HouseResponseDTO("221b, Baker street", null);
            //when
            HouseResponseDTO actual = houseService.convertToDTO(house);
            //then
            assertAll(
                    () -> assertEquals(expected.getAddress(), actual.getAddress()),
                    () -> assertEquals(expected.getPlumber(), actual.getPlumber())
            );
        }
    }

    @Nested
    class FindOneTest {
        @Test
        void should_FindHouseAndReturnResponseDTO_When_IdIsPresent() {
            //given
            House house = new House(1, "221b, Baker street", null);
            HouseResponseDTO expected = new HouseResponseDTO("221b, Baker street", null);
            when(houseRepositoryMock.findById(1)).thenReturn(Optional.of(house));
            //when
            HouseResponseDTO actual = houseService.findOne(1);
            //then
            assertAll(
                    () -> assertEquals(expected.getAddress(), actual.getAddress()),
                    () -> assertEquals(expected.getPlumber(), actual.getPlumber())
            );
        }

        @Test
        void should_ThrowHouseNotFoundException_When_IdIsNotPresent() {
            //given
            when(houseRepositoryMock.findById(anyInt())).thenThrow(HouseNotFoundException.class);
            //when
            Executable executable = () -> houseService.findOne(anyInt());
            //then
            assertThrows(HouseNotFoundException.class, executable);
        }
    }

    @Nested
    class SaveTest {
        @Test
        void should_SaveNewHouseAndReturnResponseDTO() {
            //given
            HouseRequestDTO requestDTO = new HouseRequestDTO("221b, Baker street");
            House house = new House(0, "221b, Baker street", null);
            HouseResponseDTO expected = new HouseResponseDTO("221b, Baker street", null);
            when(houseRepositoryMock.save(house)).thenReturn(house);
            //when
            HouseResponseDTO actual = houseService.save(requestDTO);
            //then
            assertAll(
                    () -> assertEquals(expected.getAddress(), actual.getAddress()),
                    () -> assertEquals(expected.getPlumber(), actual.getPlumber()),
                    () -> verify(houseRepositoryMock).save(house)
            );
        }
    }

    @Nested
    class DeleteTest {
        @Test
        void should_DeleteHouseAndReturnAddress_When_IdIsPresent() {
            //given
            House house = new House(1, "221b, Baker street", null);
            String expected = "221b, Baker street";
            when(houseRepositoryMock.findById(1)).thenReturn(Optional.of(house));
            //when
            String actual = houseService.delete(1);
            //then
            assertAll(
                    () -> assertEquals(expected, actual),
                    () -> verify(houseRepositoryMock).delete(house)
            );
        }

        @Test
        void should_ThrowHouseNotFoundException_When_IdIsNotPresent() {
            //given
            when(houseRepositoryMock.findById(anyInt())).thenThrow(HouseNotFoundException.class);
            //when
            Executable executable = () -> houseService.delete(anyInt());
            //then
            assertAll(
                    () -> assertThrows(HouseNotFoundException.class, executable),
                    () -> verify(houseRepositoryMock, never()).delete(any())
            );
        }
    }

    @Nested
    class AssignTest {
        private Plumber plumber;

        @BeforeEach
        void setup() {
            this.plumber = new Plumber(1, "Petrovich", new ArrayList<>());
        }

        @Test
        void should_AssignPlumberAndReturnName_When_IdIsPresentAndPlumberIsPresent() {
            //given
            House house = new House(1, "221b, Baker street", null);
            String expected = "Petrovich";
            when(plumberRepositoryMock.findByNameEquals("Petrovich")).thenReturn(Optional.of(plumber));
            when(houseRepositoryMock.findById(1)).thenReturn(Optional.of(house));
            //when
            String actual = houseService.assign(1, "Petrovich");
            //then
            assertAll(
                    () -> assertEquals(expected, actual),
                    () -> assertEquals(house.getPlumber(), plumber)
            );
        }

        @Test
        void should_ThrowHouseNotFoundException_When_IdIsNotPresent() {
            //given
            when(plumberRepositoryMock.findByNameEquals("Petrovich")).thenReturn(Optional.of(plumber));
            when(houseRepositoryMock.findById(anyInt())).thenThrow(HouseNotFoundException.class);
            //when
            Executable executable = () -> houseService.assign(anyInt(), "Petrovich");
            //then
            assertThrows(HouseNotFoundException.class, executable);
        }

        @Test
        void should_ThrowPlumberNotFoundException_When_NameIsNotPresent() {
            //given
            when(plumberRepositoryMock.findByNameEquals(anyString())).thenThrow(PlumberNotFoundException.class);
            //when
            Executable executable = () -> houseService.assign(1, anyString());
            //then
            assertThrows(PlumberNotFoundException.class, executable);
        }

        @Test
        void should_ThrowOutOfHousesLimitException_When_ListOfHousesSizeEqualsFive() {
            //given
            plumber.getHouses().add(new House(2, "address1", plumber));
            plumber.getHouses().add(new House(3, "address2", plumber));
            plumber.getHouses().add(new House(4, "address3", plumber));
            plumber.getHouses().add(new House(5, "address4", plumber));
            plumber.getHouses().add(new House(6, "address5", plumber));
            when(plumberRepositoryMock.findByNameEquals("Petrovich")).thenReturn(Optional.of(plumber));
            //when
            Executable executable = () -> houseService.assign(anyInt(), "Petrovich");
            //then
            assertThrows(OutOfHousesLimitException.class, executable);
        }
    }

    @Nested
    class ReleaseTest {
        @Test
        void should_ResetPlumberAndReturnName_When_IdIsPresent() {
            //given
            Plumber plumber = new Plumber(1, "Petrovich", null);
            House house = new House(1, "221b, Baker street", plumber);
            when(houseRepositoryMock.findById(1)).thenReturn(Optional.of(house));
            String expected = "Petrovich";
            //when
            String actual = houseService.release(1);
            //then
            assertAll(
                    () -> assertEquals(expected, actual),
                    () -> assertNull(house.getPlumber())
            );
        }

        @Test
        void should_ThrowHouseNotFoundException_When_IdIsNotPresent() {
            //given
            Plumber plumber = new Plumber(1, "Petrovich", null);
            House house = new House(1, "221b, Baker street", plumber);
            when(houseRepositoryMock.findById(anyInt())).thenThrow(HouseNotFoundException.class);
            //when
            Executable executable = () -> houseService.release(anyInt());
            //then
            assertAll(
                    () -> assertThrows(HouseNotFoundException.class, executable),
                    () -> assertEquals(house.getPlumber(), plumber)
            );
        }
    }

}