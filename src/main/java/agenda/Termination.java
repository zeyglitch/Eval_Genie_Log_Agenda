package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Termination {

    private LocalDate start;
    private ChronoUnit frequency;
    private final LocalDate terminationDate;
    private final long numberOfOccurrences;

    public LocalDate terminationDateInclusive() {
        return terminationDate;
    }

    public long numberOfOccurrences() {
        return numberOfOccurrences;
    }

    /**
     * Constructs a termination at a given date
     */
    public Termination(LocalDate start, ChronoUnit frequency, LocalDate terminationInclusive) {
        this.start = start;
        this.frequency = frequency;
        this.terminationDate = terminationInclusive;
        // On calcule le nombre d'occurrences correspondant
        this.numberOfOccurrences = frequency.between(start, terminationInclusive) + 1;
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     */
    public Termination(LocalDate start, ChronoUnit frequency, long numberOfOccurrences) {
        this.start = start;
        this.frequency = frequency;
        this.numberOfOccurrences = numberOfOccurrences;
        // On calcule la date de fin correspondante
        this.terminationDate = start.plus(numberOfOccurrences - 1, frequency);
    }
}