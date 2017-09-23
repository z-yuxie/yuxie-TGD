/**
 * Created by 147356 on 2017/4/27.
 */
//初始化对象列表
reLabelListClick()
getLabelListFunction();

function reLabelListClick() {
    $("#reLabekListClick").click(function () {
        getLabelListFunction();
    });
}

//获取标签列表
function getLabelListFunction() {
    var userId = null;
    var sessionId = null;
    if (userMsg != undefined || userMsg != null) {
        if (userMsg.userId!=undefined || userMsg.sessionId!=undefined) {
            userId = userMsg.userId;
            sessionId = userMsg.sessionId;
        }
    }
    if (userId!=null && sessionId!=null) {
        dataValue = "{\"userId\":\""+userId+"\",\"sessionId\":\""+sessionId+"\"}";
    } else {
        dataValue = "{}";
    }
    $.ajax({
        type:'post',
        contentType :'application/json',
        data:dataValue,
        url:"label/getLabelList",
        dataType : 'json',
        success:function (date) {
            if(date.result!=200){
                alert(date.msg);
            }
            else {
                msg = jQuery.parseJSON(date.msg);
                $("#labelList").html("<br/>");
                $.each(msg,function (index,labelBase) {
                    var relationType = labelBase.relationType;
                    var labelRelationColor = glyphiconColor(relationType);
                    $("#labelList").append("<div class=\"btn-group col-md-12\"><label class=\"labelId\" style=\"display:none;\">"+labelBase.labelId+"</label><button class=\"btn btn-primary labelGlyphiconBtn\" type=\"button\"><em style=\"color: "+labelRelationColor+"\" class=\"glyphicon glyphicon-heart labelGlyphicon\" aria-hidden=\"true\"></em></button><button class=\"btn btn-default labelName\" type=\"button\">"+labelBase.labelName+"&nbsp;<span class=\"badge\">"+labelBase.hotNum+"</span></button></div>");
                });
                labelRelationTypeFunction();
                getObjectListByLabelName();
            }
        },
        error : function() {
            alert("出错了o(╯□╰)o");
        }
    })
}

//点击关注标签按钮
function labelRelationTypeFunction() {
    $(".labelGlyphiconBtn").click(function () {
        var labelId = $(this).parent().children(".labelId").text();
        $(this).children(".labelGlyphicon").addClass("changeColor");
        $.ajax({
            type:'post',
            contentType :'application/json',
            data:"{\"userId\":\""+userMsg.userId+"\",\"sessionId\":\""+userMsg.sessionId+"\",\"labelId\":\""+labelId+"\"}",
            url:"label/changeStatusNum",
            dataType : 'json',
            success:function (date) {
                if(date.result!=200){
                    alert(date.msg);
                }
                else {
                    var relationType = date.msg;
                    var labelRelationColor = glyphiconColor(relationType);
                    $(".changeColor").css("color",labelRelationColor);
                    $("em").removeClass("changeColor");
                }
            },
            error : function() {
                alert("出错了o(╯□╰)o");
            }
        })
    });
}

//点击标签名获取对象列表
function getObjectListByLabelName() {
    $(".labelName").click(function () {
        var labelName = $(this).text();
        getObjectList(labelName);
    });
}