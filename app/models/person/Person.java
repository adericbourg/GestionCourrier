package models.person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import models.inbox.Mail;
import models.residence.Residence;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ext.JodaSerializers;
import org.joda.time.LocalDate;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.*;
import com.avaje.ebean.Query;
import com.google.common.base.Strings;
import core.io.serialization.JodaLocalDateDeserializer;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends Model {

    private static final Finder<Long, Person> FINDER = new Finder<Long, Person>(Long.class, Person.class);

    @Id
    public Long id;

    public Gender gender;

    @Constraints.Required
    public String firstName;

    @Constraints.Required
    public String lastName;

    public String maidenName;

    @JsonSerialize(using = JodaSerializers.LocalDateSerializer.class)
    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public LocalDate birthDate;

    public Department followingDepartment;

    public String followedBy;

    public String orientation;

    @Constraints.Required
    public Boolean isFollowed = true;

    public String forwardAddress;

    @Constraints.Required
    public Boolean forwardAddressActive = false;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    public List<Residence> residences = new ArrayList<Residence>();

    @JsonIgnore
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    public List<Mail> mail = new ArrayList<Mail>();

    //

    @Transient
    public Residence getLatestResidence() {
        Residence latestResidence = null;
        for (Residence residence : residences) {
            if (latestResidence == null) {
                latestResidence = residence;
            } else if (residence.startDate.isAfter(latestResidence.startDate)) {
                latestResidence = residence;
            }
        }
        return latestResidence;
    }

    @Transient
    public Residence getCurrentResidence() {
        Residence currentResidence = null;
        for (Residence residence : residences) {
            if (residence.isCurrentResidence()) {
                currentResidence = residence;
                break;
            }
        }
        return currentResidence;
    }

    @Transient
    public int getResidenceProgress() {
        Residence currentResidence = getCurrentResidence();
        if (currentResidence == null) {
            return -1;
        }
        return (int) (100 * (1 - ((double) currentResidence.getMonthsToEnd() / 12)));
    }

    @Transient
    public String getDisplay() {
        StringBuilder sb = new StringBuilder(String.format("%s %s", firstName, lastName));
        if (!Strings.isNullOrEmpty(maidenName)) {
            sb.append(String.format(" (%s)", maidenName));
        }
        return sb.toString();
    }

    @Transient
    public boolean isMailForwardMandatory() {
        return forwardAddressActive && !Strings.isNullOrEmpty(forwardAddress);
    }

    @Transient
    public int getMailCount() {
        return mail == null ? 0 : mail.size();
    }

    @Transient
    public LocalDate getOldestMailArrivalDate() {
        LocalDate oldestDate = null;
        for (Mail mailReceived : mail) {
            if (mailReceived.withdrawalDate == null) {
                if (oldestDate == null) {
                    oldestDate = mailReceived.arrivalDate;
                } else if (oldestDate.isAfter(mailReceived.arrivalDate)) {
                    oldestDate = mailReceived.arrivalDate;
                }
            }
        }
        return oldestDate;
    }

    //

    /**
     * Fetch all persons.
     * 
     * @return All persons.
     */
    public static List<Person> findAll() {
        return FINDER.all();
    }

    public static List<Person> find(String queryString) {
        if (Strings.isNullOrEmpty(queryString) || queryString.length() < 3) {
            return Collections.emptyList();
        }
        ExpressionList<Person> where = FINDER.where();
        for (String queryToken : queryString.split(" ")) {
            Expression ex = Expr.ilike("firstName", queryToken + "%");
            ex = Expr.or(ex, Expr.ilike("lastName", queryToken + "%"));
            ex = Expr.or(ex, Expr.ilike("maidenName", queryToken + "%"));
            where.add(ex);
        }
        return where.findList();
    }

    public static Person byId(Long id) {
        return FINDER.fetch("residences").orderBy("residences.startDate DESC").where().idEq(id).findUnique();
    }

    public static List<Person> findAllResidents() {
        return FINDER.fetch("residences").orderBy("residences.startDate DESC").where().le("residences.startDate", LocalDate.now())
                .ge("residences.endDate", LocalDate.now()).findList();
    }

    public static List<Person> findEndOfResidencePersons() {
        LocalDate lowerBound = LocalDate.now();
        LocalDate upperBound = lowerBound.plusMonths(3);
        return FINDER.fetch("residences").orderBy("residences.startDate DESC").where()
                .between("residences.endDate", LocalDate.now(), upperBound).findList();
    }

    public static List<Person> findNoResidencePersons() {
        String q = "find * fetch residences where residences.startDate > :today or residences.endDate < :today or residences.id is null";
        Query<Person> query = Ebean.createQuery(Person.class, q);
        query.setParameter("today", LocalDate.now());
        return query.findList();
    }
}
