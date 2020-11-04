package pl.carapi.cardemo.services;

import pl.carapi.cardemo.models.Color;
import pl.carapi.cardemo.repositories.CarRepository;
import org.springframework.stereotype.Service;
import pl.carapi.cardemo.models.CarModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServices implements CarRepository {

    List<CarModel> carModelList = new ArrayList<>();


    public CarServices() {
        this.carModelList.add(new CarModel(1L,"BMW","X6", Color.BLACK));
        this.carModelList.add(new CarModel(2L,"AUDI","A5",Color.BLUE));
        this.carModelList.add(new CarModel(3L,"VOLSVAGEN","PASAT",Color.METALIC));
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
        int size = carModelList.size();

        while (true) {
            Optional<CarModel> findId = Optional.of(this.carModelList.get(size));
            if (findId.isPresent()) {
                size++;
            }else {
                break;
            }
        }
        newCarModel.setId(size);
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
            this.carModelList.get((int)modifyCarModel.getId()).setBrand(modifyCarModel.getBrand());
            this.carModelList.get((int)modifyCarModel.getId()).setModel(modifyCarModel.getModel());
            this.carModelList.get((int)modifyCarModel.getId()).setColor(modifyCarModel.getColor());
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
                            .setColor(Color.valueOf(newPosition));
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
