package group.debug.rss.repo;

import group.debug.rss.model.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
    // 根据 feedUrl 查找 RSS 源
    Optional<RssFeed> findByFeedUrl(String feedUrl);
}
