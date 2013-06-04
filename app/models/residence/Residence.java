package models.residence;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.joda.time.LocalDate;

import play.db.ebean.Model;
import core.io.serialization.JodaLocalDateDeserializer;
import core.io.serialization.StaticListSerializer;

/**
 * @author adericbourg
 */
@Entity
public class Residence extends Model {

    @Id
    public Long id;

    @JsonSerialize(using = StaticListSerializer.class)
    @Enumerated(EnumType.STRING)
    public ResidenceType residenceType;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate startDate;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate endDate;
    public String mailForwardAddress;

    @JsonIgnore
    @ManyToOne
    public Resident resident;
}
