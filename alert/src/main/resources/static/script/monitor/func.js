/**
 * Created by zliu on 17/5/31.
 */

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}


function init() {
    $.get("http://35.161.254.146:9200/_cluster/health",function (result,status) {
        $("#cluster_name").append(result.cluster_name);
        $("#status").append(result.status);
        $("#timed_out").append(result.timed_out);
        $("#number_of_nodes").append(result.number_of_nodes);
        $("#number_of_data_nodes").append(result.number_of_data_nodes);
        $("#active_primary_shards").append(result.active_primary_shards);
        $("#active_shards").append(result.active_shards);
        $("#relocating_shards").append(result.relocating_shards);
        $("#initializing_shards").append(result.initializing_shards);
        $("#unassigned_shards").append(result.unassigned_shards);
        $("#delayed_unassigned_shards").append(result.delayed_unassigned_shards);
        $("#number_of_pending_tasks").append(result.number_of_pending_tasks);
        $("#number_of_in_flight_fetch").append(result.number_of_in_flight_fetch);
        $("#task_max_waiting_in_queue_millis").append(result.task_max_waiting_in_queue_millis);
        $("#active_shards_percent_as_number").append(result.active_shards_percent_as_number);
    })
}

function nodes() {
    $.get($("#restNodes").val(),function (result,status) {

    })
}


