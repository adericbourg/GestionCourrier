package models.inbox;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.person.Person;

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

    private static final Finder<Long, Mail> FINDER = new Finder<Long, Mail>(
            Long.class, Mail.class);

    @Id
    public Long id;

    @Constraints.Required
    @ManyToOne
    public Person recipient;

    public String sender;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate arrivalDate;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate withdrawalDate;

    //

    public static Mail byId(Long id) {
        return FINDER.byId(id);
    }

    /**
     * Fetch all mails.
     * 
     * @return All mails.
     */
    public static List<Mail> findFromInbox() {
        return FINDER.where().isNull("withdrawalDate").findList();
    }
}
