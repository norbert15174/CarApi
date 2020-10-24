package pl.carapi.cardemo.Controler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.carapi.cardemo.Model.CarModel;
import pl.carapi.cardemo.Services.CarServices;

@RestController()
@RequestMapping(path = "/Cars", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
public class WebCarControler {

    CarServices carServices;

    @Autowired
    public WebCarControler(CarServices carServices) {
        this.carServices = carServices;
    }


    @GetMapping()
    public ResponseEntity findAllPosition(){
        ResponseEntity responseEntity = new ResponseEntity(carServices.findAll(), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(path="/{id}")
    public ResponseEntity findCarById(@PathVariable long id){
        if(carServices
                .findById(id)
                .isPresent())
            return new ResponseEntity(carServices.findById(id),HttpStatus.FOUND);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="/color/{color}")
    public ResponseEntity findCarByColor(@PathVariable String color){
        if(!carServices.findByColor(color)
                .isEmpty())
            return new ResponseEntity(carServices.findByColor(color),HttpStatus.FOUND);

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody CarModel newCarModel){
        boolean addCarSuccess = carServices.addCarModel(newCarModel);
        if(addCarSuccess)
            return new ResponseEntity(HttpStatus.CREATED);
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    public ResponseEntity modifyCarModel(@RequestBody CarModel modifyCarModel){
        boolean modifyCarSuccess = carServices.modifyCarModel(modifyCarModel);
        if(modifyCarSuccess)
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyCarModelPosition(@PathVariable long id, @RequestParam String type, @RequestParam String newPosition){

        boolean isModifiedSuccess = carServices.modifyCarModelPosition(id,type,newPosition);
        if(isModifiedSuccess)
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_MODIFIED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCarById(@PathVariable int id){
        boolean isDeletedSuccess = carServices.deleteCarModel(id);
        if(isDeletedSuccess)
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
