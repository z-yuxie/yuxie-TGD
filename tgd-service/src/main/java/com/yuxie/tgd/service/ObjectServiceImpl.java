package com.yuxie.tgd.service;


import com.yuxie.tgd.common.util.LoginValidation;
import com.yuxie.tgd.common.util.MyConstans;
import com.yuxie.tgd.common.util.MySQLException;
import com.yuxie.tgd.mymapper.*;
import com.yuxie.tgd.pojo.bean.*;
import com.yuxie.tgd.pojo.dto.ObjectDetailDTO;
import com.yuxie.tgd.pojo.dto.UserSessionDTO;
import com.yuxie.tgd.pojo.vo.ObjectDetailVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 其他小型对对象操作
 * Created by 147356 on 2017/4/10.
 */
@Service
public class ObjectServiceImpl implements IObjectService{

    private static final Logger log= LoggerFactory.getLogger(ObjectServiceImpl.class);

    //持有者正确分数标准(10分满分)
    @Value(value = "${ownerNum}")
    private Integer ownerNum;
    //参阅者正确分数标准
    @Value(value = "${viewerNum}")
    private Integer viewerNum;

    @Autowired
    private ObjectDetailMapper objectDetailMapper;
    @Autowired
    private ObjectLinkMapper objectLinkMapper;
    @Autowired
    private ObjectUserStatusMapper objectUserStatusMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMessageMapper objectMessageMapper;
    @Autowired
    private LabelListMapper labelListMapper;
    @Autowired
    private LoginValidation loginValidation;
    @Autowired
    private UserLevelService userLevelService;

    /**
     * 根据对象ID获取对象的详细信息
     * @param userSessionDTO 包含对象ID等信息
     * @return 对象的详细信息
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public ObjectDetailVO getObjectDetail(UserSessionDTO userSessionDTO) throws Exception{
        ObjectDetailVO objectDetailVO = new ObjectDetailVO();
        ObjectDetail objectDetail;
        Integer objectType;
        String userName;
        String objectMessage;
        //用户ID
        Long userId = userSessionDTO.getUserId();
        //会话ID
        String sessionId = userSessionDTO.getSessionId();
        //详情ID
        Long objectId = userSessionDTO.getObjectId();
        //对象详情ID
        Long detailId;
        try {
            detailId = objectLinkMapper.getMaxDetailId(objectId);
        } catch (Exception e) {
            log.error("============获取对象详情ID失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        if (detailId == null) {
            return null;
        }
        if (userId !=null && StringUtils.isNotBlank(sessionId)) {
            int loginStatus = loginValidation.validation(userId , sessionId);
            if (loginStatus == MyConstans.SUCCESS_STATUS_NUM) {
                try {
                    List<Integer> userObjectStatus = objectUserStatusMapper.getStatusNum(userId , objectId);
                    objectDetailVO.setObjectUserStatus(userObjectStatus);
                } catch (Exception e) {
                    log.error("============获取用户与对象间状态信息失败============="+e.toString());
                    throw new MySQLException(MyConstans.SQL_DATE_FAIL);
                }
            }
        }
        try {
            objectDetail = objectDetailMapper.getObjectDetailById(detailId);
            objectType = objectLinkMapper.getObjectTypeByObjectId(objectId);
            if (objectDetail !=null) {
                userName = userMapper.getUserNameById(objectDetail.getUserId());
                objectMessage = objectMessageMapper.getObjectMessageById(objectDetail.getObjectMessageId());
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("============获取用户详细信息失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(objectMessage) && objectType != null) {
            objectDetailVO = fromDetailToVO(objectDetail , objectDetailVO);
            objectDetailVO.setUpTimes(objectUserStatusMapper.getUpTimes(objectId));
            objectDetailVO.setDownTimes(objectUserStatusMapper.getDownTimes(objectId));
            objectDetailVO.setObjectType(objectType);
            objectDetailVO.setUserName(userName);
            objectDetailVO.setObjectMessage(objectMessage);
            return objectDetailVO;
        }else {
            return null;
        }
    }

    /**
     * 更新对象详情
     * @param objectDetailDTO 包含对象详细信息的对象
     * @return 是否成功
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Integer updateObject(ObjectDetailDTO objectDetailDTO) throws Exception{
        Integer loginStatus = loginValidation.validation(objectDetailDTO.getUserId() , objectDetailDTO.getSessionId());
        if(!loginStatus.equals(MyConstans.SUCCESS_STATUS_NUM)){
            return -5;
        }
        Long objectId = objectDetailDTO.getObjectId();
        Integer objectType = objectDetailDTO.getObjectType();
        String objectMessage = objectDetailDTO.getObjectMessage();
        Long objectDetailId;
        Long objectMessageId;
        try {
            objectDetailId = objectLinkMapper.getMaxDetailId(objectId);
            objectMessageId = objectDetailMapper.getMessageId(objectDetailId);
        } catch (Exception e) {
            log.error("============获取对象详情ID或内容ID失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        ObjectDetail objectDetail = new ObjectDetail();
        objectDetail = fromDetailDTOtoDetail(objectDetail , objectDetailDTO , objectType);
        objectDetail.setUserId(objectDetailDTO.getUserId());
        objectDetail.setObjectDetailId(objectDetailId);
        try {
            Integer updateDetailStatus = objectDetailMapper.updateObjectDetailById(objectDetail);
            if (MyConstans.SUCCESS_STATUS_NUM.equals(updateDetailStatus)) {
                Integer updateMessageStatus = objectMessageMapper.updateObjectMessage(objectMessageId , objectMessage);
                if (MyConstans.SUCCESS_STATUS_NUM.equals(updateMessageStatus)) {
                    return updateMessageStatus;
                }
            }
        } catch (Exception e) {
            log.error("============更新对象详情失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        return 0;
    }

    /**
     * 新建某个新对象
     * @param objectDetailDTO 包含对象的详细信息
     * @return 新建的对象的ID
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Long createObject(ObjectDetailDTO objectDetailDTO) throws Exception {
        Integer loginStatus = loginValidation.validation(objectDetailDTO.getUserId() , objectDetailDTO.getSessionId());
        if(!MyConstans.SUCCESS_STATUS_NUM.equals(loginStatus)){
            return null;
        }
        Integer objectType = objectDetailDTO.getObjectType();
        String objectMessageValue = (StringUtils.isBlank(objectDetailDTO.getObjectMessage()) ? "" : objectDetailDTO.getObjectMessage());
        Long userId = objectDetailDTO.getUserId();
        Long objectId = null;
        Long maxDetailId = null;
        Long objectMessageId = null;
        ObjectLink objectLink = new ObjectLink();
        objectLink.setUserId(userId);
        objectLink.setObjectType(objectType);
        objectLink.setParentObjectId(objectDetailDTO.getParentObjectId());
        objectLink.setParentObjetVersion(objectDetailDTO.getParentObjectVersion());
        try {
            objectLinkMapper.insertObjectLink(objectLink);
            objectId = objectLink.getObjectId();
        } catch (Exception e) {
            log.error("============新建对象获取对象唯一ID失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        if (objectId == null || !(objectId >= 1)) {
            return null;
        }
        ObjectDetail objectDetail = new ObjectDetail();
        objectDetail = fromDetailDTOtoDetail(objectDetail , objectDetailDTO , objectType);
        objectDetail.setObjectId(objectId);
        objectDetail.setUserId(userId);
        objectDetail.setObjectVersion(1);
        try {
            if (objectType != 3) {
                //对答案关键词进行处理
                String keyWordValue = objectDetail.getObjectKeyWord();
                if (StringUtils.isNotBlank(keyWordValue)) {
                    List<String> keyWordList = shellStringToList(keyWordValue);
                    if (keyWordList.contains("")) {
                        keyWordList = null;
                    }
                    keyWordValue = "";
                    if (keyWordList != null) {
                        if (!keyWordList.isEmpty()) {
                            for (String key : keyWordList) {
                                keyWordValue = keyWordValue + key + ",";
                            }
                        }
                    }
                    objectDetail.setObjectKeyWord(keyWordValue);
                }
                //对标签进行处理
                String labelListValue = objectDetail.getObjectLabelList();
                List<String> labelList = shellStringToList(labelListValue);
                if (labelList != null) {
                    labelListValue = "";
                    if (!labelList.isEmpty()) {
                        for (String label : labelList) {//该标签存在
                            Long labelId = labelListMapper.getLableIdByName(label);
                            if (labelId == null) {//该标签不存在
                                LabelList labelListTmp = new LabelList();
                                labelListTmp.setUserId(userId);
                                labelListTmp.setLabelName(label);
                                labelListMapper.insertLabel(labelListTmp);
                            } else {
                                labelListMapper.updateLabelHotNumById(labelId , 1);//标签热度值+1
                            }
                        }
                        for (String key : labelList) {
                            labelListValue = labelListValue + key + ",";
                        }
                    }
                    objectDetail.setObjectLabelList(labelListValue);
                }
            }
            objectDetailMapper.insertDetail(objectDetail);
            maxDetailId = objectDetail.getObjectDetailId();
            if (maxDetailId >= 1) {//正常插入了一条详情记录
                ObjectMessage objectMessage = new ObjectMessage();
                objectMessage.setObjectDetailId(maxDetailId);
                objectMessage.setObjectMessageText(objectMessageValue);
                objectMessageMapper.insertMessage(objectMessage);
                objectMessageId = objectMessage.getObjectMessageId();
                if (objectMessageId >= 1) {
                    int status = objectDetailMapper.updateMessageId(maxDetailId , objectMessageId);
                    if (status == MyConstans.SUCCESS_STATUS_NUM) {
                        status = objectLinkMapper.updateDetailId(objectId , maxDetailId);
                        if (status == MyConstans.SUCCESS_STATUS_NUM) {
                            //设置用户与对象状态为所有者
                            ObjectUserStatus objectUserStatus = new ObjectUserStatus();
                            objectUserStatus.setUserId(userId);
                            objectUserStatus.setObjectId(objectId);
                            objectUserStatus.setStatusNum(4);
                            objectUserStatus.setShowNum(1);
                            status = objectUserStatusMapper.insertStatus(objectUserStatus);
                            if (status == MyConstans.SUCCESS_STATUS_NUM){
                                return objectId;
                            }
                        }
                    }
                } else {
                    objectMessageMapper.delByMessageId(objectMessageId);
                    objectDetailMapper.delByDetailId(maxDetailId);
                    objectLinkMapper.delById(objectId);
                }
            } else {//插入详情出现错误,删除已插入的记录
                objectDetailMapper.delByDetailId(maxDetailId);
                objectLinkMapper.delById(objectId);
            }
        } catch (Exception e) {
            log.error("============创建对象的插入过程失败============="+e.toString());
            if (objectMessageId != null) {
                objectMessageMapper.delByMessageId(objectMessageId);
            }
            if (maxDetailId != null) {
                objectDetailMapper.delByDetailId(maxDetailId);
            }
            objectLinkMapper.delById(objectId);
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        return null;
    }

    /**
     * 根据对象ID获取对象当前的状态
     * @param objectId 对象ID
     * @return 对象当前的状态值
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Integer getObjectStatus(Long objectId) throws Exception {
        try {
            Integer objectStatus = objectLinkMapper.getObjectStatus(objectId);
            if (objectStatus != null) {
                return objectStatus;
            } else {
                return MyConstans.FAIL_STATUS_NUM;
            }
        } catch (Exception e) {
            log.error("============根据对象ID获取对象当前状态失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 通过对象ID变更对象当前状态
     * @param userSessionDTO 包含对象ID 用户登录及权限验证等信息
     * @return 对象当前的状态值
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Integer changeObjectStatus(UserSessionDTO userSessionDTO) throws Exception {
        Integer userLevel;
        Integer objectStatus;
        Integer status = 0;
        Integer showNum = null;
        userLevel = userLevelService.mannagerValidation(userSessionDTO);
        try {
            if (!MyConstans.ADD_NUM.equals(userSessionDTO.getStatus()) && !MyConstans.MANNAGER_LEVEL_NUM.equals(userLevel)) {
                objectStatus = MyConstans.FAIL_STATUS_NUM;
                return objectStatus;
            } else {
                objectStatus = objectLinkMapper.getObjectStatus(userSessionDTO.getObjectId());
                if (objectStatus == null) {
                    objectStatus = MyConstans.FAIL_STATUS_NUM;
                    return objectStatus;
                }
                ObjectUserStatus objectUserStatus = new ObjectUserStatus();
                objectUserStatus.setUserId(userSessionDTO.getUserId());
                objectUserStatus.setObjectId(userSessionDTO.getObjectId());
                if (MyConstans.ADD_NUM.equals(userSessionDTO.getStatus())) {//如果是举报操作
                    //检查用户是否已经举报过该对象
                    objectUserStatus.setStatusNum(-2);
                    showNum = objectUserStatusMapper.getShowNum(objectUserStatus);
                    if (showNum == null) {//未举报则设置为举报
                        userSessionDTO.setStatus(objectStatus + 1);
                        objectUserStatus.setShowNum(1);
                    } else if (showNum == 1) {//状态为已举报则撤销举报
                        userSessionDTO.setStatus(objectStatus - 1);
                        objectUserStatus.setShowNum(0);
                        showNum = -1;
                    } else {//未举报则设置为举报
                        userSessionDTO.setStatus(objectStatus + 1);
                        objectUserStatus.setShowNum(1);
                        showNum = 1;
                    }
                    //设置用户在该对象中的举报状态
                    if (showNum == null) {
                        objectUserStatusMapper.insertStatus(objectUserStatus);
                        showNum = 1;
                    } else {
                        objectUserStatusMapper.updateStatus(objectUserStatus);
                    }
                }
                Long detailId = objectLinkMapper.getMaxDetailId(userSessionDTO.getObjectId());
                if (detailId !=null) {
                    if (showNum != null) {//此处判断操作是否为举报操作,是则更新该对象持有者的封禁举报状态,否则直接变更对象的封禁举报状态
                        Long ownerId = objectDetailMapper.getUserIdByDetailId(detailId);
                        Integer ownerStatus = userMapper.getUserStatus(ownerId);//获取所有者当前状态值
                        userMapper.updateUserStatus(ownerId , ownerStatus +showNum);//变更所有者举报封禁状态值
                    }
                    status = objectDetailMapper.updateObjectDetailStatus(detailId , userSessionDTO.getStatus());
                    Long parentObjectId = objectLinkMapper.getParentObjectId(userSessionDTO.getObjectId());
                    Long parentDetailId = objectLinkMapper.getMaxDetailId(parentObjectId);
                    if (status.equals(MyConstans.SUCCESS_STATUS_NUM)) {
                        status = objectLinkMapper.updateObjectStatus(userSessionDTO.getObjectId() , userSessionDTO.getStatus());
                    }
                    if (status.equals(MyConstans.SUCCESS_STATUS_NUM)) {
                        status = objectDetailMapper.updateObjectDetailStatus(parentDetailId , userSessionDTO.getStatus());
                    }
                    if (status.equals(MyConstans.SUCCESS_STATUS_NUM)) {
                        status = objectLinkMapper.updateObjectStatus(parentObjectId , userSessionDTO.getStatus());
                    }
                }
            }
            if (!MyConstans.SUCCESS_STATUS_NUM.equals(status)) {
                objectStatus = MyConstans.FAIL_STATUS_NUM;
            } else {
                objectStatus = userSessionDTO.getStatus();
            }
            return objectStatus;
        } catch (Exception e) {
            log.error("============用户信息操作失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 通过对象和用户ID获取该用户在该对象中的状态值列表
     * @param userId 用户ID
     * @param objectId 对象ID
     * @return 已激活状态值列表
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public List<Integer> getStatusNumList(Long userId, Long objectId) throws Exception{
        try {
            return objectUserStatusMapper.getStatusNum(userId , objectId);
        } catch (Exception e) {
            log.error("============获取状态值列表失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
    }

    /**
     * 变更用户在对象中的状态值,以及参加任务等
     * @param userSessionDTO 包含用户对象ID,会话ID,答案等信息
     * @return 变更成功则返回1,赞踩需减1则返回-1,所有者2,-5资源不足
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Integer changeStatusNum(UserSessionDTO userSessionDTO) throws Exception {
        Long userId = userSessionDTO.getUserId();
        Long objectId = userSessionDTO.getObjectId();
        Integer status = userSessionDTO.getStatus();
        Integer showNum = 0;
        Integer sqlNum = 0;//用于获取数据库的返回值的临时变量
        Integer sqlNum2 = 1;
        Boolean changeOwner = Boolean.FALSE;
        ObjectUserStatus objectUserStatus = new ObjectUserStatus();
        objectUserStatus.setUserId(userId);
        objectUserStatus.setObjectId(objectId);
        objectUserStatus.setStatusNum(status);
        String answer = (StringUtils.isBlank(userSessionDTO.getAnswer()) ? null : userSessionDTO.getAnswer());
        if (status == 4 || status == 2) {
            return null;
        }
        sqlNum = objectUserStatusMapper.getShowNum(objectUserStatus);
        if (status != 3) {//不是挑战任务操作
            if (sqlNum == null) {
                sqlNum = objectUserStatusMapper.insertStatus(objectUserStatus);
                showNum = 1;
            } else {
                showNum = (sqlNum + 1) % 2;
                objectUserStatus.setShowNum(showNum);
                sqlNum = objectUserStatusMapper.updateStatus(objectUserStatus);
            }
            if ((status == 1 || status == -1) && showNum == 1) {//如果用户进行的是赞或者踩操作
                status = 0 - status;
                objectUserStatus.setStatusNum(status);
                Integer showNum2 = objectUserStatusMapper.getShowNum(objectUserStatus);
                if (showNum2 != null) {//判断互斥记录是否已存在
                    if (showNum2 == 1) {//判断互斥记录是否已激活
                        objectUserStatus.setShowNum(0);
                        objectUserStatusMapper.updateStatus(objectUserStatus);//若互斥状态另一者为激活则变更其为不激活
                        showNum = -1;
                    }
                }
            }
            if (sqlNum == 1) {
                return showNum;
            } else {
                return null;
            }
        }
        ObjectLink objectLink = objectLinkMapper.getObjectLinkById(objectId);
        if (objectLink == null) {
            return null;
        } else if (objectLink.getObjectType() == null) {
            return null;
        }
        if (objectLink.getObjectType() != 2) {
            return null;
        }
        ObjectDetail objectDetail = objectDetailMapper.getObjectDetailById(objectLink.getMaxDetailId());
        Integer resource = objectDetail.getResourceEnter();//获取当前报名费
        Integer userResource = userMapper.getUserResource(userId);//获取用户当前资源数
        if (userResource < resource) {//用户资源不足
            return MyConstans.FAIL_STATUS_NUM;
        }
        userMapper.updateUserResource(userId , userResource - resource);//
        objectDetailMapper.updateObjectResourcePool(resource , objectDetail.getObjectDetailId());
        String keyWordValue = objectDetail.getObjectKeyWord();
        if (StringUtils.isEmpty(keyWordValue) || StringUtils.isEmpty(answer)) {//如果答案为null
            if (StringUtils.isEmpty(answer)) {
                changeOwner = Boolean.TRUE;
            }
        } else {
            String[] keyWords = keyWordValue.split(",");
            int i = 0;
            for (String keyWord : keyWords) {
                if (StringUtils.isNotEmpty(answer)) {
                    if (answer.contains(keyWord)) {
                        i++;
                    }
                } else {
                    continue;
                }
            }
            if (i * 10 / keyWords.length>= ownerNum) {
                changeOwner = Boolean.TRUE;
            }
        }
        if (changeOwner) {
            sqlNum2 = objectUserStatusMapper.getShowNum(objectUserStatus);
            objectUserStatus.setStatusNum(4);
            sqlNum = objectUserStatusMapper.getShowNum(objectUserStatus);
            objectUserStatusMapper.updateOwnerStatusToNo(objectId);//取消现有对象持有者的所有权
            //收取任务奖池资源给新持有者
            userResource = userMapper.getUserResource(userId);
            resource = objectDetailMapper.getObjectResourcePool(objectDetail.getObjectDetailId());
            userMapper.updateUserResource(userId  , userResource + resource);
            objectDetailMapper.updateObjectResourcePool(-1 , objectDetail.getObjectDetailId());
            if (sqlNum == null) {//如果不是之前版本的对象持有者
                sqlNum = objectUserStatusMapper.insertStatus(objectUserStatus);
            } else {
                showNum = 1;
                objectUserStatus.setShowNum(showNum);
                sqlNum = objectUserStatusMapper.updateStatus(objectUserStatus);
            }
            if (sqlNum2 != null) {
                ObjectUserStatus objectUserStatus2 = new ObjectUserStatus();
                objectUserStatus2.setUserId(userId);
                objectUserStatus2.setObjectId(objectId);
                objectUserStatus2.setStatusNum(3);
                objectUserStatus2.setShowNum(0);
                objectUserStatusMapper.updateStatus(objectUserStatus2);
            }
            objectDetail.setUserId(userId);
            objectDetail.setObjectVersion(objectDetail.getObjectVersion() + 1);
            if (sqlNum == 1) {
                ObjectMessage objectMessage = objectMessageMapper.getObjectMessageBeanById(objectDetail.getObjectMessageId());
                objectDetailMapper.insertDetail(objectDetail);
                Long objectDetailId = objectDetail.getObjectDetailId();
                objectMessage.setObjectDetailId(objectDetailId);
                objectMessageMapper.insertMessage(objectMessage);
                objectDetailMapper.updateMessageId(objectDetailId , objectMessage.getObjectMessageId());
                objectLinkMapper.updateDetailId(objectId , objectDetailId);
                //对父对象进行操作
                objectId = objectLinkMapper.getParentObjectId(objectId);
                objectDetailId = objectLinkMapper.getMaxDetailId(objectId);
                objectDetail = objectDetailMapper.getObjectDetailById(objectDetailId);
                objectMessage = objectMessageMapper.getObjectMessageBeanById(objectDetail.getObjectMessageId());
                objectDetail.setUserId(userId);
                objectDetail.setObjectVersion(objectDetail.getObjectVersion() + 1);
                objectDetailMapper.insertDetail(objectDetail);
                objectDetailId = objectDetail.getObjectDetailId();
                objectMessage.setObjectDetailId(objectDetailId);
                objectMessageMapper.insertMessage(objectMessage);
                objectDetailMapper.updateMessageId(objectDetailId , objectMessage.getObjectMessageId());
                objectLinkMapper.updateDetailId(objectId , objectDetailId);
                return 2;//成为所有者
            }
        }
        objectUserStatus.setStatusNum(3);
        sqlNum = objectUserStatusMapper.getShowNum(objectUserStatus);
        if (sqlNum == null) {
            sqlNum = objectUserStatusMapper.insertStatus(objectUserStatus);
        } else {
            showNum = (showNum + 1) % 2;
            objectUserStatus.setShowNum(showNum);
            sqlNum = objectUserStatusMapper.updateStatus(objectUserStatus);
        }
        if (sqlNum == 1) {
            return 1;//成为参阅者
        }
        return 1;
    }

    /**
     * 根据对象ID获取其任务ID
     * @param objectId 对象ID
     * @return 任务ID
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Long getTaskId(Long objectId) throws Exception {
        Long taskId = null;
        try {
            if (objectLinkMapper.getObjectTypeByObjectId(objectId) == 2) {
                return  null;
            }
            taskId = objectLinkMapper.getTaskIdByObjectId(objectId);
        } catch (Exception e) {
            log.error("===========获取任务ID失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        return taskId;
    }

    /**
     * 根据对象ID获取其父对象ID
     * @param objectId 对象ID
     * @return 父对象ID
     * @throws Exception 可能发生的redis、sql等异常
     */
    @Override
    public Long getParentObjectId(Long objectId) throws Exception {
        Long parentObjectId = null;
        try {
            if (objectLinkMapper.getObjectTypeByObjectId(objectId) == 0) {
                return  null;
            }
            parentObjectId = objectLinkMapper.getParentObjectId(objectId);
        } catch (Exception e) {
            log.error("===========获取任务ID失败============="+e.toString());
            throw new MySQLException(MyConstans.SQL_DATE_FAIL);
        }
        return parentObjectId;
    }

    /**
     * 从objectDetail中提取objectDetailVO所需要的数据
     * @param objectDetail 包含从数据库中查询出的对象详情信息
     * @param objectDetailVO 返回用的VO对象
     * @return objectDetailVO
     */
    private ObjectDetailVO fromDetailToVO(ObjectDetail objectDetail , ObjectDetailVO objectDetailVO) {
        objectDetailVO.setObjectStatus(objectDetail.getObjectStatus());
        objectDetailVO.setObjectId(objectDetail.getObjectId());
        objectDetailVO.setUserId(objectDetail.getUserId());
        objectDetailVO.setObjectVersion(objectDetail.getObjectVersion());
        objectDetailVO.setObjectTitle(objectDetail.getObjectTitle());
        objectDetailVO.setObjectIntroduction(objectDetail.getObjectIntroduction());
        objectDetailVO.setObjectLabelList(objectDetail.getObjectLabelList());
        objectDetailVO.setResourcePool(objectDetail.getResourcePool());
        objectDetailVO.setResourceEnter(objectDetail.getResourceEnter());
        objectDetailVO.setResourceSkip(objectDetail.getResourceSkip());
        objectDetailVO.setUpdateTime(objectDetail.getUpdateTime().toString());
        objectDetailVO.setCreateTime(objectDetail.getCreateTime().toString());
        return objectDetailVO;
    }

    /**
     * 从详情DTO中提取数据库详情bean对象需要的数据
     * @param objectDetail 数据库对应的对象详情bean
     * @param objectDetailDTO 对象详情DTO
     * @param objectType 对象类型
     * @return 已加入数据后的bean
     */
    private ObjectDetail fromDetailDTOtoDetail(ObjectDetail objectDetail , ObjectDetailDTO objectDetailDTO , Integer objectType){
        //设置简介
        objectDetail.setObjectIntroduction(StringUtils.isBlank(objectDetailDTO.getObjectIntroduction()) ? " " : objectDetailDTO.getObjectIntroduction());
        if (objectType != 3) {//该对象不是评论
            //设置标题
            objectDetail.setObjectTitle(StringUtils.isBlank(objectDetailDTO.getObjectTitle()) ? " " : objectDetailDTO.getObjectTitle());
            //设置标签集
            objectDetail.setObjectLabelList(StringUtils.isBlank(objectDetailDTO.getObjectLabelList()) ? " " : objectDetailDTO.getObjectLabelList());
            if (objectType == 2) {//对象为任务
                //设置任务答案关键词
                objectDetail.setObjectKeyWord(StringUtils.isBlank(objectDetailDTO.getKeyWord()) ? " " : objectDetailDTO.getKeyWord());
                //设置报名费
                objectDetail.setResourceEnter((objectDetailDTO.getResourceEnter() == null) ? 0 : objectDetailDTO.getResourceEnter());
                //设置跳过支付资源数
                objectDetail.setResourceSkip((objectDetailDTO.getResourceSkip() == null) ? 0 : objectDetailDTO.getResourceSkip());
            }
        }
        return objectDetail;
    }

    /**
     * 将字符串根据","分解为字符串list并返回
     * @param str 要分解的字符串
     * @return List<String>
     */
    private List<String> shellStringToList(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            str = str.replace('，' , ',');
            String[] strArray = str.split(",");//如果该字符串最后一个","之后为空,则该空串会被丢弃
            List<String> strList = new ArrayList<>();
            for (String strTmp : strArray) {
                if (StringUtils.isNotBlank(strTmp.trim())) {
                    strTmp = strTmp.trim();
                }
                strList.add(strTmp);
            }
            return strList;
        }
    }
}
