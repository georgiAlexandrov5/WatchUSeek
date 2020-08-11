package softuni.WatchUSeek.validations;

import softuni.WatchUSeek.data.models.service.WatchServiceModel;

public interface WatchValidationService {
    boolean isWatchValid(WatchServiceModel watchServiceModel);

}
