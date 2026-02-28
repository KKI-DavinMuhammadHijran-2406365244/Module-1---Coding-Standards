package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private Car sampleCar;

    @BeforeEach
    void setUp() {
        sampleCar = new Car();
        sampleCar.setCarId("123");
        sampleCar.setCarName("Toyota");
        sampleCar.setCarColor("Red");
        sampleCar.setCarQuantity("5");
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Mockito.when(carService.create(any(Car.class))).thenReturn(sampleCar);

        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Toyota")
                        .param("carColor", "Red")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));
    }

    @Test
    void testCarListPage() throws Exception {
        List<Car> cars = Arrays.asList(sampleCar);
        Mockito.when(carService.findAll()).thenReturn(cars);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("listCar"))
                .andExpect(model().attribute("cars", cars));
    }

    @Test
    void testEditCarPage() throws Exception {
        Mockito.when(carService.findById("123")).thenReturn(sampleCar);

        mockMvc.perform(get("/car/editCar/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attribute("car", sampleCar));
    }

    @Test
    void testEditCarPost() throws Exception {
        Mockito.when(carService.update(eq("123"), any(Car.class))).thenReturn(sampleCar);

        mockMvc.perform(post("/car/editCar")
                        .param("carId", "123")
                        .param("carName", "Toyota")
                        .param("carColor", "Blue")
                        .param("carQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));
    }

    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        Mockito.verify(carService).deleteCarById("123");
    }
}