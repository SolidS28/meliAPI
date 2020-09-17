package item.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="children")
@Inheritance(strategy=InheritanceType.JOINED)
public class Child implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @JsonProperty("stop_time")
    private ZonedDateTime stopTime;

}
