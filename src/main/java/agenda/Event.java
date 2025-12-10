package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Event {

    private String myTitle;
    private LocalDateTime myStart;
    private Duration myDuration;
    private Repetition repetition;

    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    public void setRepetition(ChronoUnit frequency) {
        this.repetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        if (repetition != null) {
            repetition.addException(date);
        }
    }

    public void setTermination(LocalDate terminationInclusive) {
        if (repetition != null) {
            repetition.setTermination(new Termination(
                myStart.toLocalDate(),
                repetition.getFrequency(),
                terminationInclusive
            ));
        }
    }

    public void setTermination(long numberOfOccurrences) {
        if (repetition != null) {
            repetition.setTermination(new Termination(
                    myStart.toLocalDate(),
                    repetition.getFrequency(),
                    numberOfOccurrences
            ));
        }
    }

    public int getNumberOfOccurrences() {
        if (repetition != null && repetition.getTermination() != null) {
            return (int) repetition.getTermination().numberOfOccurrences();
        }
        return 0;
    }

    public LocalDate getTerminationDate() {
        if (repetition != null && repetition.getTermination() != null) {
            return repetition.getTermination().terminationDateInclusive();
        }
        return null;
    }

    /**
     * Tests if an event occurs on a given day
     */
    public boolean isInDay(LocalDate aDay) {

        if (repetition != null && repetition.isException(aDay)) {
            return false;
        }

        LocalDateTime eventStart = myStart;
        LocalDateTime eventEnd = myStart.plus(myDuration);

        LocalDateTime dayStart = aDay.atStartOfDay();
        LocalDateTime dayEnd = aDay.plusDays(1).atStartOfDay();

        if (eventStart.isBefore(dayEnd) && eventEnd.isAfter(dayStart)) {
            return true;
        }

        if (repetition == null) {
            return false;
        }

        ChronoUnit freq = repetition.getFrequency();
        LocalDate initialDate = myStart.toLocalDate();

        if (aDay.isBefore(initialDate)) return false;

        long diff = freq.between(initialDate, aDay);
        
        if (checkOccurrence(diff, aDay)) return true;

        if (diff > 0 && checkOccurrence(diff - 1, aDay)) return true;

        return false;
    }

    private boolean checkOccurrence(long k, LocalDate aDay) {

        if (repetition.getTermination() != null) {

             if (repetition.getTermination().terminationDateInclusive() != null) {
                 LocalDate occDate = myStart.toLocalDate().plus(k, repetition.getFrequency());
                 if (occDate.isAfter(repetition.getTermination().terminationDateInclusive())) return false;
             }

             if (repetition.getTermination().numberOfOccurrences() > 0) {
                 if (k >= repetition.getTermination().numberOfOccurrences()) return false;
             }
        }

        LocalDateTime kStart = myStart.plus(k, repetition.getFrequency());
        LocalDateTime kEnd = kStart.plus(myDuration);

        if (repetition.isException(kStart.toLocalDate())) return false;

        LocalDateTime dayStart = aDay.atStartOfDay();
        LocalDateTime dayEnd = aDay.plusDays(1).atStartOfDay();

        return kStart.isBefore(dayEnd) && kEnd.isAfter(dayStart);
    }
    
    public String getTitle() { return myTitle; }
    public LocalDateTime getStart() { return myStart; }
    public Duration getDuration() { return myDuration; }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}