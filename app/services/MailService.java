package services;

import java.util.List;

import models.inbox.Mail;
import play.db.ebean.Model;

/**
 * @author adericbourg
 */
public class MailService {

    private static final Model.Finder<Long, Mail> FINDER = new Model.Finder<Long, Mail>(Long.class, Mail.class);

    public static Mail byId(Long id) {
        return FINDER.byId(id);
    }

    /**
     * Fetch all mails.
     * 
     * @return All mails.
     */
    public static List<Mail> findFromInbox() {
        return FINDER.where().isNull("withdrawalDate").findList();
    }
}
