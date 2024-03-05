const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws-register'
});

let parentId = null; // Variable to store the userId

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    // Subscribe to the appropriate destination with the userId
    stompClient.subscribe(
        "/user/" + parentId + "/user/child-geo", (greeting) => {
            showGreeting(JSON.parse(greeting.body).username +" "+ JSON.parse(greeting.body).latitude+" "+JSON.parse(greeting.body).longitude);
        });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    // Get the userId from the input field
    parentId = $("#parentId").val();
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendGeoData() {
    console.log("starting to send");
    var geoData =
        {
            userId: $("#userId").val(),
            latitude: "37.7749",
            longitude: "122.4194",
            timestamp: "2024-03-01T15:45:00",
            timezone: "PST",
            username: $("#username").val(),
            battery: "1"
        };

    stompClient.publish({
        destination: "/geo",
        body: JSON.stringify(geoData)
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendGeoData());
});
