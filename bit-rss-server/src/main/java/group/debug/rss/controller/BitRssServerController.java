package group.debug.rss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niulang
 * @date 2024/12/18
 */
@RestController
public class BitRssServerController {

    @GetMapping("/")
    public String index() {
        return "Hi, Welcome to Bit RSS Server!";
    }
}
