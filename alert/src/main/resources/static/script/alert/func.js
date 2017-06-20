/**
 * Created by zliu on 17/5/31.
 */

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}


function init_list(node) {
    $.get("/alert/list",function (data,status) {
        var result = JSON.parse(data);
        for(var i=0;i < result.data.length;i++){
            $("#"+node).append("<a href='/alert/info.html?name="+result.data[i].name+"' class='list-group-item'>"+result.data[i].name+"</a>");
        }
    })
}

function init_log_list() {
    var url = "/alert/list/logs";
    var name = getUrlParam("name");
    if(name){
        url = url+"/"+name;
    }
    $.get(url,function (data,status) {
        var result = JSON.parse(data);
        for(var i=0;i < result.data.length;i++){
            $("#alert-log-list").append("<a href='#' class='list-group-item'>"
                +result.data[i].name+"&nbsp;&nbsp;&nbsp;&nbsp;"
                +result.data[i].createtime+"&nbsp;&nbsp;&nbsp;&nbsp;"
                +result.data[i].content+"&nbsp;&nbsp;&nbsp;&nbsp;"
                +result.data[i].notifications+"</a>");
        }
    })
}

function init_info(){
    var name = getUrlParam('name');
    $.get("/alert/get/"+name,function (data,status) {
        var result = JSON.parse(data);
        if(result.code==0) {
            if (result.data.host) {
                 $("#host").val(result.data.host);
             }
             if(result.data.port){
                 $("#port").val(result.data.port);
             }
            $("#name").val(result.data.name);
            $("#hidden-name").val(result.data.name);
            $("#index").val(result.data.index);
            $("#filter").val(JSON.stringify(result.data.filter));
            $("#searchkey").val(result.data.searchkey);
            $("#field").val(result.data.field);
            $("#cvalue").val(result.data.cvalue);
            $("#conditionvalue").val(result.data.conditionvalue);
            $("#ccount").val(result.data.ccount);
            $("#conditioncount").val(result.data.conditioncount);
            $("#notifications").val(result.data.notifications);
            $("#emailtitle").val(result.data.emailtitle);
            $("#emailtemplate").val(result.data.emailtemplate);
        }else{
            alert("Request reuslt error "+result.msg);
        }


    })

}

function deleteAlert(name) {
    if(confirm('DELETE?')){
        name = $("#hidden-name").val();
        var url = "/alert/"+name;
        $.ajax({
            type: "DELETE",
            url: url,
            success: function( result ) {
                var data = JSON.parse(result);
                if(data.code==0){
                    window.location.href="/alert/index.html";
                }
            }
        });
    }
}
function toLog(){
    window.location.href="/alert/logs.html?name="+$("#hidden-name").val();
}

function createAlert(){
    var url = "/alert/"+$("#name").val();
    var alertData={};
    alertData.host=$("#host").val();
    alertData.port=$("#port").val();
    alertData.name=$("#name").val();
    alertData.index=$("#index").val();
    alertData.searchkey=$("#searchkey").val();
    alertData.field=$("#field").val();
    alertData.cvalue=$("#cvalue").val();
    alertData.conditionvalue=$("#conditionvalue").val();
    alertData.ccount=$("#ccount").val();
    alertData.conditioncount=$("#conditioncount").val();
    alertData.notifications=$("#notifications").val();
    alertData.emailtitle=$("#emailtitle").val();
    alertData.emailtemplate=$("#emailtemplate").val();
    if($("#filter").val()){
        alertData.filter=JSON.parse($("#filter").val());
    }
    $.ajax({
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(alertData),
        dataType: "json",
        url: url,
        success: function( result ) {
            if(result.code==0){
                window.location.href="/alert/index.html";
            }
        }
    });

}

function updateAlert(){
    var url = "/alert/"+$("#hidden-name").val();
    var alertData={};
    alertData.host=$("#host").val();
    alertData.port=$("#port").val();
    alertData.name=$("#name").val();
    alertData.index=$("#index").val();
    alertData.searchkey=$("#searchkey").val();
    alertData.field=$("#field").val();
    alertData.cvalue=$("#cvalue").val();
    alertData.conditionvalue=$("#conditionvalue").val();
    alertData.ccount=$("#ccount").val();
    alertData.conditioncount=$("#conditioncount").val();
    alertData.notifications=$("#notifications").val();
    alertData.emailtitle=$("#emailtitle").val();
    alertData.emailtemplate=$("#emailtemplate").val();
    if($("#filter").val()){
        alertData.filter=JSON.parse($("#filter").val());
    }
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(alertData),
        dataType: "json",
        url: url,
        success: function( result ) {
            if(result.code==0){
                window.location.reload();
            }
        }
    });

}

function refresh() {
    var url = "/alert/refresh";
    $.post(url,function (data,status) {
        var result = JSON.parse(data);
        if(result.code==0){
            alert("Refresh success");
        }
    })
}


