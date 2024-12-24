package group.debug.rss.controller;

import java.util.List;

import com.google.common.base.Preconditions;
import group.debug.rss.service.RssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niulang
 * @date 2024/12/22
 */
@Slf4j
@RestController
public class RssController {

    @Autowired
    private RssService rssService;

    /**
     * add a feed
     */
    @GetMapping(value = "/api/v1/rss/init")
    public String init() {
        List.of("https://www.ruanyifeng.com/blog/atom.xml",
                "https://www.zhihu.com/rss")
            .forEach(url -> {
                rssService.createFeed(url);
                rssService.checkRssEntryUpdate();
            });
        return "success";
    }

    /**
     * add a feed
     */
    @GetMapping(value = "/api/v1/rss/feed/add")
    public String addFeed(String url) {
        Preconditions.checkArgument(url != null && !url.isEmpty());
        rssService.createFeed(url);
        return "success";
    }

    @GetMapping(value = "/api/v1/rss/feed/update")
    public String updateFeed(String url) {
        Preconditions.checkArgument(url != null && !url.isEmpty());
        rssService.updateFeed(url);
        return "success";
    }

    @GetMapping(value = "/api/v1/rss/entry/update")
    public String checkRssEntryUpdate() {
        rssService.checkRssEntryUpdate();
        return "success";
    }
}
