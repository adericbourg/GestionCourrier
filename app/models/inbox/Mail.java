package models.inbox;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.residence.Resident;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.joda.time.LocalDate;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import core.io.serialization.JodaLocalDateDeserializer;

/**
 * @author alban
 */
@Entity
public class Mail extends Model {

    @Id
    public Long id;

    @Constraints.Required
    @ManyToOne
    public Resident recipient;

    public String sender;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate arrivalDate;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate withdrawalDate;

    public Boolean toBeForwarded = false;
}
