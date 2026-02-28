package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;   // mock dependency

    @InjectMocks
    private CarServiceImpl carService;     // service with mock injected

    private Car sampleCar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // initialize mocks

        sampleCar = new Car();
        sampleCar.setCarId("123");
        sampleCar.setCarName("Toyota");
        sampleCar.setCarColor("Red");
        sampleCar.setCarQuantity("5");  // if carQuantity is String, use "5"
    }

    @Test
    void testCreateCar() {
        when(carRepository.create(any(Car.class))).thenReturn(sampleCar);

        Car created = carService.create(sampleCar);

        assertNotNull(created);
        assertEquals("Toyota", created.getCarName());
        verify(carRepository).create(sampleCar);
    }

    @Test
    void testFindAllCars() {
        List<Car> cars = Arrays.asList(sampleCar);
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.findAll();

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getCarName());
        verify(carRepository).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById("123")).thenReturn(sampleCar);

        Car found = carService.findById("123");

        assertNotNull(found);
        assertEquals("123", found.getCarId());
        verify(carRepository).findById("123");
    }

    @Test
    void testUpdateCar() {
        when(carRepository.update(eq("123"), any(Car.class))).thenReturn(sampleCar);

        Car updated = carService.update("123", sampleCar);

        assertEquals("Toyota", updated.getCarName());
        verify(carRepository).update("123", sampleCar);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("123");

        verify(carRepository).delete("123");
    }
}