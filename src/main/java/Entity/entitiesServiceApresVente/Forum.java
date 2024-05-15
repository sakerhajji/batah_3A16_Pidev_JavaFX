package Entity.entitiesServiceApresVente;

import java.time.LocalDate;

public class Forum {
    private Integer id;
    private String title;
    private String content;
    private LocalDate createdAt;
    private String username;
    private Double prix;

    public Forum() {
    }

    // Constructor with all parameters
    public Forum(Integer id, String title, String content, LocalDate createdAt, String username, Double prix) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.username = username;
        this.prix = prix;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", username='" + username + '\'' +
                ", prix=" + prix +
                '}';
    }
}
