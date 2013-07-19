package controllers;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import models.person.Department;
import models.person.Gender;
import models.residence.ResidenceType;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.google.common.collect.Lists;

import core.controller.CatchBusinessException;
import core.io.serialization.StaticList;

/**
 * Controller for static lists. Serves all static lists sorted by displayed value.
 * 
 * @author adericbourg
 */
@CatchBusinessException
public class StaticLists extends Controller {

    private static final Comparator<StaticList> ITEM_COMPARATOR = new Comparator<StaticList>() {
        @Override
        public int compare(StaticList o1, StaticList o2) {
            Collator collator = Collator.getInstance(Locale.FRENCH);
            collator.setStrength(Collator.SECONDARY);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
            return collator.compare(o1.getMeaning(), o2.getMeaning());
        }
    };

    /**
     * Lists all departments.
     * 
     * @return All departments.
     */
    public static Result departments() {
        return toJson(Department.values());
    }

    /**
     * List all residence types.
     * 
     * @return All residence types.
     */
    public static Result residenceTypes() {
        return toJson(ResidenceType.values());
    }

    /**
     * Lists all genders.
     * 
     * @return All genders.
     */
    public static Result genders() {
        return toJson(Gender.values());
    }

    private static <T extends StaticList> Result toJson(T[] values) {
        List<T> returnList = Lists.newArrayList(values);
        Collections.sort(returnList, ITEM_COMPARATOR);
        return ok(Json.toJson(returnList));
    }
}
