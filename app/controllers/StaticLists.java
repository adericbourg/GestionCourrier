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
 * Controller for static lists. Serves all static lists sorted by displayed value.
 * 
 * @author adericbourg
 */
public class StaticLists extends Controller {

    private static final Comparator<? super Item<StaticList, String>> ITEM_COMPARATOR = new Comparator<Item<StaticList, String>>() {
        @Override
        public int compare(Item<StaticList, String> o1, Item<StaticList, String> o2) {
            Collator collator = Collator.getInstance(Locale.FRENCH);
            collator.setStrength(Collator.SECONDARY);
            collator.setDecomposition(Collator.FULL_DECOMPOSITION);
            return collator.compare(o1.value, o2.value);
        }
    };

    public static Result departments() {
        return toJson(Department.values());
    }

    public static Result residenceTypes() {
        return toJson(ResidenceType.values());
    }

    public static Result genders() {
        return toJson(Gender.values());
    }

    protected static <T extends StaticList> Result toJson(T[] values) {
        List<Item<StaticList, String>> returnList = new ArrayList<Item<StaticList, String>>();
        for (T item : values) {
            returnList.add(new Item<StaticList, String>(item, item.getMeaning()));
        }
        Collections.sort(returnList, ITEM_COMPARATOR);
        return ok(Json.toJson(returnList));
    }
}