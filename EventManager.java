package EventRegistration;

import java.util.*;

public class EventManager {

    private static EventManager instance = new EventManager();
    private List<User> users = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private User currentUser;
    private int nextEventId = 1;

    private EventManager() {
        // Fixed admin
        users.add(new User("Admin", "admin@gmail.com", "admin", "admin123", "ADMIN"));
    }

    public static EventManager getInstance() {
        return instance;
    }

    public boolean registerUser(User user) {
        // Prevent admin registration
        if ("ADMIN".equals(user.getRole())) return false;

        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) return false;
        }
        users.add(user);
        return true;
    }

    public User login(String username, String pw) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.checkPassword(pw)) {
                currentUser = u;
                return u;
            }
        }
        return null;
    }

    public Event createEvent(String name, String desc, String dt, String venue, int seats) {
        Event e = new Event(nextEventId++, name, desc, dt, venue, seats);
        events.add(e);
        return e;
    }

    public void deleteEvent(int id) {
        events.removeIf(e -> e.getId() == id);
    }

    public List<Event> getEvents() {
        return events;
    }

    public boolean register(int eventId) {
        Event e = getEventById(eventId);
        if (e == null || !e.hasAvailableSeats()) return false;

        e.addParticipant(currentUser);
        return true;
    }

    public Event getEventById(int id) {
        for (Event e : events) {
            if (e.getId() == id) return e;
        }
        return null;
    }
}