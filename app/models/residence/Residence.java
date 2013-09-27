package models.residence;

import javax.persistence.*;

import models.person.Person;

import org.joda.time.LocalDate;
import org.joda.time.Months;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateSerializer;
import core.io.serialization.JodaLocalDateDeserializer;

/**
 * @author adericbourg
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Residence extends Model {

    @Id
    public Long id;

    @Enumerated(EnumType.STRING)
    public ResidenceType residenceType;

    @Constraints.Required
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate startDate;

    @Constraints.Required
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate endDate;

    @JsonIgnore
    @ManyToOne
    public Person person;

    @Transient
    public int getMonthsToEnd() {
        return Months.monthsBetween(LocalDate.now(), endDate).getMonths();
    }

    @Transient
    public boolean isCurrentResidence() {
        LocalDate today = LocalDate.now();
        return today.isAfter(startDate) && today.isBefore(endDate)
                || today.isEqual(startDate) || today.isEqual(endDate);
    }

    @Transient
    public int getEndDateSortOrder() {
        // FIXME This is so ugly :-(
        return endDate.getYear() * 10000 + (endDate.getMonthOfYear() + 10)
                * 100 + (endDate.getDayOfMonth() + 10);
    }

    //

    @Transient
    public Residence copy() {
        Residence copy = new Residence();
        copy.residenceType = residenceType;
        return copy;
    }
}
