package pl.carapi.cardemo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.carapi.cardemo.Model.CarModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServices {

    List<CarModel> carModelList = new ArrayList<>();


    public CarServices() {
        this.carModelList.add(new CarModel(1l,"BMW","X6","WHITE"));
        this.carModelList.add(new CarModel(2l,"AUDI","A5","WHITE"));
        this.carModelList.add(new CarModel(3l,"VOLSVAGEN","PASAT","METALIC"));
    };

    public List<CarModel> findAll(){
        return this.carModelList;
    };

    public Optional<CarModel> findById(long id){
        return this.carModelList.stream().filter(carModel -> carModel.getId() == id).findFirst();
    };

    public List<CarModel> findByColor( String color ){
            return this.carModelList.stream().filter(carModel -> carModel.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
    };

    public boolean addCarModel(CarModel newCarModel){
        boolean findCar = this.carModelList.stream().filter(carModel -> carModel.getId() == newCarModel.getId()).findFirst().isPresent();
        if(findCar) return false;
        this.carModelList.add(newCarModel);
        return true;
    }
    public boolean modifyCarModel(CarModel modifyCarModel){
        Optional<CarModel> findCar = this.carModelList.stream().filter(carModel -> carModel.getId() == modifyCarModel.getId()).findFirst();
        if(findCar.isPresent()){
            this.carModelList.remove(findCar.get());
            this.carModelList.add(modifyCarModel);
            return true;
        }
        return false;
    }



    public boolean modifyCarModelPosition(long id, String type, String newPosition){

        Optional<CarModel> findCar = this.carModelList.stream().filter(carModel -> carModel.getId() == id).findFirst();
        if(findCar.isPresent()){
            type.toLowerCase();
            switch (type){
                case "id":
                    this.carModelList.get((int)id-1).setId(Long.parseLong(newPosition));
                    break;
                case "brand":
                    this.carModelList.get((int)id-1).setBrand(newPosition);
                    break;
                case "model":
                    this.carModelList.get((int)id-1).setModel(newPosition);
                    break;
                case "color":
                    this.carModelList.get((int)id-1).setColor(newPosition);
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;

    }

    public boolean deleteCarModel(int id){
        Optional<CarModel> findCar = this.carModelList.stream().filter(carModel -> carModel.getId() == id).findFirst();
        if(findCar.isPresent()){
            this.carModelList.remove(findCar.get());
            return true;
        }
        return false;

    }
}
