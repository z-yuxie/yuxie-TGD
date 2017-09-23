/**
 * 右侧用户区
 * Created by 147356 on 2017/4/27.
 */
//全局储值变量
var userMsg = null;
var dataValue = null;
var objectId = null;
var objectType = null;
var checkedUserId = null;
var userOrObject = 0;
var objectMsg = null;
var sortType = null;
var listType = null;
var pageNum = null;
var keyWord = null;
var msg = null;
var checkUserNameBealoon = false;
var checkUserPwBealoon = false;
var checkEmailBealoon = false;
var checkPhoneBealoon = false;
var userLevelViewTmp = null;
var userStatusViewTmp = null;
var userRelationColor = null;
//页面初始化操作
$("#registLink").click(registFromHtmlLoad);
$("#loginBtn").click(loginBtnClick);
listTypeFunction(0);
userListClickFunction();

function myJsSHAFunction(password) {
    var shaObj = new jsSHA("SHA-512", "TEXT");
    shaObj.update(password);
    password = shaObj.getHash("HEX");
    return password;
}


//显示列表相关选项
function showListProject() {
    $("#sortTypeSelect").show();
    $("#listTypeSelect").show();
    $("#reObjectOrUserList").show();
    $("#myPageView").show();
}

//隐藏列表相关项
function hideListProject() {
    $("#sortTypeSelect").hide();
    $("#listTypeSelect").hide();
    $("#reObjectOrUserList").hide();
    $("#myPageView").hide();
}

//设置列表筛选类型
function listTypeFunction(userLevel) {
    if (userLevel == 1) {
        $("#listTypeSelect").html("<option value=\"0\" selected=\"selected\">所有</option><option value=\"3\">我的收藏或关注</option><option value=\"-2\">异常</option><option value=\"-1\">被封禁</option><option value=\"1\">被举报</option><option value=\"2\">全部</option>");
    } else {
        $("#listTypeSelect").html("<option value=\"0\" selected=\"selected\">所有</option><option value=\"3\">我的收藏或关注</option>");
    }
}

//点击用户列表
function userListClickFunction() {
    $("#userListClick").click(function () {
        getUserList(null);
    });
}

//用户状态按钮的文本内容
function changeUserOrObjectStatusBtnText(statusNum) {
    if (statusNum >= 0) {
        return "封禁";
    } else if (statusNum == -1) {
        return "解封";
    }
}


//变更用户当前状态
function changeUserStatusFunction(userId , sessionId , checkedUserId , changeStatus) {
    var msgTmp = null;
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:"{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId
        +"\",\"checkedUserId\":\""+checkedUserId+"\",\"status\":\""+changeStatus+"\"}",
        url:"userBase/changeUserStatus",
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

//给用户列表设置封禁按钮
function sealUserStatusFunction(userLevel) {
    if (userLevel == 1) {
        $(".checkedChangeUserStatus").click(function () {
            checkedUserId = $(this).parent().find(".checkedUserId").text();
            var successNum = changeUserStatusFunction(userMsg.userId , userMsg.sessionId , checkedUserId , -1);
            var userStatus = userStatusView(successNum);
            var sealBtnText = changeUserOrObjectStatusBtnText(successNum);
            $(this).parent().find(".checkedUserStatus").text(userStatus);
            $(this).text(sealBtnText);
        });
    } else {
        $(".checkedChangeUserStatus").hide();
    }

}

//获取用户列表
function getUserList(objectIdTmp) {
    userOrObject = 1;
    var userId = null;
    var sessionId = null;
    var userLevel = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined || userMsg.userLevel!=undefined) {
            userId = userMsg.userId;
            sessionId = userMsg.sessionId;
            userLevel = userMsg.userLevel;
            sortType = $("#sortTypeSelect").val();
            listType = $("#listTypeSelect").val();
            pageNum = $("#pageNum").text();
            $.ajax({
                type:'post',
                contentType :'application/json',
                data:"{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId
                +"\",\"objectId\":\""+objectIdTmp+"\",\"listType\":\""+listType
                +"\",\"pageNum\":\""+pageNum +"\"}",
                url:"userList/getUserList",
                dataType : 'json',
                success:function (date) {
                    if(date.result!=200){
                        $("#objectOrUserList").html(date.msg);
                    }
                    else {
                        msg = jQuery.parseJSON(date.msg);
                        $("#objectOrUserList").html("");
                        $.each(msg,function (index,userBase) {
                            var userStatus = userStatusView(userBase.userStatus);
                            var sealBtnText = changeUserOrObjectStatusBtnText(userStatus);
                            if (userBase.userStatus == 0 || userBase.userStatus == -1) {
                                sealBtnText = changeUserOrObjectStatusBtnText(userBase.userStatus);
                            }
                            $("#objectOrUserList").append("<div class=\"col-md-4 column userBaseCol\"><blockquote><p class=\"checkedContact\">"+userBase.contact+"</p><small><label class=\"checkedUserId\" style=\"display:none;\">"+userBase.userId+"</label><cite class=\"checkedUserName btn btn-link\">"+userBase.userName+"</cite></small></blockquote><div class=\"row clearfix\"><div class=\"col-md-6 column\"><label>状态：</label></div><div class=\"col-md-6 column\"><label class=\"checkedUserStatus pull-right\">"+userStatus+"</label></div><div class=\"col-md-4 column\"></div></div><button type=\"button\" class=\"btn btn-primary pull-right checkedChangeUserStatus\">"+sealBtnText+"</button></div>");
                        });
                        sealUserStatusFunction(userLevel);
                        checkedUserMsgFuntion();
                    }
                },
                error : function() {
                    alert("出错了o(╯□╰)o");
                }
            })
        }
    }
}

//点击关注按钮
function userRelationTypeFunction() {
    $("#userRelationType").click(function () {
        checkedUserId = $(this).parent().children("#checkedUserId").text();
        $.ajax({
            type:'post',
            contentType :'application/json',
            data:"{\"userId\":\""+userMsg.userId+"\",\"sessionId\":\""+userMsg.sessionId+"\",\"checkedUserId\":\""
            +checkedUserId+"\"}",
            url:"userBase/changeUserRelation",
            dataType : 'json',
            success:function (date) {
                if(date.result!=200){
                    alert(date.msg);
                }
                else {
                    var relationType = date.msg;
                    userRelationColor = glyphiconColor(relationType);
                    $("#userRelationGlyphicon").css("color",userRelationColor);
                }
            },
            error : function() {
                alert("出错了o(╯□╰)o");
            }
        })
    });
}

//退出登录按钮
function logoutFunction() {
    $("#logout").click(function () {
        $.ajax({
            type:'post',
            contentType :'application/json',
            data:"{\"userId\":\""+userMsg.userId+"\",\"sessionId\":\""+userMsg.sessionId+"\"}",
            url:"userBase/loginOut",
            dataType : 'json',
            success:function (date) {
                if(date.result!=200){
                    alert(date.msg);
                }
                else {
                    msg = date.msg;
                    if (userMsg.userId == msg) {
                        userMsg = null;
                        loginFromHtmlLoad();
                        listTypeFunction(0);
                    }
                }
            },
            error : function() {
                alert("出错了o(╯□╰)o");
            }
        });
    });
}

//点击返回用户自身信息按钮
function userSelfClick() {
    $("#userSelf").click(function () {
        userMsgToView(userMsg);
    });
}

//点击编辑按钮
function reUserMessageFunction() {
    $("#reUserMessage").click(function () {
        $("#userMessageView").html("<form role=\"form\"><div class=\"form-group\"><label for=\"userNameInput\">用户名</label><input type=\"text\" class=\"form-control\" id=\"userNameInput\" /></div><div class=\"form-group\"><label for=\"userPwInput\">密码</label><input type=\"password\" class=\"form-control\" id=\"userPwInput\" /></div><div class=\"form-group\"><label for=\"emailInput\">邮箱</label><input type=\"email\" class=\"form-control\" id=\"emailInput\" /></div><div class=\"form-group\"><label for=\"phoneInput\">手机号</label><input type=\"tel\" class=\"form-control\" id=\"phoneInput\" /></div><div class=\"form-group\"><label for=\"contactInput\">个人简介：</label><input type=\"text\" class=\"form-control\" id=\"contactInput\" /></div></form><button id=\"changeUserMessage\" type=\"button\" class=\"btn btn-primary\">确定</button><button id=\"userSelf\" class=\"btn btn-link\">返回</button>");
        $("#userNameInput").val(userMsg.userName);
        $("#emailInput").val(userMsg.email);
        $("#phoneInput").val(userMsg.phone);
        $("#contactInput").val(userMsg.contact);
        changeUserMessageClick();
        userSelfClick();
    });
}

//点击用户信息确认编辑按钮
function changeUserMessageClick() {
    $("#changeUserMessage").click(function () {
        var userNameValue = $("#userNameInput").val();
        var userPwValue = $("#userPwInput").val();
        userPwValue = myJsSHAFunction(userPwValue);
        var emailValue = $("#emailInput").val();
        var phoneValue = $("#phoneInput").val();
        var conactValue = $("#contactInput").val();
        checkUserNameBealoon = checkUserName();
        checkUserPwBealoon = checkUserPw();
        checkEmailBealoon = checkEmail();
        checkPhoneBealoon = checkPhone();
        if (checkUserNameBealoon && checkUserPwBealoon && checkEmailBealoon && checkPhoneBealoon) {
            $.ajax({
                type:'post',
                contentType :'application/json',
                data:"{\"userId\":\""+userMsg.userId+"\",\"sessionId\":\""+userMsg.sessionId
                +"\",\"userName\":\""+userNameValue+"\",\"userPw\":\""+userPwValue+"\",\"email\":\""
                +emailValue+"\",\"phone\":\""+phoneValue+"\",\"contact\":\""+conactValue+"\"}",
                url:"userBase/updateUserMessageById",
                dataType : 'json',
                success:function (date) {
                    if(date.result!=200){
                        alert(date.msg);
                    }
                    else {
                        msg = jQuery.parseJSON(date.msg);
                        userMsgToView(msg);
                        userMsg = msg;//更新用户信息缓存
                    }
                },
                error : function() {
                    alert("出错了o(╯□╰)o");
                }
            })
        }
    });
}

//点击用户名获取该用户信息详情
function checkedUserMsgFuntion() {
    $(".checkedUserName").click(function () {
        checkedUserId = $(this).parent().children(".checkedUserId").text();
        var userId = null;
        var sessionId = null;
        if (userMsg != undefined || userMsg != null) {
            if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
                userId = userMsg.userId;
                sessionId = userMsg.sessionId;
                dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\",\"checkedUserId\":\""+checkedUserId+"\"}";
            }
        } else {
            dataValue = "{\"checkedUserId\":\""+checkedUserId+"\"}";
        }
        $.ajax({
            type:'post',
            contentType :'application/json',
            data:dataValue,
            url:"userBase/getUserMessageById",
            dataType : 'json',
            success:function (date) {
                if(date.result!=200){
                    alert(date.msg);
                }
                else {
                    msg = jQuery.parseJSON(date.msg);
                    userMsgToView(msg);
                }
            },
            error : function() {
                alert("出错了o(╯□╰)o");
            }
        })
    });
}

//用户等级展示
function userLevelView(userlevel) {
    if (userlevel == 1){
        return "管理员";
    } else {
        return "普通用户";
    }
}

//用户状态展示
function userStatusView(userStatus) {
    if (userStatus == -1) {
        return "被封禁";
    } else if (userStatus == 0) {
        return "正常";
    } else {
        return "被举报"+userStatus+"次";
    }
}

//设置字体图标颜色
function glyphiconColor(statusNum) {
    if (statusNum == 1) {
        return "#FF3333";
    } else {
        return "#5e5e5e";
    }
}

//用户详细信息返回json解析
function userMsgToView(msg) {
    var userId = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.userId!=null) {
            userId = userMsg.userId;
        }
    }
    if ((msg.relationType == 0 || msg.relationType == 1) && (msg.userId != userId)) {
        userLevelViewTmp = userLevelView(msg.userLevel);
        userRelationColor = glyphiconColor(msg.relationType);
        $("#userMessageView").html("<form role=\"form\"><div class=\"form-group\"><label>用户名：</label><label>"+msg.userName+"</label></div><div class=\"form-group\"><label>资源量：</label><label>"+msg.resource+"</label></div><div class=\"form-group\"><label>权限等级：</label><label>"+userLevelViewTmp+"</label><div class=\"form-group\"><label>个人简介：</label><br/><label>"+msg.contact+"</label></div></form><label id=\"checkedUserId\" style=\"display:none;\">"+msg.userId+"</label><button id=\"userRelationType\" class=\"btn btn-primary\"><em id=\"userRelationGlyphicon\" style=\"color: "+userRelationColor+"\" class=\"glyphicon glyphicon-heart\" aria-hidden=\"true\"></em>关注</button><button id=\"userSelf\" class=\"btn btn-link\">返回</button>");
        userSelfClick();
        userRelationTypeFunction();
    } else {
        userLevelViewTmp = userLevelView(msg.userLevel);
        userStatusViewTmp = userStatusView(msg.userStatus);
        $("#userMessageView").html("<form role=\"form\"><div class=\"form-group\"><label>用户名：</label><label>"+msg.userName+"</label></div><div class=\"form-group\"><label>资源量：</label><label>"+msg.resource+"</label></div><div class=\"form-group\"><label>权限等级：</label><label>"+userLevelViewTmp+"</label></div><div class=\"form-group\"><label>邮箱：</label><label id=\"email\">"+msg.email+"</label></div><div class=\"form-group\"><label>手机号：</label><label id=\"phone\">"+msg.phone+"</label></div><div class=\"form-group\"><label>当前状态：</label><label>"+userStatusViewTmp+"</label></div><div class=\"form-group\"><label>个人简介：</label><br/><label>"+msg.contact+"</label></div></form><button id=\"reUserMessage\" class=\"btn btn-primary\">编辑</button><button id=\"logout\" class=\"btn btn-link\">退出</button>");
        listTypeFunction(msg.userLevel);
        reUserMessageFunction();
        logoutFunction();
    }
}

// 注册按钮点击操作
function registBtnClick() {
    $("#registBtn").click(function () {
        var userNameValue = $("#userNameInput").val();
        var userPwValue = $("#userPwInput").val();
        userPwValue = myJsSHAFunction(userPwValue);
        var emailValue = $("#emailInput").val();
        var phoneValue = $("#phoneInput").val();
        var conactValue = $("#contactInput").val();
        checkUserNameBealoon = checkUserName();
        checkUserPwBealoon = checkUserPw();
        checkEmailBealoon = checkEmail();
        checkPhoneBealoon = checkPhone();
        if (checkUserNameBealoon && checkUserPwBealoon && checkEmailBealoon && checkPhoneBealoon) {
            $.ajax({
                type:'post',
                contentType :'application/json',
                data:"{\"userName\":\""+userNameValue+"\",\"userPw\":\""+userPwValue+"\",\"email\":\""
                +emailValue+"\",\"phone\":\""+phoneValue+"\",\"contact\":\""+conactValue+"\"}",
                url:"userBase/userRegist",
                dataType : 'json',
                success:function (date) {
                    if(date.result!=200){
                        alert(date.msg);
                    }
                    else {
                        msg = jQuery.parseJSON(date.msg);
                        userMsgToView(msg);
                        userMsg = msg;//更新用户信息缓存
                        getObjectList(null);
                    }
                },
                error : function() {
                    alert("出错了o(╯□╰)o");
                }
            })
        }
    });
}

//加载注册表单
function registFromHtmlLoad() {
    $("#userMessageView").html("<form role=\"form\"><div class=\"form-group\"><label for=\"userNameInput\">用户名</label><input type=\"text\" class=\"form-control\" id=\"userNameInput\" /></div><div class=\"form-group\"><label for=\"userPwInput\">密码</label><input type=\"password\" class=\"form-control\" id=\"userPwInput\" /></div><div class=\"form-group\"><label for=\"emailInput\">邮箱</label><input type=\"email\" class=\"form-control\" id=\"emailInput\" /></div><div class=\"form-group\"><label for=\"phoneInput\">手机号</label><input type=\"tel\" class=\"form-control\" id=\"phoneInput\" /></div><div class=\"form-group\"><label for=\"contactInput\">个人简介：</label><input type=\"text\" class=\"form-control\" id=\"contactInput\" /></div></form><button id=\"registBtn\" type=\"button\" class=\"btn btn-primary\">注册</button><button id=\"loginLink\" type=\"button\" class=\"btn btn-link\">登录</button>");
    registBtnClick();
    $("#loginLink").click(loginFromHtmlLoad);
}

//登录按钮点击操作
function loginBtnClick() {
    $("#loginBtn").click(function(){
        var userNameValue = $("#userNameInput").val();
        var userPwValue = $("#userPwInput").val();
        userPwValue = myJsSHAFunction(userPwValue);
        checkUserNameBealoon = checkUserName();
        checkUserPwBealoon = checkUserPw();
        if (checkUserNameBealoon && checkUserPwBealoon) {
            $.ajax({
                type:'post',
                contentType :'application/json',
                data:"{\"userName\":\""+userNameValue+"\",\"userPw\":\""+userPwValue+"\"}",
                url:"userBase/userLogin",
                dataType : 'json',
                success:function (date) {
                    if(date.result!=200){
                        alert(date.msg);
                    }
                    else {
                        msg = jQuery.parseJSON(date.msg);
                        userMsgToView(msg);
                        userMsg = msg;//更新用户信息缓存
                        getLabelListFunction();
                        getObjectList(null);
                    }
                },
                error : function() {
                    alert("出错了o(╯□╰)o");
                }
            })
        }
    });
}

//加载登录表单
function loginFromHtmlLoad() {
    $("#userMessageView").html("<form role=\"form\"><div class=\"form-group\"><label for=\"userNameInput\">用户名</label><input type=\"text\" class=\"form-control\" id=\"userNameInput\" /></div><div class=\"form-group\"><label for=\"userPwInput\">密码</label><input type=\"password\" class=\"form-control\" id=\"userPwInput\" /></div></form><button id=\"loginBtn\" type=\"button\" class=\"btn btn-primary\">登录</button><button id=\"registLink\" type=\"button\" class=\"btn btn-link\">注册</button>");
    loginBtnClick();
    $("#registLink").click(registFromHtmlLoad);
}

//验证用户名
function checkUserName() {
    if ($("#userNameInput").val() == "") {
        alert("用户名不能为空!");
        $("#userNameInput").focus();
        return false;
    }
    return true;
}

//验证用户密码
function checkUserPw() {
    if ($("#userPwInput").val() == "") {
        alert("密码不能为空!");
        $("#userPwInput").focus();
        return false;
    }
    return true;
}

//验证邮箱
function checkEmail() {
    if ($("#emailInput").val() == "") {
        alert("邮箱不能为空!");
        $("#emailInput").focus();
        return false;
    }
    if (!$("#emailInput").val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)) {
        alert("邮箱格式不正确");
        $("#emailInput").focus();
        return false;
    }
    return true;
}

//验证手机号码
function checkPhone() {
    if ($("#phoneInput").val() == "") {
        alert("手机号码不能为空！");
        $("#phoneInput").focus();
        return false;
    }

    if (!$("#phoneInput").val().match(/^((1[0-9]{2})+\d{8})$/)) {
        alert("手机号码格式不正确！");
        $("#phoneInput").focus();
        return false;
    }
    return true;
}
