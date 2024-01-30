window.onload = () => {
    const performanceId = document.getElementById("performanceId").value;
    const performanceInfo = document.getElementById("performanceInfo");
    const reservableSeatList = document.getElementById("reservableSeatList");

    const url = "/query/" + performanceId;
    fetch(url).then((response) => response.json()).then((data) => {
        const responseMessage = data;
        const statusCode = responseMessage["statusCode"];
        if (statusCode === "OK") {
            const responseData = responseMessage["data"];
            let performanceName = responseData["performanceName"];
            let round = responseData["performanceRound"];
            let startDate = responseData["startDate"];
            let isReserve = responseData["isReserve"];
            let seatInfo = responseData["seatInfo"];

            let newRow = document.createElement("div");
            newRow.setAttribute("class", "row");
            let newPerformanceName = document.createElement("div");
            newPerformanceName.setAttribute("class", "col");
            let newRound = document.createElement("div");
            newRound.setAttribute("class", "col");
            let newStartDate = document.createElement("div");
            newStartDate.setAttribute("class", "col");
            let newIsReserve = document.createElement("div");
            newIsReserve.setAttribute("class", "col");

            performanceInfo.appendChild(newRow);
            newRow.appendChild(newPerformanceName);
            newPerformanceName.innerText = performanceName;
            newRow.appendChild(newRound);
            newRound.innerText = round;
            newRow.appendChild(newStartDate);
            newStartDate.innerText = startDate;
            newRow.appendChild(newIsReserve);
            if (isReserve === "enable") {
                newIsReserve.innerText = "예약 가능";
            }
            if (isReserve === "disable") {
                newIsReserve.innerText = "예약 불가능";
            }

            for (var i = 0; i < seatInfo.length; i++) {
                let round = seatInfo[i]["round"];
                let gate = seatInfo[i].gate;
                let line = seatInfo[i]["line"];
                let seat = seatInfo[i]["seat"];

                console.log(round);
                let newListRow = document.createElement("div");
                newListRow.setAttribute("class", "row");
                let newRound = document.createElement("div");
                newRound.setAttribute("class", "col");
                let newGate = document.createElement("div");
                newGate.setAttribute("class", "col");
                let newSeat = document.createElement("div");
                newSeat.setAttribute("class", "col");


                reservableSeatList.appendChild(newListRow);
                newListRow.appendChild(newRound);
                newRound.innerText = round + "회차";
                newListRow.appendChild(newGate);
                newGate.innerText = gate + "번 게이트";
                newListRow.appendChild(newSeat);
                newSeat.innerText = line + seat + "번 좌석";
            }





        }
    })
}
//{"statusCode":"OK","message":"Success","data":{"performanceId":"23066020-be9c-11ee-bc5b-0242ac140002","performanceName":"영웅","performanceRound":1,"startDate":"2024-01-27T19:30:00","isReserve":"enable"}}