package models.residence;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class Resident extends Model {

    private static final Finder<Long, Resident> FINDER = new Finder<Long, Resident>(Long.class, Resident.class);

    @Id
    public Long id;
    @Constraints.Required
    public String firstName;
    @Constraints.Required
    public String lastName;
    public Date birthDate; // TODO Use LocalDate (need to fix JSon mapping)
    public Department followingDepartment;
    public String followedBy;
    @OneToMany(mappedBy = "resident")
    public List<Residence> residences;

    //

    /**
     * Fetch all residents. * @return All residents.
     */
    public static List<Resident> findAll() {
        return FINDER.all();
    }

    public static Resident byId(Long id) {
        return FINDER.byId(id);
    }
}
