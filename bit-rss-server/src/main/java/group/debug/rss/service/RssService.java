package group.debug.rss.service;

import java.util.List;
import java.util.Optional;

import group.debug.rss.model.RssEntry;
import group.debug.rss.model.RssFeed;
import group.debug.rss.repo.RssEntryRepository;
import group.debug.rss.repo.RssFeedRepository;
import group.debug.rss.util.RssXmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author niulang
 * @date 2024/12/22
 */
@Slf4j
@Service
public class RssService {

    @Autowired
    private RssEntryRepository rssEntryRepository;

    @Autowired
    private RssFeedRepository rssFeedRepository;

    public void checkRssEntryUpdate() {
        List<RssFeed> rssFeedList = rssFeedRepository.findAll();
        for (RssFeed rssFeed : rssFeedList) {
            // 获取 feedUrl 内容
            List<RssEntry> rssEntryList = RssXmlUtils.getRssFeedEntry(rssFeed.getFeedUrl());
            for (RssEntry rssEntry : rssEntryList) {
                // 校验 rssEntry 是否存在
                Optional<RssEntry> existingEntry = rssEntryRepository.findByLink(rssEntry.getLink());
                if (existingEntry.isPresent()) {
                    updateEntry(existingEntry.get().getId(), rssEntry);
                } else {
                    rssEntry.setRssFeedId(rssFeed.getId());
                    // 如果不存在，则新增
                   createEntry(rssEntry);
                }
            }
        }
    }

    // 新增 RSS 条目
    public RssEntry createEntry(RssEntry rssEntry) {
        return rssEntryRepository.save(rssEntry);
    }

    // 更新现有的 RSS 条目
    public void updateEntry(Long id, RssEntry updatedEntry) {
        // 查找已有的 RSS 条目
        Optional<RssEntry> existingEntry = rssEntryRepository.findById(id);
        if (existingEntry.isPresent()) {
            if (!existingEntry.get().getContentMd5().equals(updatedEntry.getContentMd5())) {
                RssEntry entryToUpdate = existingEntry.get();
                // 更新字段
                entryToUpdate.setTitle(updatedEntry.getTitle());
                entryToUpdate.setDescription(updatedEntry.getDescription());
                entryToUpdate.setAuthor(updatedEntry.getAuthor());
                entryToUpdate.setContent(updatedEntry.getContent());
                entryToUpdate.setContentMd5(updatedEntry.getContentMd5());
                // 保存更新后的实体
                rssEntryRepository.save(entryToUpdate);
            } else {
                log.info("RSS Entry not updated with id: " + id);
            }
        } else {
            log.error("RSS Entry not found with id: " + id);
            throw new RuntimeException("RSS Entry not found with id: " + id);
        }
    }

    // 新增 RSS 源
    public RssFeed createFeed(String url) {
        Optional<RssFeed> existingFeed = rssFeedRepository.findByFeedUrl(url);
        if (existingFeed.isPresent()) {
            log.info("RSS Feed already exists with url: " + url);
            return existingFeed.get();
        }
        RssFeed rssFeed = RssXmlUtils.getRssFeed(url);
        RssFeed saved = rssFeedRepository.save(rssFeed);
        log.info("RSS Feed created with url: " + url);
        return saved;
    }

    // 更新现有的 RSS 源
    public void updateFeed(String url) {
        RssFeed rssFeed = RssXmlUtils.getRssFeed(url);
        // 查找已有的 RSS 源
        Optional<RssFeed> existingFeed = rssFeedRepository.findByFeedUrl(url);
        if (existingFeed.isPresent()) {
            if (!rssFeed.getTitle().equals(existingFeed.get().getTitle())) {
                RssFeed feedToUpdate = existingFeed.get();
                // 更新字段
                feedToUpdate.setTitle(rssFeed.getTitle());
                feedToUpdate.setDescription(rssFeed.getDescription());
                rssFeedRepository.save(feedToUpdate);
                log.info("RSS Feed updated with url: " + url);
            }
        } else {
            log.error("RSS Feed not found with url: " + url);
            throw new RuntimeException("RSS Feed not found with url: " + url);
        }
    }
}
