package pl.carapi.cardemo.Repositories;

import org.springframework.stereotype.Repository;
import pl.carapi.cardemo.Model.CarModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository {

    List<CarModel> findAll();

    Optional<CarModel> findById(long id);

    List<CarModel> findByColor( String color );

    boolean addCarModel(CarModel newCarModel);

    boolean modifyCarModel(CarModel modifyCarModel);

    boolean modifyCarModelPosition(long id, String type, String newPosition);

    boolean deleteCarModel(int id);


}
