package item.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item extends Child implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    @JsonProperty("category_id")
    private String categoryId;
    private long price;
    @JsonProperty("start_time")
    private ZonedDateTime startTime;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Child> items;

}
