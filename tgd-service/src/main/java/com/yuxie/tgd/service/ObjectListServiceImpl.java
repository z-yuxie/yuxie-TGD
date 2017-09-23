package com.yuxie.tgd.service;

import com.yuxie.tgd.common.util.LoginValidation;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.mymapper.ObjectDetailMapper;
import com.yuxie.tgd.mymapper.ObjectLinkMapper;
import com.yuxie.tgd.mymapper.ObjectUserStatusMapper;
import com.yuxie.tgd.mymapper.UserMapper;
import com.yuxie.tgd.pojo.bean.ObjectDetail;
import com.yuxie.tgd.pojo.bean.ObjectLink;
import com.yuxie.tgd.pojo.dto.ListParamDTO;
import com.yuxie.tgd.pojo.dto.ObjectIdDTO;
import com.yuxie.tgd.pojo.dto.SelectObjectList;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.ObjectBaseVO;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取各种对象列表
 * Created by 147356 on 2017/4/10.
 */
@Service
public class ObjectListServiceImpl implements IObjectListService {

    private static final Logger log= LoggerFactory.getLogger(ObjectListServiceImpl.class);

    //每页对象数量
//    @Value("${pageObjectNum}")
    private Integer pageObjectNum = 20;

    @Autowired
    private ObjectUserStatusMapper objectUserStatusMapper;
    @Autowired
    private ObjectLinkMapper objectLinkMapper;
    @Autowired
    private ObjectDetailMapper objectDetailMapper;
    @Autowired
    private UserLevelService userLevelService;
    @Autowired
    private LoginValidation loginValidation;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取对象基本信息List的service
     * @param listParamDTO 包含要获取的列表的信息
     * @return List<ObjectBaseVO>
     * @throws Exception 可能发生的redis,sql等异常
     */
    @Override
    public List<ObjectBaseVO> getObjectList(ListParamDTO listParamDTO) throws Exception {
        log.info("======成功进入获取对象基本信息列表的service======");
        List<ObjectIdDTO> objectIdDTOList;
        Long detailId;
        List<Long> objectIdList;
        //通过三目运算符来赋值,以解决可能出现的空指针异常等问题
        //排序规则
        int sortType = ((listParamDTO.getSortType() == null) ? 0 : listParamDTO.getSortType());
        //列表类型
        int listType = ((listParamDTO.getListType() == null) ? 0 : listParamDTO.getListType());
        //页码
        int pageNum = ((listParamDTO.getPageNum() == null) ? 0 : (listParamDTO.getPageNum() - 1));
        //用户ID
        Long userId = listParamDTO.getUserId();
        //父对象ID
        Long parentObjectId = listParamDTO.getObjectId();
        //获取对象类型
        Integer objectType = listParamDTO.getObjectType();
        objectType = (objectType == null) ? 0 : objectType;
        //父对象版本号
        Integer parentObjectVersion = listParamDTO.getObjectVersion();
        //会话ID
        String sessionId = (StringUtils.isBlank(listParamDTO.getSessionId()) ? null :listParamDTO.getSessionId());
        //关键词
        String keyWord = (StringUtils.isBlank(listParamDTO.getSeachWord()) ? null :listParamDTO.getSeachWord());
        //
        //验证用户登录状态及权限等级
        UserSessionDTO userSessionDTO = new UserSessionDTO();
        userSessionDTO.setUserId(userId);
        userSessionDTO.setSessionId(sessionId);
        Integer loginStatus = loginValidation.validation(userId , sessionId);
        Integer userIsMannager = userLevelService.mannagerValidation(userSessionDTO);
        try {
            //如果用户未登录,或为普通用户但操作不合法
            if ((loginStatus != 1 || userIsMannager.equals(MyConstans.USER_LEVEL_NUM)) && (listType != 0 || listType != 3)) {
                listType = 0;
            }
            if (loginStatus != 1 && listType == 3) {//若用户未登录且想要获取收藏的对象
                listType = 0;
            }
            if ((parentObjectId == null || parentObjectVersion == null) && objectType == 3) {
                return null;
            }
            SelectObjectList selectObjectList = new SelectObjectList();
            selectObjectList.setUserId(userId);
            selectObjectList.setParentObjectId(parentObjectId);
            selectObjectList.setParentObjectVersion(parentObjectVersion);
            selectObjectList.setListType(listType);
            selectObjectList.setSortType(sortType);
            selectObjectList.setObjectType(objectType);
            if (listType == 3) {//如果用户获取的是收藏的对象的列表
                objectIdDTOList = new ArrayList<>();
                objectIdList = objectUserStatusMapper.getObjectIdListByUserIdAndStatus(userId , 0 , 1);
                for (Long objectIdTmp : objectIdList) {
                    try {
                        ObjectIdDTO objectIdDTO = objectLinkMapper.getObjectIdDTOByObjectId(objectIdTmp);
                        objectIdDTOList.add(objectIdDTO);
                    } catch (Exception e) {
                        //如果循环过程中发生异常,直接捕获,日志输出错误信息后跳过,以免无法获取后续的对象信息
                        log.error("========获取对象"+objectIdTmp+"的IDDTO信息出错======="+e.toString());
                        continue;
                    }
                }
            } else {
                objectIdDTOList = objectLinkMapper.getObjectIdDTOList(selectObjectList);
            }
            if (objectIdDTOList == null) {
                log.debug("========未获取到对象列表所需的对象ID列表========");
                return null;
            }
            if (objectIdDTOList.isEmpty()) {
                log.debug("========未获取到对象列表所需的对象ID列表========");
                return null;
            }
        } catch (Exception e) {
            log.error("======获取对象ID列表失败======="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        List<ObjectBaseVO> objectBaseVOList = new ArrayList<ObjectBaseVO>();
        int i = 1;//分页用计数器,每成功天界一个对象,自加一
        log.info("=======开始通过对象ID获取对象基本信息并添加进列表中===========");
        for (ObjectIdDTO objectIdDTO :objectIdDTOList) {
            if (objectType != 3) {
                if (StringUtils.isBlank(keyWord)) {//不包含关键词的情况下，一个ID即代表一个对象的基本信息对象，所以跳过一个ID即跳过一个对象
                    if (i <= (pageNum * pageObjectNum)) {//还未到达指定页数
                        i++;
                        continue;
                    }
                }
                if (i > ((pageNum + 1) * pageObjectNum)) {//对象列表中对象数量已到达一页的数量
                    log.info("========指定页中的对象基本信息列表获取完毕========");
                    break;
                }
            }
            try {
                Long objectId = objectIdDTO.getObjectId();
                ObjectBaseVO objectBaseVO =new ObjectBaseVO();//此处是否存在改进方式？
                Long missionId = objectLinkMapper.getTaskIdByObjectId(objectId);
                if (missionId != null) {//该对象包含任务
                    Integer status = objectUserStatusMapper.getPassStatusNum(userId , missionId);
                    if (!status.equals(MyConstans.SUCCESS_STATUS_NUM)) {//如果用户不可跳过该对象持有的任务,则替换对象ID为该对象的任务ID
                        objectBaseVO.setParentObjectId(objectId);
                        objectIdDTO.setObjectId(missionId);
                    }
                }
                detailId = objectLinkMapper.getMaxDetailId(objectIdDTO.getObjectId());
                //如果未获取到详情ID,则选择跳过
                if (detailId == null) {
                    log.debug("=========获取通过对象ID对象最新详情ID失败=============");
                    continue;
                }
                ObjectDetail objectDetail = objectDetailMapper.getObjectDetailById(detailId);
                //设置对象ID
                objectBaseVO.setObjectId(objectIdDTO.getObjectId());
                //设置对象类型
                objectBaseVO.setObjectType(objectIdDTO.getObjectType());
                //设置对象创建者ID
                objectBaseVO.setUserId(objectIdDTO.getUserId());
                //设置创建者名称
                objectBaseVO.setUserName(userMapper.getUserNameById(objectIdDTO.getUserId()));
                //提取需要的剩余部分信息
                objectBaseVO = fromObjectDetailToBaseVO(objectDetail , objectBaseVO);
                //设置所有者名称
                objectBaseVO.setOwnerName(userMapper.getUserNameById(objectBaseVO.getOwnerId()));
                //判断搜索关键词是否存在,通过此双层if筛选来实现搜索功能
                if (!StringUtils.isBlank(keyWord)) {
                    log.info("========成功进入搜索关键词筛选============");
                    //如果该对象的标题、简介、创建者名称、所有者名称、标签集均不包含该关键词,则跳过此次循环
                    if (!objectBaseVO.getObjectTitle().contains(keyWord)
                            || !objectBaseVO.getObjectIntroduction().contains(keyWord)
                            || !objectBaseVO.getUserName().contains(keyWord)
                            || !objectBaseVO.getOwnerName().contains(keyWord)
                            || !objectBaseVO.getObjectLabelList().contains(keyWord)) {
                        continue;
                    } else if (i <= (pageNum*20 + 1)) {//还未到达指定页数,包含关键词的对象只能在此进行判断才能正确根据有效对象数来进行分页
                        i++;
                        continue;
                    }
                }
                objectBaseVO.setUpTimes(objectUserStatusMapper.getUpTimes(objectId));
                objectBaseVO.setDownTimes(objectUserStatusMapper.getDownTimes(objectId));
                log.info("========向List中添加了一个对象基本信息包装类对象============");
                //将该对象基本信息的包装类添加到list中
                objectBaseVOList.add(objectBaseVO);
                i++;
            } catch (Exception e) {
                //如果循环过程中发生异常,直接捕获,日志输出错误信息后跳过,以免无法获取后续的对象信息
                log.error("========获取对象"+objectIdDTO.getObjectId()+"的信息出错======="+e.toString());
                continue;
            }
        }
        return objectBaseVOList;
    }


    /**
     * 从objectDetail中提取出objectBaseVO需要的数据到objectBaseVO中
     * @param objectDetail 包含某一对象某一版本的详细信息的包装类
     * @param objectBaseVO 对象的基本信息类
     * @return objectBaseVO 对象的基本信息类,主要用于返回对象基本信息列表
     */
    private ObjectBaseVO fromObjectDetailToBaseVO(ObjectDetail objectDetail , ObjectBaseVO objectBaseVO) {
        //对象详情ID
        objectBaseVO.setDetailId(objectDetail.getObjectDetailId());
        //所有者ID
        objectBaseVO.setOwnerId(objectDetail.getUserId());
        //对象标题
        objectBaseVO.setObjectTitle(objectDetail.getObjectTitle());
        //对象简介
        objectBaseVO.setObjectIntroduction(objectDetail.getObjectIntroduction());
        //对象包含的标签集
        objectBaseVO.setObjectLabelList(objectDetail.getObjectLabelList());
        //对象状态
        objectBaseVO.setStatus(objectDetail.getObjectStatus());
        //任务奖池积累数
        objectBaseVO.setResourcePool(objectDetail.getResourcePool());
        //设置参与用户的数量
        objectBaseVO.setInvolvedNum(objectDetail.getInvolvedNum());
        return objectBaseVO;
    }
}
