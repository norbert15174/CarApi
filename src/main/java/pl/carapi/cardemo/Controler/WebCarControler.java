package pl.carapi.cardemo.Controler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.carapi.cardemo.Model.CarModel;
import pl.carapi.cardemo.Services.CarServices;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController()
@RequestMapping(path = "/Cars", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
public class WebCarControler {

    CarServices carServices;

    @Autowired
    public WebCarControler(CarServices carServices) {
        this.carServices = carServices;
    }


    CollectionModel<CarModel> linkToCar (List<CarModel> carModels){
        CollectionModel<CarModel> collectionModel = CollectionModel.of(carModels);
        collectionModel.forEach(carModel -> carModel.removeLinks());
        Link link = linkTo(WebCarControler.class).withRel("All Cars");
        collectionModel.add(link);
        return  collectionModel;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<CarModel>> findAllPosition(){
        List<CarModel> carModels = carServices.findAll();
        if(!carModels.isEmpty()){
            CollectionModel<CarModel> collectionModel = linkToCar(carModels);
            collectionModel.forEach(carModel -> carModel.add(linkTo(WebCarControler.class).slash(carModel.getId()).withSelfRel()));
            return new ResponseEntity(collectionModel,HttpStatus.FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<EntityModel<CarModel>> findCarById(@PathVariable long id){
        if(carServices
                .findById(id)
                .isPresent()) {
            Link link = linkTo(WebCarControler.class).slash(id).withSelfRel();
            EntityModel<CarModel> entityModel = EntityModel.of(carServices.findById(id).get());
            entityModel.add(link);
            return new ResponseEntity(entityModel, HttpStatus.FOUND);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="/color/{color}")
    public ResponseEntity<CollectionModel<CarModel>> findCarByColor(@PathVariable String color){
        List<CarModel> carModels = carServices.findByColor(color);
        if(!carModels.isEmpty()){
            CollectionModel<CarModel> collectionModel = linkToCar(carModels);
            collectionModel.forEach(carModel -> carModel.add(linkTo(WebCarControler.class).slash(carModel.getColor()).withSelfRel()));
            return new ResponseEntity(collectionModel,HttpStatus.FOUND);}


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
