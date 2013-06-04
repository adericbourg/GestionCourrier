package models.residence;

import static core.util.Collections.first;

import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import core.io.serialization.JodaLocalDateDeserializer;
import core.io.serialization.StaticListSerializer;

@Entity
public class Resident extends Model {

    private static final Finder<Long, Resident> FINDER = new Finder<Long, Resident>(
            Long.class, Resident.class);

    @Id
    public Long id;

    @JsonSerialize(using = StaticListSerializer.class)
    public Sex sex;

    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    public String maidenName;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate birthDate;

    @JsonSerialize(using = StaticListSerializer.class)
    public Department followingDepartment;

    public String followedBy;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    public List<Residence> residences;

    //

    @Transient
    public Residence getLatestResidence() {
        return first(residences);
    }

    @Transient
    public int getResidenceProgress() {
        Residence latestResidence = getLatestResidence();
        if (latestResidence == null
                || LocalDate.now().isAfter(latestResidence.endDate)) {
            return -1;
        }
        return (int) (100 * (1 - (((double) Days.daysBetween(LocalDate.now(),
                latestResidence.endDate).getDays()) / 365)));
    }

    //

    /**
     * Fetch all residents. * @return All residents.
     */
    public static List<Resident> findAll() {
        return FINDER.all();
    }

    public static Resident byId(Long id) {
        return FINDER.fetch("residences").orderBy("residences.startDate DESC")
                .where().idEq(id).findUnique();
    }
}
