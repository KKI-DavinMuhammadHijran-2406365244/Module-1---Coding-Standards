package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface CarService {

    Car create(Car car);

    List<Car> findAll();

    Car findById(String carId);

    void update(String carId Car car);

    void deleteCarbyId(String carId);
}
