package softuni.WatchUSeek.service.interfaces;

import softuni.WatchUSeek.data.models.service.StrapServiceModel;

import java.util.List;

public interface StrapService {

    void addStrap(StrapServiceModel strapServiceModel);

    StrapServiceModel findStrapByName(String name);

    List<StrapServiceModel> findAll();

    StrapServiceModel findStrapById(String id);

    void editById(String id, StrapServiceModel strapServiceModel);

    void deleteStrap(String id);
}