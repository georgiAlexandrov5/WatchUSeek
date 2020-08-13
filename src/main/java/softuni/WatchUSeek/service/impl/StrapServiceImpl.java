package softuni.WatchUSeek.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.entities.Strap;
import softuni.WatchUSeek.data.models.service.StrapServiceModel;
import softuni.WatchUSeek.errors.EntityNotSavedException;
import softuni.WatchUSeek.errors.StrapNotFoundException;
import softuni.WatchUSeek.repositories.StrapRepository;
import softuni.WatchUSeek.service.interfaces.StrapService;
import softuni.WatchUSeek.service.validations.StrapServiceModelValidator;

import java.util.List;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.STRAP_NOT_FOUND;
import static softuni.WatchUSeek.service.validations.ValidationConstants.ENTITY_NOT_SAVED;

@Service
public class StrapServiceImpl implements StrapService {

    private final ModelMapper mapper;
    private final StrapRepository strapRepository;
    private final StrapServiceModelValidator validator;

    @Autowired
    public StrapServiceImpl(ModelMapper mapper, StrapRepository strapRepository, StrapServiceModelValidator validator) {
        this.mapper = mapper;
        this.strapRepository = strapRepository;
        this.validator = validator;
    }


    @Override
    public void addStrap(StrapServiceModel strapServiceModel) {
        if (!validator.isValid(strapServiceModel)) {
            throw new EntityNotSavedException(ENTITY_NOT_SAVED);
        }
        Strap strap = this.mapper.map(strapServiceModel, Strap.class);
        this.strapRepository.save(strap);
    }

    @Override
    public StrapServiceModel findStrapByName(String name) {
        return this.strapRepository.findByName(name)
                .map(s -> this.mapper.map(s, StrapServiceModel.class))
                .orElseThrow(() -> new StrapNotFoundException(STRAP_NOT_FOUND));
    }

    @Override
    public List<StrapServiceModel> findAll() {
        return strapRepository.findAll()
                .stream()
                .map(s -> this.mapper.map(s, StrapServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public StrapServiceModel findStrapById(String id) {
        return this.strapRepository.findById(id)
                .map(r -> this.mapper.map(r, StrapServiceModel.class))
                .orElseThrow(() -> new StrapNotFoundException(STRAP_NOT_FOUND));
    }

    @Override
    public void editById(String id, StrapServiceModel strapServiceModel) {

        if (!validator.isValid(strapServiceModel)) {
            throw new EntityNotSavedException(ENTITY_NOT_SAVED);
        }
        Strap strap = this.getStrapById(id);

        strap.setDescription(strapServiceModel.getDescription());
        strap.setMaterial(strapServiceModel.getMaterial());
        strap.setName(strapServiceModel.getName());
        strap.setPhone(strapServiceModel.getPhone());
        strap.setPicUrl(strapServiceModel.getPicUrl());
        strap.setPrice(strapServiceModel.getPrice());
        strapRepository.save(strap);
    }

    @Override
    public void deleteStrap(String id) {
        Strap strap = this.getStrapById(id);
        strapRepository.delete(strap);
    }

    private Strap getStrapById(String id){
        return this.strapRepository.findById(id)
                .orElseThrow(() -> new StrapNotFoundException(STRAP_NOT_FOUND));
    }
}
