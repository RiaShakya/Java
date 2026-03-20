package EventRegistration;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private String name, description, dateTime, venue;
    private int totalSeats;
    private List<User> participants = new ArrayList<>();

    public Event(int id, String name, String description, String dateTime, String venue, int totalSeats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.venue = venue;
        this.totalSeats = totalSeats;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDateTime() { return dateTime; }
    public String getVenue() { return venue; }
    public int getTotalSeats() { return totalSeats; }
    public List<User> getParticipants() { return participants; }

    public boolean hasAvailableSeats() { return participants.size() < totalSeats; }
    public void addParticipant(User user) { participants.add(user); }
    }