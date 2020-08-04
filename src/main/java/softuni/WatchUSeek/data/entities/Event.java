package softuni.WatchUSeek.data.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event extends BaseEntity{

    private String title;
    private String location;

}
