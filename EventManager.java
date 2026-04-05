package EventRegistration;
import java.util.*;

public class EventManager {
    private static EventManager instance = new EventManager();

    private UserDAO         userDAO         = new UserDAO();
    private EventDAO        eventDAO        = new EventDAO();
    private RegistrationDAO registrationDAO = new RegistrationDAO();

    private User currentUser;

    private EventManager() {}

    public static EventManager getInstance() { return instance; }

    public boolean registerUser(User user) {
        if ("ADMIN".equals(user.getRole())) return false;
        return userDAO.registerUser(user);
    }

    public User login(String username, String pw) {
        currentUser = userDAO.login(username, pw);
        return currentUser;
    }

    public Event createEvent(String name, String desc, String dt, String venue, int seats) {
        Event e = new Event(0, name, desc, dt, venue, seats);
        eventDAO.createEvent(e);
        return e;
    }

    public boolean updateEvent(int id, String name, String desc, String dt, String venue, int seats) {
        return eventDAO.updateEvent(id, name, desc, dt, venue, seats);
    }

    public void deleteEvent(int id) {
        registrationDAO.deleteByEvent(id); // remove registrations first
        eventDAO.deleteEvent(id);
    }

    public List<Event> getEvents() {
        List<Event> events = eventDAO.getEvents();
        for (Event e : events) {
            List<User> participants = registrationDAO.getParticipants(e.getId());
            for (User u : participants) e.addParticipant(u);
        }
        return events;
    }

    /** Register a user for an event by event ID. Returns true if successful. */
    public boolean register(User user, int eventId) {
        List<Event> events = getEvents();
        Event target = events.stream().filter(e -> e.getId() == eventId).findFirst().orElse(null);
        if (target == null || !target.hasAvailableSeats()) return false;
        boolean alreadyRegistered = target.getParticipants().stream()
            .anyMatch(u -> u.getUsername().equals(user.getUsername()));
        if (alreadyRegistered) return false;
        return registrationDAO.register(user.getUsername(), eventId);
    }

    /** Unregister a user from an event. Returns true if successful. */
    public boolean unregister(User user, int eventId) {
        return registrationDAO.unregister(user.getUsername(), eventId);
    }

    // Legacy compatibility
    public boolean register(int eventId) {
        if (currentUser == null) return false;
        return register(currentUser, eventId);
    }
}
