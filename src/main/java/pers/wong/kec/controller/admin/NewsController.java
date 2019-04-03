package pers.wong.kec.controller.admin;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestDTO.NewsDTO;
import pers.wong.kec.service.NewsService;

/**
 * @author Wangjunwei
 * @Date 1/25/2019 4:14 PM
 * @Description
 */

@RestController
@RequestMapping("/news")
public class NewsController {

  @Resource
  private NewsService newsService;

  //新增板块
  @PostMapping("/insertNews")
  public Result insertNews(@RequestBody @Valid NewsDTO newsDTO) {
    return newsService.insertNews(newsDTO);
  }

  @PostMapping("/getNewsList")
  public Result getNewsList(@RequestBody NewsDTO newsDTO) {
    return newsService.getNewsList(newsDTO);
  }

  @GetMapping("/deleteNews/{newsId}")
  public Result deleteNews(@PathVariable("newsId") String newsId) {
    return newsService.deleteNews(newsId);
  }

  @GetMapping("/getNewsDetail/{newsId}")
  public Result getNewsDetail(@PathVariable("newsId") String newsId) {
    return newsService.getNewsDetail(newsId);
  }
}
