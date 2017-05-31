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
            $("#"+node).append("<li class='list-group-item' ><a href='/alert/info.html?name="+result.data[i].name+"'>"+result.data[i].name+"</a></li>");
        }
    })
}

function init_info(){
    var name = getUrlParam('name');
    $.get("/alert/get/"+name,function (data,status) {
        var result = JSON.parse(data);
        if(result.code==0){
            $("#alertName").val(result.data.name);
            $("#index").val(result.data.index);
            $("#users").val(result.data.notifications);
        }else{
            alert("Request reuslt error "+result.msg);
        }


    })

}

