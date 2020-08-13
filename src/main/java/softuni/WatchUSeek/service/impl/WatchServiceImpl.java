package softuni.WatchUSeek.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.WatchUSeek.data.entities.Watch;
import softuni.WatchUSeek.data.models.service.WatchServiceModel;
import softuni.WatchUSeek.errors.EntityNotSavedException;
import softuni.WatchUSeek.errors.WatchNotFoundException;
import softuni.WatchUSeek.repositories.UserRepository;
import softuni.WatchUSeek.repositories.WatchRepository;
import softuni.WatchUSeek.service.interfaces.WatchService;
import softuni.WatchUSeek.service.validations.WatchServiceModelValidator;

import java.util.List;
import java.util.stream.Collectors;

import static softuni.WatchUSeek.errors.Constants.WATCH_NOT_FOUND;
import static softuni.WatchUSeek.service.validations.ValidationConstants.ENTITY_NOT_SAVED;

@Service
public class WatchServiceImpl implements WatchService {


    private final WatchRepository watchRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final WatchServiceModelValidator validator;

    @Autowired
    public WatchServiceImpl(WatchRepository watchRepository,
                            UserRepository userRepository,
                            ModelMapper modelMapper, WatchServiceModelValidator validator) {
        this.watchRepository = watchRepository;
        this.userRepository = userRepository;
        this.mapper = modelMapper;
        this.validator = validator;
    }


    @Override
    public void addWatch(WatchServiceModel watchServiceModel) {
        if (!validator.isValid(watchServiceModel)) {
            throw new EntityNotSavedException(ENTITY_NOT_SAVED);
        }
        Watch watch = this.mapper.map(watchServiceModel, Watch.class);
        this.watchRepository.save(watch);

    }

    @Override
    public WatchServiceModel findWatchByRefNumber(String name) {
        return this.watchRepository.findByRefNumber(name)
                .map(w -> this.mapper.map(w, WatchServiceModel.class))
                .orElseThrow(() -> new WatchNotFoundException(WATCH_NOT_FOUND));
    }

    @Override
    public List<WatchServiceModel> findAll() {
        return watchRepository.findAll()
                .stream()
                .map(w -> this.mapper.map(w, WatchServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public WatchServiceModel findWatchById(String id) {
        return this.watchRepository.findById(id)
                .map(r -> this.mapper.map(r, WatchServiceModel.class))
                .orElseThrow(() -> new WatchNotFoundException(WATCH_NOT_FOUND));
    }

    @Override
    public void editById(String id, WatchServiceModel watchServiceModel) {
        if (!validator.isValid(watchServiceModel)) {
            throw new EntityNotSavedException(ENTITY_NOT_SAVED);
        }
        Watch watch = this.getWatchById(id);

        watch.setDescription(watchServiceModel.getDescription());
        watch.setManufacturer(watchServiceModel.getManufacturer());
        watch.setPrice(watchServiceModel.getPrice());
        watch.setYear(watchServiceModel.getYear());
        watch.setRefNumber(watchServiceModel.getRefNumber());
        watch.setPicUrl(watchServiceModel.getPicUrl());
        watch.setContactNumber(watchServiceModel.getContactNumber());

        watchRepository.save(watch);

    }

    @Override
    public void deleteWatch(String id) {
        Watch watch = this.getWatchById(id);
        watchRepository.delete(watch);
    }

    private Watch getWatchById(String id){
        return watchRepository.findById(id)
                .orElseThrow(() -> new WatchNotFoundException(WATCH_NOT_FOUND));
    }


}
