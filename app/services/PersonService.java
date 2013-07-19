package services;

import java.util.Collections;
import java.util.List;

import models.person.Person;

import org.joda.time.LocalDate;

import play.db.ebean.Model;

import com.avaje.ebean.*;
import com.google.common.base.Strings;

/**
 * @author adericbourg
 */
public class PersonService {

    private static final Model.Finder<Long, Person> FINDER = new Model.Finder<Long, Person>(Long.class, Person.class);

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
