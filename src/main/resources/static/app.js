var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#logs").show();
    }
    else {
        $("#logs").hide();
    }
    $("#logBody").html("");
}

function connect() {
    var socket = new SockJS('/log-monitor-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.send("/app/register", {}, "");
        stompClient.subscribe('/topic/logs', function (greeting) {
        	appendLogs(JSON.parse(greeting.body).logdata);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function appendLogs(message) {
    $("#logBody").append("<tr><td>" + message + "</td></tr>");
    $("#logBody tr:last-child")[0].scrollIntoView();
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
   // $( "#send" ).click(function() { sendName(); });
});