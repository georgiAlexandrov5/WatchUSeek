package softuni.WatchUSeek.service;

import softuni.WatchUSeek.data.models.service.WatchServiceModel;

import java.util.List;

public interface WatchService {

    void addWatch(WatchServiceModel watchServiceModel);

    WatchServiceModel findWatchByRefNumber(String name);

    List<WatchServiceModel> findAll();

    WatchServiceModel findWatchById(String id);

    void editById(String id, WatchServiceModel watchServiceModel);

    void deleteWatch(String id);

}
