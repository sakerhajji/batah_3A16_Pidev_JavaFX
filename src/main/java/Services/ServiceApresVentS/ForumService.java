package Services.ServiceApresVentS;


import DataBaseSource.DataSource;
import Entity.entitiesServiceApresVente.Forum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ForumService  {

    private Connection cnx;

    public ForumService() {
        cnx = DataSource.getInstance().getCnx();
    }
    public int getForumCount() {
        String query = "SELECT COUNT(*) AS count FROM forum";
        try (PreparedStatement pst = cnx.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving forum count: " + ex.getMessage());
        }
        return 0; // Return zero if there's an exception
    }
    public List<Forum> getForums(int start, int count) {
        List<Forum> forums = new ArrayList<>();
        String query = "SELECT * FROM forum ORDER BY created_at DESC LIMIT ? OFFSET ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, count);
            pst.setInt(2, start);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Forum forum = new Forum();
                    forum.setId(rs.getInt("id"));
                    forum.setTitle(rs.getString("title"));
                    forum.setContent(rs.getString("content"));
                    forum.setCreatedAt(rs.getDate("created_at").toLocalDate());
                    forum.setUsername(rs.getString("username"));
                    forum.setPrix(rs.getDouble("prix"));
                    forums.add(forum);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving forums for pagination: " + ex.getMessage());
        }
        return forums;
    }

    public void create(Forum forum) {
        String query = "INSERT INTO forum (title, content, created_at, username, prix) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, forum.getTitle());
            pst.setString(2, forum.getContent());
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Set created_at to the current date
            pst.setString(4, forum.getUsername());
            pst.setDouble(5, forum.getPrix());
            pst.executeUpdate();
            System.out.println("Forum entry created successfully");
        } catch (SQLException ex) {
            System.err.println("Error adding forum entry: " + ex.getMessage());
        }
    }


    public void update(Forum forum) {
        String query = "UPDATE forum SET title=?, content=?, created_at=?, username=?, prix=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, forum.getTitle());
            pst.setString(2, forum.getContent());
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Update created_at to the current date
            pst.setString(4, forum.getUsername());
            pst.setDouble(5, forum.getPrix());
            pst.setInt(6, forum.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Forum entry updated successfully");
            } else {
                System.out.println("No forum entry found with ID: " + forum.getId());
            }
        } catch (SQLException ex) {
            System.err.println("Error updating forum entry: " + ex.getMessage());
        }
    }


    public void delete(int id) {
        String query = "DELETE FROM forum WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Forum entry deleted successfully");
            } else {
                System.out.println("No forum entry found with ID: " + id);
            }
        } catch (SQLException ex) {
            System.err.println("Error deleting forum entry: " + ex.getMessage());
        }
    }


    public Forum getById(int id) {
        String query = "SELECT * FROM forum WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Forum forum = new Forum();
                    forum.setId(rs.getInt("id"));
                    forum.setTitle(rs.getString("title"));
                    forum.setContent(rs.getString("content"));
                    forum.setCreatedAt(rs.getDate("created_at").toLocalDate());
                    forum.setUsername(rs.getString("username"));
                    forum.setPrix(rs.getDouble("prix"));
                    return forum;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving forum entry: " + ex.getMessage());
        }
        return null;
    }


    public List<Forum> getAll() {
        List<Forum> forumList = new ArrayList<>();
        String query = "SELECT * FROM forum";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Forum forum = new Forum();
                    forum.setId(rs.getInt("id"));
                    forum.setTitle(rs.getString("title"));
                    forum.setContent(rs.getString("content"));
                    forum.setCreatedAt(rs.getDate("created_at").toLocalDate());
                    forum.setUsername(rs.getString("username"));
                    forum.setPrix(rs.getDouble("prix"));
                    forumList.add(forum);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving forum entries: " + ex.getMessage());
        }
        return forumList;
    }
}
