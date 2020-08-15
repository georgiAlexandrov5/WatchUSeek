package softuni.WatchUSeek.ScheduledJob;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import softuni.WatchUSeek.repositories.ReviewRepository;

@Component
@EnableAsync
public class ScheduleJob {

    private final ReviewRepository reviewRepository;


    public ScheduleJob(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Async
    @Scheduled(cron = "0 1 1 * *")
    protected void scheduleJob() {
        reviewRepository.deleteAll();

    }

}
