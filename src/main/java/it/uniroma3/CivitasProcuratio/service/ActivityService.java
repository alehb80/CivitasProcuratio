package it.uniroma3.CivitasProcuratio.service;

import it.uniroma3.CivitasProcuratio.dao.ActivityDAO;
import it.uniroma3.CivitasProcuratio.model.Activity;
import it.uniroma3.CivitasProcuratio.model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityDAO activityDAO;

    @Transactional
    public void save(final Activity activity) {
        this.activityDAO.save(activity);
    }

    @Transactional
    public Iterable<Activity> findAll() {
        return this.activityDAO.findAll();
    }

    @Transactional
    public Activity findOne(Long id) {
        return this.activityDAO.findOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.activityDAO.delete(id);
    }

    @Transactional
    public List<Activity> findByGuest(Guest guest) {
        return this.activityDAO.findByGuest(guest);
    }

    @Transactional
    public List<Activity> findByDate(Date date) {
        return this.activityDAO.findByDate(date);
    }

    @Transactional
    public List<Activity> getPresenceGuestsByDate(List<Guest> guests, Date date) {
        List<Activity> activitiesByDate = this.findByDate(date);
        List<Activity> temp = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();
        for (Activity a : activitiesByDate) {
            for (Guest g : guests) {
                if (g.getActivities().contains(a))
                    temp.add(a);
            }
        }
        for (Activity a : temp) {
            if (a.getName().equals("PRESENCE") || a.getName().equals("presence") || a.getName().equals("Presence"))
                activities.add(a);
        }
        return activities;
    }

    @Transactional
    public List<Activity> getPresenceBetweenPeriod(List<Guest> guests, Date from, Date to) {
        List<Activity> activities = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        while (calendar.getTime().before(to)) {
            activities.addAll(this.getPresenceGuestsByDate(guests, calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        calendar.setTime(to);
        activities.addAll(this.getPresenceGuestsByDate(guests, calendar.getTime()));
        return activities;
    }

    public boolean alreadyExists(Activity activity, String name1, Date date) {
        List<Activity> activities = this.activityDAO.findByDate(date);
        for (Activity a: activities) {
            if (a.getName().equals(name1) && a.getDate().equals(activity.getDate()) &&
                    a.getGuest().equals(activity.getGuest()))
                return true;
        }
        return false;
    }

}
