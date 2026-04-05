package EventRegistration;
import java.sql.*;
import java.util.*;

public class RegistrationDAO {

    /** Register a user for an event. Returns true if successful. */
    public boolean register(String username, int eventId) {
        String sql = "INSERT INTO registrations(username, event_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, eventId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Unregister a user from an event. Returns true if successful. */
    public boolean unregister(String username, int eventId) {
        String sql = "DELETE FROM registrations WHERE username = ? AND event_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, eventId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Returns all users registered for a given event. */
    public List<User> getParticipants(int eventId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.name, u.email, u.username, u.password, u.role " +
                     "FROM registrations r JOIN users u ON r.username = u.username " +
                     "WHERE r.event_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                    rs.getString("name"), rs.getString("email"),
                    rs.getString("username"), rs.getString("password"),
                    rs.getString("role")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return users;
    }

    /** Delete all registrations for a given event . */
    public void deleteByEvent(int eventId) {
        String sql = "DELETE FROM registrations WHERE event_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
