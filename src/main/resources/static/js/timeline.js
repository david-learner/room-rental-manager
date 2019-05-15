var locations = [];
var events = [];

function getLocations(callback) {
    $.ajax({
        type: 'GET',
        dataType: "json",
        url: '/api/locations',
    }).done(callback);
}

function getEvents(date, callback) {
    $.ajax({
        type: 'GET',
        dataType: "json",
        url: '/api/events/days/' + date + '/all',
        // 요청에 성공하면, 요청한 JSON 데이터를 done()으로 넘겨준다
    }).done(callback);
}

function getEmptyEvents(date, callback) {
    $.ajax({
        type: 'GET',
        dataType: "json",
        url: '/api/events/days/' + date + '/emptyevents',
        // 요청에 성공하면, 요청한 JSON 데이터를 done()으로 넘겨준다
    }).done(callback);
}

function create(event) {
    event.preventDefault(); // avoid to execute the actual submit of the form.
    if (!checkEventInputForm()) {
        return false;
    }
    var form = $('#eventForm');
    var url = '/api/events';

    $.ajax({
        type: 'POST',
        url: url,
        data: form.serialize(), // serializes the form's elements.
    }).done(function (data) {
        console.log("SUCCESS CREATE EVENT");
        location.reload(); // refresh(F5)
    }).fail(function (error) {
        // console.log(JSON.stringify(error));
        alert(error.responseJSON.message);
    });
}


function updateEvent(event) {
    event.preventDefault();
    var form = $('#update-delete-form');
    var id = $("#update-delete-form input[name='id']").val();
    console.log("PUT /api/events/" + id);
    $.ajax({
        type: 'PUT',
        url: '/api/events/' + id,
        data: form.serialize(),
    }).done(function (data) {
        console.log("수정 성공");
        location.reload(); // refresh(F5)
    }).fail(function (error) {
        // console.log(JSON.stringify(error));
        alert(error.responseJSON.message);
    });
}

function deleteEvent(event) {
    event.preventDefault();
    var form = $('#update-delete-form');
    var id = $("#update-delete-form input[name='id']").val();
    console.log("DELETE /api/events/" + id);
    $.ajax({
        type: 'DELETE',
        url: '/api/events/' + id,
        data: form.serialize(),
        success: function (data) {
            alert("삭제 성공");
            // 부분 갱신으로 변경하기
            location.reload();
        }
    });
}

function checkEventInputForm() {
    var name = document.eventInputForm.lessorname.value;
    var endDateTime = document.eventInputForm.enddatetime.value;
    if (name == "") {
        alert("이름을 입력해주세요");
        name.focus();
        return false;
    }

    if (endDateTime == "") {
        alert("대여시간을 입력해주세요");
        endDateTime.focus();
        return false;
    }
    return true;
}

function setLocationsToDropdownMenu(data) {
    var roomlist = document.getElementById("room-list");
    var locationsUpdateDeleteForm = document.getElementById("locations-update-delete");
    for (var index in data) {
        var roomInfo = data[index].roomNo + ' : ' + data[index].pianoCategory + ', ' + data[index].pianoCount;
        var roomNo = data[index].roomNo;

        var option = document.createElement("OPTION");
        option.innerText = roomInfo;
        option.value = roomNo;

        var optionClone = document.createElement("OPTION");
        optionClone.innerText = roomInfo;
        optionClone.value = roomNo;

        roomlist.options.add(option);
        locationsUpdateDeleteForm.options.add(optionClone);
    }
}

function isNow(startDateTime, endDateTime) {
    // 분단위까지만 비교하기, 초단위까지 비교하니깐 동일한 시간(분)에서 체크 안 됌
    var now = new Date().setSeconds(0, 0).toString();
    // setSeconds를 하면 1970년0시0분0초(UTC)로부터 지금까지의 시간이 밀리초로 환산되서 나온다
    // The number of milliseconds between 1 January 1970 00:00:00 UTC and the updated date.
    var start = startDateTime.setSeconds(0, 0);
    var end = endDateTime.setSeconds(0, 0);
    // console.log("n : " + now + " s : " + start + " e : " + end);
    if (now >= start && now <= end) {
        return true;
    }
    return false;
}

function drawEvents(data) {
    // console.log(JSON.stringify(data));

    var timeRows = document.getElementById("time-events").children;

    for (var i = 0; i < data.length; i++) {
        // 필요한 데이터
        var eventId = data[i].id;
        var locationId = data[i].location.id;
        var locationNumber = data[i].location.roomNo;
        var lessorName = data[i].lessorName;
        var startDateTime = new Date(data[i].startDateTime);
        var endDateTime = new Date(data[i].endDateTime);

        console.log(timeRows);
        console.log("loc id : " + locationId);
        console.log("timeRows[id-1] " + timeRows[locationId - 1]);

        var timeEventElement1 = createTimeEventElement(eventId, locationId, locationNumber, lessorName, startDateTime, endDateTime);
        timeRows[locationId - 1].appendChild(timeEventElement1);
    }
}

function drawEmptyEvents(data) {
    console.log(JSON.stringify(data));

    for (var i = 0; i < data.length; i++) {
        // 필요한 데이터
        var eventId = data[i].id;
        var locationId = data[i].location.id;
        var locationNumber = data[i].location.roomNo;
        var lessorName = data[i].lessorName;
        var startDateTime = new Date(data[i].startDateTime);
        var endDateTime = new Date(data[i].endDateTime);

        var emptyEvent = createTimeEmptyEventElement(eventId, locationId, locationNumber, lessorName, startDateTime, endDateTime);
        document.getElementById(locationNumber).appendChild(emptyEvent);
    }
}

function drawLocations(data) {
    // console.log(JSON.stringify(data));
    for (var i in data) {
        locations.push(data[i]);
        // id가 time-locations인 태그 아래에 방번호를 나타내는 div태그 삽입
        var location = document.createElement("div");
        location.classList.add("time-location");
        var locationInfo = data[i].roomNo + "/" + data[i].pianoCategory.substring(0, 1) + "/" + data[i].pianoCount;
        location.append(document.createTextNode(locationInfo));
        document.getElementById("time-locations").appendChild(location);

        // id가 time-events인 태그 아래에 각 방의 대여 기록을 나타내는 div태그 삽입
        var event = document.createElement("div");
        event.classList.add("d-flex", "flex-row", "time-row");
        document.getElementById("time-events").appendChild(event);
    }
}

function drawEmptyLocations(data) {
    for (var i in data) {
        locations.push(data[i]);

        // 방정보를 담은 엘리먼트를 제일 앞에 배치한다
        var emptyEvents = document.createElement("div");
        emptyEvents.setAttribute("id", data[i].roomNo);
        emptyEvents.classList.add("d-flex", "border-primary", "border-bottom");

        var locationInfo = data[i].roomNo + "/" + data[i].pianoCategory.substring(0, 1) + "/" + data[i].pianoCount;
        var location = document.createElement("div");
        location.classList.add("mb-1", "mr-2");
        location.textContent = locationInfo;

        // emptyEvents.appendChild
        emptyEvents.append(location, createDummyEventElement());
        
        // 모든 방개수를 4개의 열로 나누어 넣는다
        var divider = Math.floor(data.length / 4) + 1; // 11
        var quotient = Math.floor(i / divider); // quotient, remainder

        switch (quotient) {
            case 0 :
                document.getElementById("empty-events-1").appendChild(emptyEvents);
                break;
            case 1 :
                document.getElementById("empty-events-2").appendChild(emptyEvents);
                break;
            case 2 :
                document.getElementById("empty-events-3").appendChild(emptyEvents);
                break;
            default : // case 3 and 4
                document.getElementById("empty-events-4").appendChild(emptyEvents);
        }
    }
}

function createTimeEmptyEventElement(eventId, locationId, locationNumber, lessorName, startDateTime, endDateTime) {
    // 데이터를 텍스트로
    var startDateTimeText = leftZeroPad(startDateTime.getHours()) + ':' + leftZeroPad(startDateTime.getMinutes());
    var endDateTimeText = leftZeroPad(endDateTime.getHours()) + ':' + leftZeroPad(endDateTime.getMinutes());

    var startDateTimeNode = document.createElement("div");
    startDateTimeNode.setAttribute("name", "startDateTime");
    startDateTimeNode.textContent = startDateTimeText;
    var endDateTimeNode = document.createElement("div");
    endDateTimeNode.setAttribute("name", "endDateTime");
    endDateTimeNode.textContent = endDateTimeText;
    var dateTimeNode = document.createElement("div");
    dateTimeNode.setAttribute("name", "dateTimes");
    dateTimeNode.classList.add("pl-1", "pr-1", "border-secondary", "border");
    dateTimeNode.append(startDateTimeNode, endDateTimeNode);
    if (endDateTime < new Date() && !(lessorName == "dummy")) {
        console.log("지나간 시간");
        dateTimeNode.classList.add("d-none");
    }

    // 타임 엘리먼트 생성
    var timeEventElement = document.createElement("div");

    // 만약 지금 진행중인 이벤트면 백그라운드 색 넣기
    if (isNow(startDateTime, endDateTime)) {
        timeEventElement.classList.add("time-now");
    }

    var dateTimeElement = document.createElement("div");

    // div에 text 넣기
    dateTimeElement.appendChild(dateTimeNode);

    // event div에 내용 추가하기
    timeEventElement.append(dateTimeElement);

    return timeEventElement;
}

function createDummyEventElement() {
    var dummy = createTimeEmptyEventElement(null, null, null, "dummy", new Date(), new Date());
    dummy.classList.add("invisible");
    return dummy;
}

function createTimeEventElement(eventId, locationId, locationNumber, lessorName, startDateTime, endDateTime) {
    // 데이터를 텍스트로
    if (startDateTime != null) {
        var startDateTimeText = leftZeroPad(startDateTime.getHours()) + ':' + leftZeroPad(startDateTime.getMinutes());
    } else {
        var startDateTimeText = "empty";
    }

    if (endDateTime != null) {
        var endDateTimeText = leftZeroPad(endDateTime.getHours()) + ':' + leftZeroPad(endDateTime.getMinutes());
    } else {
        var endDateTimeText = "empty";
    }

    var startDateTimeNode = document.createElement("div");
    startDateTimeNode.setAttribute("name", "startDateTime");
    startDateTimeNode.textContent = startDateTimeText;
    var endDateTimeNode = document.createElement("div");
    endDateTimeNode.setAttribute("name", "endDateTime");
    endDateTimeNode.textContent = endDateTimeText;
    var dateTimeNode = document.createElement("div");
    dateTimeNode.setAttribute("name", "dateTimes");
    dateTimeNode.append(startDateTimeNode, endDateTimeNode);

    // 타임 엘리먼트 생성
    var timeEventElement = document.createElement("div");
    timeEventElement.classList.add("d-flex", "flex-column", "time-event");
    // 만약 지금 진행중인 이벤트면 백그라운드 색 넣기
    // if (startDateTime != null && endDateTime != null && lessorName != "공실") {
    if (isNow(startDateTime, endDateTime)) {
        timeEventElement.classList.add("time-now");
    }
    // }
    if (lessorName != "공실") {
        timeEventElement.setAttribute("onclick", "loadUpdateDeleteForm(event)");
    }

    var eventIdElement = document.createElement("div");
    eventIdElement.setAttribute("style", "display: none;");
    eventIdElement.setAttribute("name", "eventId");

    var locationIdElement = document.createElement("div");
    locationIdElement.setAttribute("style", "display: none;");
    locationIdElement.setAttribute("name", "locationId");

    var locationNumberElement = document.createElement("div");
    locationNumberElement.setAttribute("style", "display: none;");
    locationNumberElement.setAttribute("name", "locationNumber");

    var lessorNameElement = document.createElement("div");
    lessorNameElement.classList.add("time-lessor");

    var dateTimeElement = document.createElement("div");
    dateTimeElement.classList.add("time-datetime");

    // div에 text 넣기
    lessorNameElement.appendChild(document.createTextNode(lessorName));
    dateTimeElement.appendChild(dateTimeNode);
    eventIdElement.appendChild(document.createTextNode(eventId));
    locationIdElement.appendChild(document.createTextNode(locationId));
    locationNumberElement.appendChild(document.createTextNode(locationNumber));

    // event div에 내용 추가하기
    timeEventElement.append(eventIdElement, locationIdElement, locationNumberElement, lessorNameElement, dateTimeElement);

    return timeEventElement;
}

function getCurrentDateTime() {
    var today = new Date();
    return zeroFilledDateTime(today);
}

function setCurrentDateTime() {
    $('#start-date').val(getCurrentDateTime());
}

function setCurrentTime() {
    var currentTime = new Date();
    var currentTimeText = leftZeroPad(convert12hours(currentTime.getHours())) + ':' + leftZeroPad(currentTime.getMinutes()) + ':' + leftZeroPad(currentTime.getSeconds());
    $('#current-time-view').text(currentTimeText);
    setTimeout(setCurrentTime, 500);
}

function convert12hours(hour) {
    if (hour % 12 == 0) {
        return hour;
    }
    if ((hour = hour % 12) > 0) {
        return hour;
    }
}

function setEndDateTime(minutes) {
    var startDateTime = new Date($('#start-date').val());
    var endDateTime = new Date();
    endDateTime.setTime(startDateTime.getTime() + (minutes * 60 * 1000));
    $('#end-date').val(zeroFilledDateTime(endDateTime));
    $('#end-date').effect("highlight", {}, 1500);
}

function zeroFilledDateTime(oldDate) {
    var newDate = oldDate.getFullYear() + '-' + (leftZeroPad(oldDate.getMonth() + 1)) + '-' + leftZeroPad(oldDate.getDate());
    var newTime = leftZeroPad(oldDate.getHours()) + ":" + leftZeroPad(oldDate.getMinutes());
    var newDateTime = newDate + 'T' + newTime;
    return newDateTime;
}

// 3 -> 03, 02 -> 02
function leftZeroPad(target) {
    if (target.toString().length < 2) {
        return '0' + target;
    } else {
        return target;
    }
}

// 2019-02-28 => 190228
function getDays() {
    var today = new Date();
    return today.getFullYear().toString().substring(2, 4) + leftZeroPad(today.getMonth() + 1) + leftZeroPad(today.getDate());
}

function loadUpdateDeleteForm(event) {
    event.stopPropagation();

    $.fancybox.open({
        src: '#update-delete-form',
        type: 'inline',
        opts: {
            beforeShow: function () {
                // console.log(event.currentTarget.children[4]);
                // console.log(event.currentTarget.querySelector('div[name="eventId"]').innerHTML);
                // console.log(event.currentTarget.querySelector('div[name="locationId"]'));
                // console.log(event.currentTarget.querySelector('div[name="locationNumber"]').innerHTML);
                // console.log(event.currentTarget.querySelector('.time-lessor').innerHTML);

                var eventId = event.currentTarget.querySelector('div[name="eventId"]').innerHTML;
                var filteredEvent = events.filter(function (event) {
                    console.log("e id : " + event.id + " / eventId : " + eventId);
                    if (event.id == eventId) {
                        return event;
                    }
                });

                // var lessorName = event.currentTarget.querySelector('.time-lessor').innerHTML;
                // var locationNumber = event.currentTarget.querySelector('div[name="locationNumber"]').innerHTML;
                // console.log("startdatetime : " + filteredEvent[0].startDateTime.substring(0, 16));
                $("#update-delete-form input[name='id']").val(eventId); // 1
                $("#update-delete-form input[name='lessorname']").val(filteredEvent[0].lessorName); // 황태원
                $("#update-delete-form select[name='roomno']").val(filteredEvent[0].location.roomNo); // 401
                $("#update-delete-form input[name='startdatetime']").val(filteredEvent[0].startDateTime.substring(0, 16)); // 2019-02-28T12:34:00 -> 2019-02-28T12:34
                $("#update-delete-form input[name='enddatetime']").val(filteredEvent[0].endDateTime.substring(0, 16));
            }
        }
    });
}

Date.prototype.toDatetimeLocal =
    function toDatetimeLocal() {
        var
            date = this,
            ten = function (i) {
                return (i < 10 ? '0' : '') + i;
            },
            YYYY = date.getFullYear(),
            MM = ten(date.getMonth() + 1),
            DD = ten(date.getDate()),
            HH = ten(date.getHours()),
            II = ten(date.getMinutes()),
            SS = ten(date.getSeconds())
        ;
        return YYYY + '-' + MM + '-' + DD + 'T' +
            HH + ':' + II + ':' + SS;
    };

Date.prototype.fromDatetimeLocal = (function (BST) {
    // BST should not be present as UTC time
    return new Date(BST).toISOString().slice(0, 16) === BST ?
        // if it is, it needs to be removed
        function () {
            return new Date(
                this.getTime() +
                (this.getTimezoneOffset() * 60000)
            ).toISOString();
        } :
        // otherwise can just be equivalent of toISOString
        Date.prototype.toISOString;
}('2006-06-06T06:06'));

// // 페이지 로딩시 동작
// $(
//
// );

