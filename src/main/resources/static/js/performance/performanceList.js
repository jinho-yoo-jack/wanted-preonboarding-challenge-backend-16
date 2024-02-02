window.onload = () => {

    const performanceList = document.getElementById("performanceList");
    const reservable = document.getElementById("reserve");
    const disable = document.getElementById("disable");
    const reservableUrl = "/query/reservable/performance"
    const disableUrl = "/query/disable/performance"

    reservable.addEventListener("click", () => {
        performanceList.replaceChildren();
        createList(reservableUrl);
    });
    disable.addEventListener("click", () => {
        performanceList.replaceChildren();
        createList(disableUrl);
    })

    createList(reservableUrl);
};

function createList(url) {

    fetch(url).then((response) => response.json())
        .then((data) => {
            console.log(data);
            // let listData = JSON.parse(data);
            let listData = data["data"];
            for (var i = 0; i < listData.length; i++) {
                let performanceId = listData[i].performanceId;
                let performanceName = listData[i].performanceName;
                let performanceType = listData[i].performanceType;
                let performanceRound = listData[i].performanceRound;
                let startDate = listData[i].startDate;
                let isReserve = listData[i].isReserve;

                let newRow = document.createElement("div");
                newRow.setAttribute("class", "row");
                let newPerformance = document.createElement("div");
                newPerformance.setAttribute("id", performanceId);
                newPerformance.setAttribute("class", "row");
                newPerformance.setAttribute("name", "performance");
                let newName = document.createElement("div");
                newName.setAttribute("name", "performanceName");
                newName.setAttribute("class", "col");
                let newType = document.createElement("div");
                newType.setAttribute("name", "performanceType");
                newType.setAttribute("class", "col");
                let newRound = document.createElement("div");
                newRound.setAttribute("name", "performanceRound");
                newRound.setAttribute("class", "col");
                let newStartDate = document.createElement("div");
                newStartDate.setAttribute("name", "startDate");
                newStartDate.setAttribute("class", "col");
                let newIsReserve = document.createElement("div");
                newIsReserve.setAttribute("name", "isReserve");
                newIsReserve.setAttribute("class", "col");
                let seatInfo = document.createElement("input")
                seatInfo.setAttribute("type", "button")
                seatInfo.setAttribute("class", "col btn");
                seatInfo.setAttribute("name", "seatInfo");
                seatInfo.setAttribute("value", "좌석 정보 확인하기");

                performanceList.appendChild(newRow);
                newRow.appendChild(newPerformance);
                newPerformance.appendChild(newName);
                newName.innerText = performanceName;
                newPerformance.appendChild(newType);
                if (performanceType === "NONE") {
                    newType.innerText = "일반";
                }
                if (performanceType === "CONCERT") {
                    newType.innerText = "콘서트";
                }
                if (performanceType === "EXHIBITION") {
                    newType.innerText = "전시회";
                }
                newPerformance.appendChild(newRound);
                newRound.innerText = performanceRound + "회차";
                newPerformance.appendChild(newStartDate);
                newStartDate.innerText = startDate;
                newPerformance.appendChild(newIsReserve);

                if (isReserve === "enable") {
                    newIsReserve.innerText = "예약 가능";
                    newPerformance.appendChild(seatInfo);
                    seatInfo.addEventListener("click", function (event) {
                        location.href = "/performance/" + performanceId;
                    })
                }
                if (isReserve === "disable") {
                    newIsReserve.innerText = "예약 불가능";

                    let reserveAlarm = document.createElement("input")
                    reserveAlarm.setAttribute("type", "button")
                    reserveAlarm.setAttribute("class", "col btn");
                    reserveAlarm.setAttribute("name", "reserveAlarm");
                    reserveAlarm.setAttribute("value", "취소표 알람 설정하기");
                    newPerformance.appendChild(reserveAlarm);
                }
            }
        })
}
