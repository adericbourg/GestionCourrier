package models.residence;

import javax.persistence.*;

import org.joda.time.LocalDate;

import play.db.ebean.Model;

/**
 * @author adericbourg
 */
@Entity
public class Residence extends Model {

    @Id
    public Long id;
    @Enumerated(EnumType.STRING)
    public ResidenceType residenceType;
    public LocalDate startDate;
    public LocalDate endDate;
    public String mailForwardAddress;
    @ManyToOne
    public Resident resident;

}
