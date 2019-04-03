package pers.wong.kec.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.enums.KecAllEnum;
import pers.wong.kec.dao.dao.PostMapper;
import pers.wong.kec.domain.responseDTO.PopularPostResponseDTO;
import pers.wong.kec.service.PostService;

/**
 * @author Wangjunwei
 * @Date 2019/3/29 15:06
 * @Description 定时器配置类
 */

@Configuration
@EnableScheduling
public class ScheduleConfig {

  private static final Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


  @Resource
  PostMapper postMapper;

  /**
   * 【热门主贴定时任务】
   * https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
   * fixedRate：固定速率执行
   * fixedDelay：以固定延迟的每一段时间调用方法，这意味着该周期是从每个前一次调用的完成时间开始测量的
   * cron 即CronSequenceGenerator，精确的时间
   */
//  @Scheduled(fixedDelay = 100000)
  @Scheduled(cron = "0 0 0 * * *")
//  @Scheduled(fixedRate = 1000 * 5)
  public void popularPostSchedule() {
    log.info("热门主贴定时任务：开始时间 {}", dateFormat.format(new Date()));
    List<PopularPostResponseDTO> popularList = postMapper.popularPostList();
    //获取原本的热帖并将其设为普通
    List<String> oldPopular = postMapper.selectOldHot();
    int size = 0;
    List<String> newPopular = new ArrayList<>();
    int o = postMapper.updatePostType(oldPopular, KecAllEnum.POST_TYPE_NORMAL.getCode());
    if (null == popularList || popularList.size() == 0) {
      log.info("热门主贴定时任务完成，没有符合的主贴");
    } else {
      //计算分数，倒序排序，获取前一百名设为热帖
      IntStream.range(0, popularList.size()).forEach(i -> {
        double score = CommonUtil
            .getPopularScore(popularList.get(i).getFollow(), popularList.get(i).getHours());
        popularList.get(i).setScore(score);
      });
      popularList.sort(Comparator.comparing(PopularPostResponseDTO::getScore).reversed());
      if (popularList.size() < 100) {
        size = popularList.size();
      } else {
        size = 100;
      }
      IntStream.range(0, size).forEach(i ->{
        if (popularList.get(i).getScore() > 0.0) {
          newPopular.add(popularList.get(i).getPostId());
        }
      });
      int i1 = postMapper.updatePostType(newPopular, KecAllEnum.POST_TYPE_POPULAR.getCode());
      //TODO
    }
    log.info("热门主贴定时任务：结束时间 {}", dateFormat.format(new Date()));
    log.info("============================================================");
  }
}
