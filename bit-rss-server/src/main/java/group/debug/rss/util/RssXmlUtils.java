package group.debug.rss.util;

import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import group.debug.rss.model.RssEntry;
import group.debug.rss.model.RssFeed;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class RssXmlUtils {
    public static void main(String[] args) {
        //String s = HttpUtil.get("https://www.zhihu.com/rss");
        //System.out.println(s);
        //RssFeed rssFeed = getRssFeed("https://www.zhihu.com/rss");
        List<RssEntry> rssFeedEntry = getRssFeedEntry("https://www.ruanyifeng.com/blog/atom.xml");
        for (RssEntry rssEntry : rssFeedEntry) {
            System.out.println(rssEntry.getTitle());
        }
    }
    public static List<RssEntry> getRssFeedEntry(String url) {
        log.info("getRssFeedEntry url:{}", url);
        List<RssEntry> rssEntryList = new ArrayList<>();
        try {
            URL feedUrl = new URL(url);

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new InputStreamReader(feedUrl.openStream()));
            List<SyndEntry> entries = feed.getEntries();
            for (SyndEntry entry : entries) {
                RssEntry rssEntry = new RssEntry();
                rssEntry.setTitle(entry.getTitle());
                rssEntry.setLink(entry.getLink());
                rssEntry.setDescription(entry.getDescription().getValue());
                if (entry.getContents() != null && !entry.getContents().isEmpty()) {
                    rssEntry.setContent(entry.getContents().get(0).getValue());
                } else if (rssEntry.getDescription() != null){
                    rssEntry.setContent(rssEntry.getDescription());
                    String desc = StringUtils.substring(rssEntry.getDescription(), 0, 100);
                    rssEntry.setDescription(desc.replaceAll("<[^>]*>", ""));
                }
                rssEntry.setContentMd5(DigestUtils.md5Hex(rssEntry.getContent()));
                // 发布时间
                Date publishedDate = entry.getPublishedDate();
                Date updatedDate = entry.getUpdatedDate();
                ZoneId zoneId = ZoneId.systemDefault();
                if (publishedDate != null) {
                    rssEntry.setEntryCreateTime(LocalDateTime.ofInstant(publishedDate.toInstant(), zoneId));
                }
                if (updatedDate != null) {
                    rssEntry.setEntryUpdateTime(LocalDateTime.ofInstant(updatedDate.toInstant(), zoneId));
                }
                // author
                if (entry.getAuthors() != null && entry.getAuthors().size() > 0) {
                    rssEntry.setAuthor(entry.getAuthors().get(0).getName());
                }
                rssEntryList.add(rssEntry);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rssEntryList;
    }

    public static RssFeed getRssFeed(String url) {
        log.info("getRssFeed url:{}", url);
        try {
            URL feedUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new InputStreamReader(feedUrl.openStream()));
            RssFeed rssFeed = new RssFeed();
            rssFeed.setFeedUrl(url);
            rssFeed.setTitle(feed.getTitle());
            rssFeed.setDescription(feed.getDescription());
            rssFeed.setLastCheckTime(LocalDateTime.now());
            return rssFeed;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
