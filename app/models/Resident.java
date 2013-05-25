package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Resident extends Model {

    private static final Finder<Long, Resident> FINDER = new Finder<Long, Resident>(
            Long.class, Resident.class);

    @Id
    public Long id;
    public String firstName;
    public String lastName;

    //

    /**
     * Fetch all residents. * @return All residents.
     */
    public static List<Resident> findAll() {
        return FINDER.all();
    }
}
