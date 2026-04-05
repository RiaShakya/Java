package EventRegistration;

public class Registration {
    private User user;
    private Event event;

    public Registration(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    public User getUser() { return user; }
    public Event getEvent() { return event; }
}
