package group.debug.rss.repo;

import group.debug.rss.model.RssEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RssEntryRepository extends JpaRepository<RssEntry, Long> {
    // 根据 rssFeedId 查询所有的 RSS 条目
    List<RssEntry> findByRssFeedId(Long rssFeedId);
    // 根据 link 查询唯一的 rss 条目
    Optional<RssEntry> findByLink(String link);
}
