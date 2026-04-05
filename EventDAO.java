package EventRegistration;
import java.sql.*;
import java.util.*;

public class EventDAO {

    public boolean createEvent(Event e) {
        String sql = "INSERT INTO events(name, description, datetime, venue, seats) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getDateTime());
            ps.setString(4, e.getVenue());
            ps.setInt(5, e.getTotalSeats());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) { ex.printStackTrace(); return false; }
    }

    /** Update an existing event's details. Returns true if successful. */
    public boolean updateEvent(int id, String name, String desc, String dt, String venue, int seats) {
        String sql = "UPDATE events SET name=?, description=?, datetime=?, venue=?, seats=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setString(3, dt);
            ps.setString(4, venue);
            ps.setInt(5, seats);
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<Event> getEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("datetime"),
                    rs.getString("venue"),
                    rs.getInt("seats")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public void deleteEvent(int id) {
        String sql = "DELETE FROM events WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
