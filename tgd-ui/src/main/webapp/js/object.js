/**
 * Created by 147356 on 2017/4/27.
 */
//初始化对象列表
previousPageClick();
nextPageClick();
getObjectList(null);
objectListBaseFunction();
searchFunction();
createObjectClickFunction();

//创建对象按钮点击事件
function createObjectClickFunction() {
    $("#createObjectClick").click(function () {
        createObjectFunction(0);
        reObjectListOrObjectDetailClick();
        createObjectClick();
    });
}

//显示创建或编辑对象表单
function createObjectFunction(objectType) {
    var userId = null;
    var sessionId = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
            hideListProject();
            if (objectType == 0) {
                $("#objectOrUserList").html("<form role=\"form\"><label id=\"objectType\" style=\"display:none;\">"+objectType+"</label><div class=\"form-group\"><label for=\"objectTitle\">对象标题</label><input type=\"text\" class=\"form-control\" id=\"objectTitle\" /></div><div class=\"form-group\"><label for=\"objectIntroduction\">对象简介</label><textarea id=\"objectIntroduction\" class=\"form-control\" rows=\"2\"></textarea></div> <div class=\"form-group\"><label for=\"objectLabelList\">对象的标签</label><textarea id=\"objectLabelList\" class=\"form-control\" rows=\"2\">用\"，\"分隔</textarea></div><div class=\"form-group\"><label for=\"objectMessage\">对象内容</label><textarea id=\"objectMessage\" class=\"form-control\" rows=\"10\">请在此输入对象的详细内容</textarea></div></form><button id=\"reObjectListOrObjectDetail\" class=\"btn btn-primary pull-right\">取消</button><button id=\"createObject\" class=\"btn btn-primary pull-right\">保存</button>");
            } else {
                $("#objectOrUserList").html("<form role=\"form\"><label id=\"objectType\" style=\"display:none;\">"+objectType+"</label><div class=\"form-group\"><label for=\"objectTitle\">任务标题</label><input type=\"text\" class=\"form-control\" id=\"objectTitle\" /></div><div class=\"form-group\"><label for=\"objectIntroduction\">任务简介</label><textarea id=\"objectIntroduction\" class=\"form-control\" rows=\"2\"></textarea></div> <div class=\"form-group\"><label for=\"objectLabelList\">任务的标签</label><textarea id=\"objectLabelList\" class=\"form-control\" rows=\"2\">用\"，\"分隔</textarea></div><div class=\"form-group\"><label for=\"objectMessage\">任务内容</label><textarea id=\"objectMessage\" class=\"form-control\" rows=\"10\">请在此输入任务的详细内容</textarea></div><div class=\"form-group\"><label for=\"objectKeyWord\">答案关键词</label><textarea id=\"objectKeyWord\" class=\"form-control\" rows=\"2\">用\"，\"分隔</textarea></div><div class=\"input-group col-md-6\"><span class=\"input-group-btn\"><button class=\"btn btn-primary\" type=\"button\">挑战任务所需资源量：</button></span><input id=\"resourceEnter\" type=\"text\" class=\"form-control\"></div></form><button id=\"reObjectListOrObjectDetail\" class=\"btn btn-primary pull-right\">取消</button><button id=\"createObject\" class=\"btn btn-primary pull-right\">保存</button>");
            }
        } else {
            alert("请登录后再进行操作");
        }
    } else {
        alert("请登录后再进行操作");
    }
}

//点击取消操作
function reObjectListOrObjectDetailClick() {
    $("#reObjectListOrObjectDetail").click(function () {
        if (objectMsg != undefined || objectMsg != null) {
            objectMsgToView(objectMsg);
            getCommentsListAJAX(objectMsg.objectId , objectMsg.objectVersion);
        } else {
            getObjectList(null);
        }
    });
}

//点击保存创建对象
function createObjectClick() {
    $("#createObject").click(function () {
        var objectType = $("#objectType").text();
        var objectTitle = $("#objectTitle").val();
        var objectIntroduction = $("#objectIntroduction").val();
        var objectLabelList = $("#objectLabelList").val();
        var objectMessage = $("#objectMessage").val();
        var userId = null;
        var sessionId = null;
        if (userMsg != undefined || userMsg != null) {
            if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
                userId = userMsg.userId;
                sessionId = userMsg.sessionId;
            }
        }
        var objectTmp = null;
        if (objectType == 2) {
            var objectKeyWord = $("#objectKeyWord").val();
            var resourceEnter = $("#resourceEnter").val();
            var parentObjectIdTmp = objectMsg.objectId;
            var parentObjectVersion = objectMsg.objectVersion;
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectType\":\""+objectType
                +"\",\"objectTitle\":\""+objectTitle+"\",\"objectIntroduction\":\""+objectIntroduction
                +"\",\"objectLabelList\":\""+objectLabelList+"\",\"objectMessage\":\""+objectMessage
                +"\",\"objectKeyWord\":\""+objectKeyWord+"\",\"resourceEnter\":\""+resourceEnter
                +"\",\"parentObjectId\":\""+parentObjectIdTmp+"\",\"parentObjectVersion\":\""+parentObjectVersion+"\"}";
            objectTmp = createObjectAJAX(dataValue);
        } else if (objectType == 0) {
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectType\":\""+objectType
                +"\",\"objectTitle\":\""+objectTitle+"\",\"objectIntroduction\":\""+objectIntroduction
                +"\",\"objectLabelList\":\""+objectLabelList+"\",\"objectMessage\":\""+objectMessage+"\"}";
            objectTmp = createObjectAJAX(dataValue);
        }
        getMessageAJAX(objectTmp);
    });
}

//创建对象AJAX
function createObjectAJAX(dataValueTmp) {
    var msgTmp = null;
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValueTmp,
        url:"objectBase/createObject",
        async: false,
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                alert(date.msg);
            }
            else {
                msgTmp = date.msg;
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    });
    return msgTmp;
}

//点击保存更新对象
function updateObjectClick() {
    $("#createObject").click(function () {
        var objectIdTmp= objectMsg.objectId;
        var objectType = $("#objectType").text();
        var objectTitle = $("#objectTitle").val();
        var objectIntroduction = $("#objectIntroduction").val();
        var objectLabelList = $("#objectLabelList").val();
        var objectMessage = $("#objectMessage").val();
        var userId = null;
        var sessionId = null;
        if (userMsg != undefined || userMsg != null) {
            if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
                userId = userMsg.userId;
                sessionId = userMsg.sessionId;
            }
        }
        var updateStatus = null;
        if (objectType == 2) {
            var objectKeyWord = $("#objectKeyWord").val();
            var resourceEnter = $("#resourceEnter").val();
            var parentObjectIdTmp = objectMsg.objectId;
            var parentObjectVersion = objectMsg.objectVersion;
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectType\":\""+objectType
                +"\",\"objectTitle\":\""+objectTitle+"\",\"objectIntroduction\":\""+objectIntroduction
                +"\",\"objectLabelList\":\""+objectLabelList+"\",\"objectMessage\":\""+objectMessage
                +"\",\"objectKeyWord\":\""+objectKeyWord+"\",\"resourceEnter\":\""+resourceEnter
                +"\",\"parentObjectId\":\""+parentObjectIdTmp+"\",\"parentObjectVersion\":\""
                +parentObjectVersion+"\",\"objectId\":\""+objectIdTmp+"\"}";
            updateStatus = updateObjectAJAX(dataValue);
        } else if (objectType == 0) {
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectType\":\""+objectType
                +"\",\"objectTitle\":\""+objectTitle+"\",\"objectIntroduction\":\""+objectIntroduction
                +"\",\"objectLabelList\":\""+objectLabelList+"\",\"objectMessage\":\""+objectMessage+"\",\"objectId\":\""+objectIdTmp+"\"}";
            updateStatus = updateObjectAJAX(dataValue);
        }
        if (updateStatus == 1) {
            getMessageAJAX(objectMsg.objectId);
        } else {
            alert("更新失败");
        }
    });
}

//更新对象AJAX
function updateObjectAJAX(dataValueTmp) {
    var msgTmp = null;
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValueTmp,
        url:"objectBase/updateObject",
        async: false,
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                alert(date.msg);
            }
            else {
                msgTmp = date.msg;
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    });
    return msgTmp;
}

//点击上一页
function previousPageClick() {
    $("#previousPage").click(function () {
        pageNum = $("#pageNum").text();
        if (pageNum == 1) {
            alert("已经是第一页了");
        } else {
            $("#pageNum").text(pageNum-1);
            getObjectList(null);
        }
    });
}

//点击下一页
function nextPageClick() {
    $("#nextPage").click(function () {
        var objectNum =$("#objectOrUserList").children(".column").length;
        if (objectNum < 20) {
            alert("已经是最后一页了");
        } else {
            $("#pageNum").text(pageNum+1);
            getObjectList(null);
        }
    });
}

//点击按钮获取对象列表
function objectListBaseFunction() {
    $("#objectListClick").click(function () {
        getObjectList(null);
    });
    $("#reObjectOrUserList").click(function () {
        if (userOrObject == 0) {
            getObjectList(null);
        } else {
            getUserList(null);
        }
    });
}

//点击搜索获取对象列表
function searchFunction() {
    $("#searchButton").click(function () {
        keyWord = $("#searchInputValue").val();
        getObjectList(keyWord);
    });
}

//获取对象列表
function getObjectList(keyWord) {
    userOrObject = 0;
    var userId = null;
    var sessionId = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
            userId = userMsg.userId;
            sessionId = userMsg.sessionId;
        }
    }
    sortType = $("#sortTypeSelect").val();
    listType = $("#listTypeSelect").val();
    pageNum = $("#pageNum").text();
    if (userId!=null && sessionId!=null) {
        if (keyWord != null) {
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId
                +"\",\"objectType\":\"0\",\"sortType\":\""+sortType+"\",\"listType\":\""+listType
                +"\",\"pageNum\":\""+pageNum +"\",\"seachWord\":\""+keyWord+"\"}";
        } else {
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId
                +"\",\"objectType\":\"0\",\"sortType\":\""+sortType+"\",\"listType\":\""+listType
                +"\",\"pageNum\":\""+pageNum +"\"}";
        }

    } else {
        if (keyWord !=null) {
            dataValue = "{\"objectType\":\"0\",\"sortType\":\""+sortType+"\",\"listType\":\""+listType
                +"\",\"pageNum\":\""+pageNum +"\",\"seachWord\":\""+keyWord+"\"}";
        } else {
            dataValue = "{\"objectType\":\"0\",\"sortType\":\""+sortType+"\",\"listType\":\""+listType
                +"\",\"pageNum\":\""+pageNum +"\"}";
        }
    }
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValue,
        url:"objectList/getObjectList",
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                $("#objectOrUserList").html(date.msg);
            }
            else {
                msg = jQuery.parseJSON(date.msg);
                $("#objectOrUserList").html("");
                $.each(msg,function (index,objectBase) {
                    $("#objectOrUserList").append("<br/><div class=\"col-md-12 column\"><h3 class=\"objectTitle\">"+objectBase.objectTitle+"</h3><blockquote><p class=\"objectIntroduction\">"+objectBase.objectIntroduction+"</p><small>当前持有者<label class=\"checkedUserId\" style=\"display:none;\">"+objectBase.ownerId+"</label><cite class=\"checkedUserName btn btn-link\">"+objectBase.ownerName+"</cite></small></blockquote><div class=\"row clearfix\"><div class=\"col-md-4 column\"><label>被举报次数</label></div><div class=\"col-md-4 column\"><label>奖池累计值</label></div><div class=\"col-md-4 column\"></div></div><div class=\"row clearfix\"><div class=\"col-md-4 column\"><label class=\"objectStatus\">"+objectBase.status+"</label></div><div class=\"col-md-4 column\"><label class=\"resourcePoolNum\">"+objectBase.resourcePool+"</label></div><div class=\"col-md-4 column\"><label class=\"objectId\" style=\"display:none;\">"+objectBase.objectId+"</label><label class=\"objectType\" style=\"display:none;\">"+objectBase.objectType+"</label><button type=\"button\" class=\"btn btn-primary pull-right getObjectMessage\">进去看看</button></div></div></div>");
                });
                checkedUserMsgFuntion();
                showListProject();
                getObjectMessageFunction();
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    })
}

//点击进去看看按钮
function getObjectMessageFunction() {
    $(".getObjectMessage").click(function () {
        hideListProject();
        objectId = $(this).parent().children(".objectId").text();
        objectType = $(this).parent().children(".objectType").text();
        getMessageAJAX(objectId);
    });
}

//获取对象详情AJAX
function getMessageAJAX(objectIdTmp) {
    var userId = null;
    var sessionId = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
            userId = userMsg.userId;
            sessionId = userMsg.sessionId;
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectId\":\""+objectIdTmp+"\"}";
        }
    } else {
        dataValue = "{\"objectId\":\""+objectIdTmp+"\"}";
    }
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValue,
        url:"objectBase/getObjectDetail",
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                alert(date.msg);
            }
            else {
                msg = jQuery.parseJSON(date.msg);
                objectMsgToView(msg);
                objectMsg = msg;
                getCommentsListAJAX(msg.objectId , msg.objectVersion);
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    })
}

//请求评论列表AJAX
function getCommentsListAJAX(objectIdTmp , parentObjectVersion) {
    var userId = null;
    var sessionId = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
            userId = userMsg.userId;
            sessionId = userMsg.sessionId;
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectType\":\"3\",\"objectId\":\""+objectIdTmp+"\",\"objectVersion\":\""+parentObjectVersion+"\"}";
        }
    } else {
        dataValue = "{\"objectType\":\"3\",\"objectId\":\""+objectIdTmp+"\",\"objectVersion\":\""+parentObjectVersion+"\"}";
    }
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValue,
        url:"objectList/getObjectList",
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                $("#commentsList").html(date.msg);
            }
            else {
                msg = jQuery.parseJSON(date.msg);
                commentsListMsgToView(msg);
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    })
}

//将获取到的评论列表转换为HTML代码
function commentsListMsgToView(msgTmp) {
    $("#commentsList").html("");
    $.each(msgTmp , function (index , commentsBase) {
        var objectIntroduction = commentsBase.objectIntroduction;
        var ownerName = commentsBase.ownerName;
        var commentsId = commentsBase.objectId;
        var upTimes = commentsBase.upTimes;
        var downTimes = commentsBase.downTimes;
        $("#commentsList").append("<div style=\"height: 10px\" class=\"col-md-12\"></div><blockquote><p class=\"commentsMessage\">"+objectIntroduction+"</p><small>来自：<cite class=\"commentsUserName\">"+ownerName+"</cite></small><div class=\"btn-group\"><label class=\"objectId\" style=\"display:none;\">"+commentsId+"</label><button type=\"button\" class=\"btn btn-sm btn-link upTimes\"><em class=\"glyphicon glyphicon-thumbs-up\"></em>赞<span class=\"badge\">"+upTimes+"</span></button><button type=\"button\" class=\"btn btn-sm btn-link downTimes\"><em class=\"glyphicon glyphicon-thumbs-down\"></em>踩<span class=\"badge\">"+downTimes+"</span></button></div></blockquote>");
    });
    upTimesClick2();
    downTimesClick2();
    $("#userMessageView").removeClass("active");
    $("#commentsList").addClass("active");
    $("#userMessageActive").removeClass("active");
    $("#commentsListActive").addClass("active");
}

//将获取到的对象详情JSON转化为html代码
function objectMsgToView(objectMsgTmp) {
    var objectIdTmp = objectMsgTmp.objectId;
    var objectTitle = objectMsgTmp.objectTitle;
    var objectIntroduction = objectMsgTmp.objectIntroduction;
    var objectLabelList = objectMsgTmp.objectLabelList;
    var objectMessage = objectMsgTmp.objectMessage;
    var objectVersion = objectMsgTmp.objectVersion;
    var resourcePool = objectMsgTmp.resourcePool;
    var updateTime = objectMsgTmp.updateTime;
    var upTimes = objectMsgTmp.upTimes;
    var downTimes = objectMsgTmp.downTimes;
    var userId = objectMsgTmp.userId;
    var resourceEnter = objectMsgTmp.resourceEnter;
    var objectType = objectMsgTmp.objectType;
    var objectStatus = objectMsgTmp.objectStatus;
    var objectUserStatus = objectMsgTmp.objectUserStatus;
    var statusValue = changeUserOrObjectStatusBtnText(objectStatus);
    var attentionImgColor = "#5e5e5e";
    if ($.inArray(0,objectUserStatus) == -1) {
        attentionImgColor = glyphiconColor(0);
    } else {
        attentionImgColor = glyphiconColor(1);
    }
    $("#objectOrUserList").html("<div class=\"col-md-12 column\"><div id=\"objectHead\" class=\"col-md-12 column\"><label id=\"objectVersion\">Version:"+objectVersion+"</label>&nbsp;&nbsp;&nbsp;<label id=\"updateTime\">最近更新时间："+updateTime+"</label><label id=\"resourcePool\" class=\"pull-right\">奖池:"+resourcePool+"</label></div><br/><br/><h1 id=\"objectTitle\">"+objectTitle+"</h1><br/><blockquote><p id=\"objectIntroduction\"><small>"+objectIntroduction+"</small></p></blockquote><blockquote class=\"pull-right\"><p id=\"objectLabelList\"><small>"+objectLabelList+"</small></p></blockquote><br/><p id=\"objectMessage\" class=\"col-md-12\">"+objectMessage+"</p><div style=\"height: 55px\"></div><textarea id=\"messageInput\" class=\"form-control\" rows=\"3\">请在此输入回答或评论内容</textarea><br/><label class=\"objectId\" style=\"display:none;\">"+objectIdTmp+"</label><button id=\"upTimes\" type=\"button\" class=\"btn btn-sm btn-link\">赞<span class=\"badge\">"+upTimes+"</span></button><button id=\"downTimes\" type=\"button\" class=\"btn btn-sm btn-link\">踩<span class=\"badge\">"+downTimes+"</span></button><button id=\"attention\" type=\"button\" style=\"color: "+attentionImgColor+"\" class=\"btn btn-sm btn-link glyphicon glyphicon-heart\"></button><div id=\"userBtnGroup\" class=\"btn-group btn-group-sm pull-right\"></div></div>");
    upTimesClick();//点赞
    downTimesClick();//点踩
    attentionClick();//收藏
    if (userMsg !=undefined || userMsg != null) {
        if (userMsg.userId!=undefined && userMsg.userId == userId) {
            $("#userBtnGroup").append("<button id=\"changeObjectMessage\" type=\"button\" class=\"btn btn-primary\">编辑</button>");
            changeObjectMessageClick();
            if (objectType == 0) {
                $("#userBtnGroup").append("<button id=\"createTask\" type=\"button\" class=\"btn btn-primary\">创建任务</button>");
                $("#createTask").click(function () {
                    $.ajax({
                        type:'post',
                        contentType :'application/json',
                        data:"{\"objectId\":\""+objectMsg.objectId+"\"}",
                        url:"objectBase/getTaskId",
                        dataType : 'json',
                        success:function (date) {
                            if(date.result !=200){
                                createObjectFunction(2);
                                reObjectListOrObjectDetailClick();
                                createObjectClick();
                            } else {
                                alert("该对象已拥有任务");
                            }
                        },
                        error : function() {
                            alert("出错了o(╯□╰)o");
                        }
                    })
                });
            }
        } else {
            if (userMsg.userLevel == 1) {
                $("#userBtnGroup").append("<button id=\"changeObjectStatus\" type=\"button\" class=\"btn btn-primary\">"+statusValue+"</button>");
                sealObjectFunctin();
            } else {
                $("#userBtnGroup").append("<button id=\"changeObjectStatus1\" type=\"button\" class=\"btn btn-primary\">举报</button>");
                reportObjectFunctin();
            }
        }
    } else {
        $("#userBtnGroup").append("<button id=\"changeObjectStatus1\" type=\"button\" class=\"btn btn-primary\">举报</button>");
        reportObjectFunctin();
    }
    if (objectType == 0) {
        $("#userBtnGroup").append("<button id=\"seeTask\" type=\"button\" class=\"btn btn-primary\">查看任务</button>");
        seeTaskClick();
    } else {
        if ($.inArray(3,objectUserStatus)!=-1 || $.inArray(4,objectUserStatus)!=-1) {
            $("#userBtnGroup").append("<button id=\"seeObject\" type=\"button\" class=\"btn btn-primary\">查看内容</button>");
            seeObject();
        } else {
            $("#userBtnGroup").append("<button id=\"changeStatusNum\" type=\"button\" class=\"btn btn-primary\">挑战任务(-"+resourceEnter+")</button>");
            changeStatusNumClick();
        }
    }
    $("#userBtnGroup").append("<button id=\"comments\" type=\"button\" class=\"btn btn-primary\">评论</button>");
    commentsClickFunction()
    $("#userBtnGroup").append("<button id=\"returnToObjectList\" type=\"button\" class=\"btn btn-primary\">返回</button>");
    $("#returnToObjectList").click(function () {
        getObjectList(null);
    });
}

//点击挑战任务
function changeStatusNumClick() {
    $("#changeStatusNum").click(function () {
        var answer = $("#messageInput").val();
        if (answer == undefined) {
            answer = "";
        }
        var objectIdTmp = objectMsg.objectId;
        var statusNumTmp = 3;
        var msgTmp = null;
        var userId = null;
        var sessionId = null;
        if (userMsg != undefined || userMsg != null) {
            if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
                userId = userMsg.userId;
                sessionId = userMsg.sessionId;
                dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectId\":\""+objectIdTmp
                    +"\",\"status\":\""+statusNumTmp+"\",\"answer\":\""+answer+"\"}";
            }
        } else {
            dataValue = "{\"objectId\":\""+objectIdTmp+"\",\"status\":\""+statusNumTmp+"\",\"answer\":\""+answer+"\"}";
        }
        $.ajax({
            type:'post',
            contentType :'application/json',
            data:dataValue,
            url:"objectBase/changeStatusNum",
            async: false,
            dataType : 'json',
            success:function (date) {
                if(date.result!=200){
                    alert(date.msg);
                }
                else {
                    msgTmp = Number(date.msg);
                }
            },
            error : function() {
                alert("出错了o(╯□╰)o");
            }
        });
        statusNumTmp = Number(msgTmp);
        if (statusNumTmp != 3) {
            seeObjectAJAX(objectIdTmp);
        } else {
            alert("挑战任务失败。");
        }
    });
}

//点击编辑按钮
function changeObjectMessageClick() {
    $("#changeObjectMessage").click(function () {
        var objectType = objectMsg.objectType;
        createObjectFunction(objectType);
        reObjectListOrObjectDetailClick();
        updateObjectClick();
        $("#objectTitle").val(objectMsg.objectTitle);
        $("#objectIntroduction").val(objectMsg.objectIntroduction);
        $("#objectLabelList").val(objectMsg.objectLabelList);
        $("#objectMessage").val(objectMsg.objectMessage);
        if (objectType == 2) {
            $("#objectKeyWord").val(objectMsg.objectKeyWord);
            $("#resourceEnter").val(objectMsg.resourceEnter);
        }
    });
}

//点击评论按钮
function commentsClickFunction() {
    $("#comments").click(function () {
        var parentObjectIdTmp = objectMsg.objectId;
        var parentObjectVersion = objectMsg.objectVersion;
        var objectIntroduction = $("#messageInput").val();
        var userId = null;
        var sessionId = null;
        if (userMsg != undefined || userMsg != null) {
            if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
                userId = userMsg.userId;
                sessionId = userMsg.sessionId;
            }
        }
        var dataValueTmp = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectType\":\"3\",\"parentObjectId\":\""+parentObjectIdTmp
            +"\",\"parentObjectVersion\":\""+parentObjectVersion+"\",\"objectIntroduction\":\""+objectIntroduction+"\"}";
        var commentIdTmp = createObjectAJAX(dataValueTmp);
        if (commentIdTmp != null) {
            getCommentsListAJAX(objectMsg.objectId , objectMsg.objectVersion);
        }
    });
}

//点击收藏
function attentionClick() {
    $("#attention").click(function () {
        var objectIdTmp = objectMsg.objectId;
        var statusNumTmp = 0;
        var resultTmp = changeObjectUserStatus(objectIdTmp , statusNumTmp);
        if (resultTmp == 1) {
            var attentionImgColor = glyphiconColor(resultTmp);
            $("#attention").css("color",attentionImgColor);
        } else {
            var attentionImgColor = glyphiconColor(0);
            $("#attention").css("color",attentionImgColor);
        }
    });
}

//点赞操作
function upTimesClick() {
    $("#upTimes").click(function () {
        var objectIdTmp = $(this).parent().children(".objectId").text();
        var statusNumTmp = 1;
        var resultTmp = changeObjectUserStatus(objectIdTmp , statusNumTmp);
        var upTimesValue = $(this).children("span").text();
        if (resultTmp != 0) {
            upTimesValue = Number(upTimesValue) + 1;
            $(this).children("span").text(upTimesValue);
        } else {
            upTimesValue = Number(upTimesValue) - 1;
            $(this).children("span").text(upTimesValue);
        }
        if (resultTmp == -1) {
            upTimesValue = $(this).parent().children("#downTimes").children("span").text();
            upTimesValue = Number(upTimesValue) - 1;
            $(this).parent().children("#downTimes").children("span").text(upTimesValue);
        }
    });
}

//点踩操作
function downTimesClick() {
    $("#downTimes").click(function () {
        var objectIdTmp = $(this).parent().children(".objectId").text();
        var statusNumTmp = -1;
        var resultTmp = changeObjectUserStatus(objectIdTmp , statusNumTmp);
        var upTimesValue = $(this).children("span").text();
        if (resultTmp != 0) {
            upTimesValue = Number(upTimesValue) + 1;
            $(this).children("span").text(upTimesValue);
        } else {
            upTimesValue = Number(upTimesValue) - 1;
            $(this).children("span").text(upTimesValue);
        }
        if (resultTmp == -1) {
            upTimesValue = $(this).parent().children("#upTimes").children("span").text();
            upTimesValue = Number(upTimesValue) - 1;
            $(this).parent().children("#upTimes").children("span").text(upTimesValue);
        }
    });
}

//评论点赞操作
function upTimesClick2() {
    $(".upTimes").click(function () {
        var objectIdTmp = $(this).parent().children(".objectId").text();
        var statusNumTmp = 1;
        var resultTmp = changeObjectUserStatus(objectIdTmp , statusNumTmp);
        var upTimesValue = $(this).children("span").text();
        if (resultTmp != 0) {
            upTimesValue = Number(upTimesValue) + 1;
            $(this).children("span").text(upTimesValue);
        } else {
            upTimesValue = Number(upTimesValue) - 1;
            $(this).children("span").text(upTimesValue);
        }
        if (resultTmp == -1) {
            upTimesValue = $(this).parent().children(".downTimes").children("span").text();
            upTimesValue = Number(upTimesValue) - 1;
            $(this).parent().children(".downTimes").children("span").text(upTimesValue);
        }
    });
}

//评论点踩操作
function downTimesClick2() {
    $(".downTimes").click(function () {
        var objectIdTmp = $(this).parent().children(".objectId").text();
        var statusNumTmp = -1;
        var resultTmp = changeObjectUserStatus(objectIdTmp , statusNumTmp);
        var upTimesValue = $(this).children("span").text();
        if (resultTmp != 0) {
            upTimesValue = Number(upTimesValue) + 1;
            $(this).children("span").text(upTimesValue);
        } else {
            upTimesValue = Number(upTimesValue) - 1;
            $(this).children("span").text(upTimesValue);
        }
        if (resultTmp == -1) {
            upTimesValue = $(this).parent().children(".upTimes").children("span").text();
            upTimesValue = Number(upTimesValue) - 1;
            $(this).parent().children(".upTimes").children("span").text(upTimesValue);
        }
    });
}

//变更用户在对象中的状态AJAX
function changeObjectUserStatus(objectIdTmp , statusNumTmp) {
    var userId = null;
    var sessionId = null;
    var msgTmp = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
            userId = userMsg.userId;
            sessionId = userMsg.sessionId;
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectId\":\""+objectIdTmp+"\",\"status\":\""+statusNumTmp+"\"}";
        }
    } else {
        dataValue = "{\"objectId\":\""+objectIdTmp+"\",\"status\":\""+statusNumTmp+"\"}";
    }
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValue,
        url:"objectBase/changeStatusNum",
        async: false,
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                alert(date.msg);
            }
            else {
                msgTmp = date.msg;
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    });
    return msgTmp;
}

//查看任务
function seeTaskClick() {
    $("#seeTask").click(function () {
        var objectIdTmp = objectMsg.objectId;
        $.ajax({
            type:'post',
            contentType :'application/json',
            data:"{\"objectId\":\""+objectIdTmp+"\"}",
            url:"objectBase/getTaskId",
            dataType : 'json',
            success:function (date) {
                if(date.result!=200){
                    alert(date.msg);
                }
                else {
                    var taskId = date.msg;
                    getMessageAJAX(taskId);
                }
            },
            error : function() {
                alert("出错了o(╯□╰)o");
            }
        })
    });
}

//查看内容
function seeObject() {
    $("#seeObject").click(function () {
        var objectIdTmp = objectMsg.objectId;
        seeObjectAJAX(objectIdTmp);
    });
}

function seeObjectAJAX(objectIdTmp) {
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:"{\"objectId\":\""+objectIdTmp+"\"}",
        url:"objectBase/getParentObjectId",
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                alert(date.msg);
            }
            else {
                var parentObjectIdTmp = date.msg;
                getMessageAJAX(parentObjectIdTmp);
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    });
}

//点击封禁或解封
function sealObjectFunctin() {
    $("#changeObjectStatus").click(function () {
        var objectIdTmp = objectMsg.objectId;
        var objectStatusTmp = objectMsg.objectStatus;
        if (objectStatusTmp>=0) {
            objectStatusTmp = -1;
        } else {
            objectStatusTmp = 0;
        }
        objectStatusTmp= changeObjectStatuaAJAX(objectIdTmp , objectStatusTmp);
        var statusValue = changeUserOrObjectStatusBtnText(objectStatusTmp);
        $("#changeObjectStatus").text(statusValue);
        objectMsg.objectStatus = objectStatusTmp;
    });
}

//点击举报
function reportObjectFunctin() {
    $("#changeObjectStatus1").click(function () {
        var objectIdTmp = objectMsg.objectId;
        var objectStatusTmp = 1;
        objectStatusTmp= changeObjectStatuaAJAX(objectIdTmp , objectStatusTmp);
        var objectStatusTmp2 = Number(objectMsg.objectStatus);
        objectMsg.objectStatus = objectStatusTmp;
        if (objectStatusTmp > objectStatusTmp2 ) {
            alert("举报成功。");
        } else {
            alert("已取消举报。");
        }
    });
}

//变更对象那个当前状态AJAX
function changeObjectStatuaAJAX(objectIdTmp , objectStatusTmp) {
    var userId = null;
    var sessionId = null;
    var msgTmp = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
            userId = userMsg.userId;
            sessionId = userMsg.sessionId;
            dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"objectId\":\""+objectIdTmp+"\",\"status\":\""+objectStatusTmp+"\"}";
        }
    } else {
        dataValue = "{\"objectId\":\""+objectIdTmp+"\",\"status\":\""+objectStatusTmp+"\"}";
    }
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValue,
        url:"objectBase/changeObjectStatus",
        async: false,
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                alert(date.msg);
            }
            else {
                msgTmp = date.msg;
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    });
    return msgTmp;
}