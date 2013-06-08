package controllers;

import java.text.Collator;
import java.util.*;

import models.residence.Department;
import models.residence.Gender;
import models.residence.ResidenceType;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import controllers.model.Item;
import core.io.serialization.StaticList;

/**
 * Controller for static lists. Serves all static lists sorted by displayed
 * value.
 * 
 * @author adericbourg
 */
public class StaticLists extends Controller {

    private static final Comparator<? super Item<String, String>> ITEM_COMPARATOR = new Comparator<Item<String, String>>() {
        @Override
        public int compare(Item<String, String> o1, Item<String, String> o2) {
            Collator collator = Collator.getInstance(Locale.FRENCH);
            collator.setStrength(Collator.SECONDARY);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
            return collator.compare(o1.value, o2.value);
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

    protected static <T extends StaticList> Result toJson(T[] values) {
        List<Item<String, String>> returnList = new ArrayList<Item<String, String>>();
        for (T item : values) {
            returnList.add(new Item<String, String>(item.name(), item
                    .getMeaning()));
        }
        Collections.sort(returnList, ITEM_COMPARATOR);
        return ok(Json.toJson(returnList));
    }
}
