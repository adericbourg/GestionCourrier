package models.inbox;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.person.Person;

import org.joda.time.LocalDate;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;
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
    public Person recipient;

    public String sender;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate arrivalDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate withdrawalDate;
}
