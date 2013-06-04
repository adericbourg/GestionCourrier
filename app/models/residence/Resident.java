package models.residence;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.joda.time.LocalDate;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import core.io.serialization.JodaLocalDateDeserializer;
import core.io.serialization.StaticListSerializer;

@Entity
public class Resident extends Model {

    private static final Finder<Long, Resident> FINDER = new Finder<Long, Resident>(Long.class, Resident.class);

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

    /**
     * Fetch all residents. * @return All residents.
     */
    public static List<Resident> findAll() {
        return FINDER.all();
    }

    public static Resident byId(Long id) {
        return FINDER.fetch("residences").orderBy("residences.startDate DESC").where().idEq(id).findUnique();
    }
}
