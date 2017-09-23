package com.yuxie.tgd.task;

import com.yuxie.tgd.mymapper.ObjectDetailMapper;
import com.yuxie.tgd.mymapper.ObjectLinkMapper;
import com.yuxie.tgd.mymapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 这是用来将用户持有的任务中的奖池积累的资源转移到用户账户中的一个定时任务
 * Created by 御 on 2017/5/19 0019.
 */
@Component
public class GetResourcePoolToOwner {

    private static final Logger log= LoggerFactory.getLogger(GetResourcePoolToOwner.class);

    @Autowired
    private ObjectLinkMapper objectLinkMapper;
    @Autowired
    private ObjectDetailMapper objectDetailMapper;
    @Autowired
    private UserMapper userMapper;

    @Scheduled(cron = "0 0 0 * * ?")
    public void getResourcePoolToOwner() {
        log.info("************定时任务开始*****************");
        Long ownerId = null;
        Long maxDetailId = null;
        Integer userResource = null;
        Integer resource = null;
        log.info("************开始获取任务ID列表*****************");
        List<Long> taskIdList = objectLinkMapper.getTaskIdListByType(1);
        log.info("************获取任务ID列表结束*****************");
        if (taskIdList != null) {
            //任务ID列表不为空
            log.info("************进入任务ID列表循环*****************");
            for (Long taskId : taskIdList) {
                try {
                    maxDetailId = objectLinkMapper.getMaxDetailId(taskId);
                    ownerId = objectDetailMapper.getUserIdByDetailId(maxDetailId);
                    resource = objectDetailMapper.getObjectResourcePool(maxDetailId);
                    userResource = userMapper.getUserResource(ownerId);
                    userMapper.updateUserResource(ownerId , resource + userResource);
                    objectDetailMapper.updateObjectResourcePool(-1 , maxDetailId);
                }catch (Exception e) {
                    log.error("！！！！！转移任务奖池资源到用户账户出现异常！！！！！");
                    continue;
                }
            }
            log.info("************任务ID列表循环完毕*****************");
        }
        log.info("************定时任务结束*****************");
    }
}
