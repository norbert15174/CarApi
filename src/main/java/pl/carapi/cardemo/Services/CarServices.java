package pl.carapi.cardemo.Services;

import pl.carapi.cardemo.Repositories.CarRepository;
import org.springframework.stereotype.Service;
import pl.carapi.cardemo.Model.CarModel;
import pl.carapi.cardemo.Model.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServices implements CarRepository {

    List<CarModel> carModelList = new ArrayList<>();


    public CarServices() {
        this.carModelList.add(new CarModel(1L,"BMW","X6","WHITE"));
        this.carModelList.add(new CarModel(2L,"AUDI","A5","WHITE"));
        this.carModelList.add(new CarModel(3L,"VOLSVAGEN","PASAT","METALIC"));
    }

    @Override
    public List<CarModel> findAll(){
        return this.carModelList;
    }
    @Override
    public Optional<CarModel> findById(long id){
        return this.carModelList
                .stream()
                .filter(carModel -> carModel.getId() == id)
                .findFirst();
    }
    @Override
    public List<CarModel> findByColor( String color ){
            color.toUpperCase();
            return this.carModelList
                    .stream()
                    .filter(carModel -> carModel.getColor().toString().equalsIgnoreCase(color))
                    .collect(Collectors.toList());
    }
    @Override
    public boolean addCarModel(CarModel newCarModel){
        boolean findCar = this.carModelList
                .stream()
                .filter(carModel -> carModel.getId() == newCarModel.getId())
                .findFirst()
                .isPresent();
        if(findCar) return false;
        this.carModelList.add(newCarModel);
        return true;
    }
    @Override
    public boolean modifyCarModel(CarModel modifyCarModel){
        Optional<CarModel> findCar = this.carModelList
                .stream()
                .filter(carModel -> carModel.getId() == modifyCarModel.getId())
                .findFirst();
        if(findCar.isPresent()){
            this.carModelList.remove(findCar.get());
            this.carModelList.add(modifyCarModel);
            return true;
        }
        return false;
    }


    @Override
    public boolean modifyCarModelPosition(long id, String type, String newPosition){

        Optional<CarModel> findCar = this.carModelList
                .stream()
                .filter(carModel -> carModel.getId() == id)
                .findFirst();
        if(findCar.isPresent()){
            type.toLowerCase();
            switch (type){
                case "id":
                    this.carModelList.get((int)id-1)
                            .setId(Long.parseLong(newPosition));
                    break;
                case "brand":
                    this.carModelList.get((int)id-1)
                            .setBrand(newPosition);
                    break;
                case "model":
                    this.carModelList.get((int)id-1)
                            .setModel(newPosition);
                    break;
                case "color":
                    this.carModelList.get((int)id-1)
                            .setColor(new Color(newPosition));
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;

    }
    @Override
    public boolean deleteCarModel(int id){
        Optional<CarModel> findCar = this.carModelList
                .stream().filter(carModel -> carModel.getId() == id)
                .findFirst();
        if(findCar.isPresent()){
            this.carModelList.remove(findCar.get());
            return true;
        }
        return false;

    }
}
