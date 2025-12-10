package agenda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Description : An agenda that stores events
 */
public class Agenda {
    
    private final List<Event> events = new LinkedList<>();

    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day to test
     * @return a list of events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.isInDay(day)) {
                result.add(e);
            }
        }
        return result;
    }
    
    // -- Questions Bonus --

    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getTitle().equals(title)) {
                result.add(e);
            }
        }
        return result;
    }
    
    /**
     * Déterminer s’il y a de la place dans l'agenda pour un événement (aucun autre événement au même moment)
     * @param e L'événement à tester (on se limitera aux événements sans répétition)
     * @return vrai s’il y a de la place dans l'agenda pour cet événement
     */
    public boolean isFreeFor(Event e) {
        for (Event existing : events) {
            
            if (e.getStart().isBefore(existing.getStart().plus(existing.getDuration())) &&
                existing.getStart().isBefore(e.getStart().plus(e.getDuration()))) {
                return false;
            }
        }
        return true;
    }
}