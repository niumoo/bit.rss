package group.debug.rss.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bit_rss_entry")
public class RssEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rss_feed_id", nullable = false)
    private Long rssFeedId; // 关联的 RSS 源

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    private String link;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String author;

    @Column(name = "entry_create_time", nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime entryCreateTime;

    @Column(name = "entry_update_time", nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime entryUpdateTime;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content; // 详细内容

    @Column(name = "content_md5", nullable = false)
    private String contentMd5; // 内容的 MD5

    @Column(name = "created_time", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    @PrePersist
    public void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedTime = LocalDateTime.now();
    }
}
