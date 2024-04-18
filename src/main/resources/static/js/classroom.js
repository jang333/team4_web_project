let pageObject = {
    page : 1,
    pageSize : 5
}
console.log(pageObject);
roomInfo(1)
// ========================= 강의실 유효성검사 시작 ==============================//
// 강의실 등록 필드값 유효성 체크
let roomcheckArray = [false, false] // 강의실호수, 강의실인원

// 강의실 중복검사, 유효성검사(숫자 3자리만 가능)
function rnumcheck(){
    let roomnumber = document.querySelector('.roomnumber').value;

    // 정규표현식 : 숫자 3자리만 가능
    let rnumregex = /^\d{3}$/;

    // roomnumber 중복체크 (ajax)
    if(rnumregex.test(roomnumber)){
        $.ajax({
            url: "/up/roomnumcheck", // 보낼 맵핑
            async : false,
             method: "get", // 메소드 방식
             data: {'roomnumber':roomnumber}, // 보낼 데이터
             success: (r)=>{ // 데이터 보내는게 성공했을 시
                if(r){
                    document.querySelector('.roomCheckBox').innerHTML = `<p class="text-danger mt-2 mb-1">사용중인 강의실 호수입니다.<p>`;
                    roomcheckArray[0] = false;
                }else {
                    document.querySelector('.roomCheckBox').innerHTML = `<p class="text-success mt-2 mb-1">통과</p>`;
                     roomcheckArray[0] = true;
                }
             } // success end
        }) // ajax end
    }else{
        // 정규표현식 유효성검사(숫자 3자만가능) 결과 출력
        document.querySelector('.roomCheckBox').innerHTML = `<p class="text-danger mt-2 mb-1">숫자 3자리만 입력 가능합니다.</p>`;
        roomcheckArray[0] = false;
    } // if end
} // roomcheck end

// 강의실 인원 유효성검사 (숫자만 가능)
function totalcheck(){
    let totalperson = document.querySelector('.totalperson').value;

    // 정규표현식 : 숫자만 가능
    let totalregex = /^[0-9]+$/;

    if(totalregex.test(totalperson)){
        document.querySelector('.totalCheckBox').innerHTML = `<p class="text-success mt-2 mb-1">통과</p>`;
        roomcheckArray[1] = true;
    }else {
        document.querySelector('.totalCheckBox').innerHTML = `<p class="text-danger mt-2 mb-1">숫자만 입력 가능합니다.</p>`;
        roomcheckArray[1] = false;
    }
} // totalcheck end
// ========================= 강의실 등록 ==============================//

// 강의실 등록
function roomSignUp(){

    // 강의실 호수, 인원 필드값 하나라도 false시 등록 실패
    for(let i = 0; i < roomcheckArray.length; i++){
        if(roomcheckArray[i]==false){
            alert('입력값을 정확하게 입력해주세요')
            return;
        }
    } // for end

    // 필드값 가져오기
    let roomnumber = document.querySelector('.roomnumber').value;
    let totalperson = document.querySelector('.totalperson').value;

    $.ajax({
        url: "/up/roomsignup",
         method: "post",
         async : false,
         data: {'roomnumber':roomnumber, 'totalperson':totalperson},
         success: (r)=>{
             if(r){
                 alert('강의실 등록 완료');
                 location.href = "/up/ro"
             }else{
                 alert('강의실 등록 실패');
                 location.href = "/up/ro"
             }
         }
    }) // ajax end
} // roomSignUp end

// ========================= 강의실 출력 ==============================//
function roomInfo(page) {
    pageObject.page= page;
    let roomInfo = document.querySelector(".roomInfo > table > tbody");
    let html = "";
    $.ajax({
        url : "/up/roominfo",
        data : pageObject,
        async : false,
        method : "get",
        success : (r)=> {
            console.log(r);
            for(let i = 0; i < r.list.length ; i++){
                 html += `<tr class="inputRoom${r.list[i].rno} text-center">
                            <th scope="row" class="text-center">${r.list[i].rno}</th>
                            <td colspan="5">${r.list[i].roomnumber}</td>
                            <td colspan="4">${r.list[i].totalperson}</td>
                            <td class="text-center" id="editBtn">
                                <button type="button" class="btn btn-outline-secondary btn-sm" onclick="inputRoom(${r.list[i].rno},${r.list[i].roomnumber},${r.list[i].totalperson})">Edit</button>
                            </td>
                        </tr>`
            }
        roomInfo.innerHTML = html;
        pagination(r);
        }
    })
}

// ========================= 페이지네이션 ==============================//
function pagination(r){
    let pagination = document.querySelector('.pagination');
    let html = '';
    html += `<li class="page-item">
                 <a class="page-link" onclick="roomInfo(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
    for(let i = r.startbtn ; i <= r.endbtn ; i++){
        html += `<li class="page-item"${i == r.page ? 'active' : '' }>
        <a class="page-link" onclick="roomInfo(${i})">${i}</a></li>`
    }

    html +=`<li class="page-item" >
                    <a class="page-link" onclick="roomInfo(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>`

    pagination.innerHTML = html;

}


// ========================= 강의실 수정 ==============================//
function roomUpdate(rno){
    console.log("roomUpdate()");
     let roomnumber = document.querySelector('.reRoomnumber').value;
     let totalperson = document.querySelector('.reTotalperson').value;

     $.ajax({
        url : "/up/roomupdate",
        method : "put",
        async : false,
        data : {'rno':rno, 'roomnumber':roomnumber, 'totalperson':totalperson},
        success : (r) => {
            if(r){
                alert('수정 완료')
                location.href = "/up/ro"
            }else {
                alert('수정 실패')
                location.href = "/up/ro"
            }
        }
     })
}

// ========================= 강의실 수정 인풋 ==============================//
function inputRoom(rno, roomnumber, totalperson){
    let roomUpdate = document.querySelector('.inputRoom'+rno);
    let html = `<th scope="row" class="text-center">${rno}</th>
                    <td colspan="5">
                        <input value="${roomnumber}" type="text" class="reRoomnumber form-control w-50">
                    </td>
                    <td colspan="4">
                        <input value="${totalperson}" type="text" class="reTotalperson form-control w-50">
                    </td>
                    <td class="text-center">
                        <button type="button" onclick="roomUpdate(${rno})" class="btn btn-secondary btn-sm">Edit</button>
                    </td>`;
    roomUpdate.innerHTML=html;
}

$.ajax({
    method: "get",
    async : false,
    url: "/member/login/check",
    success: function (id) {
        $.ajax({
            url : '/member/login/info',
            method : 'get',
            data : {'id' : id},
            async : false,
            success : (logindto) => {
                if(logindto.state == 1){
                    let editBtn = document.querySelectorAll('#editBtn')
                    for(let i=0; i<editBtn.length; i++){
                        editBtn[i].innerHTML ='';
                    }
                }else if(logindto.state == 2){
                    let editBtn = document.querySelectorAll('#editBtn')
                    for(let i=0; i<editBtn.length; i++){
                        editBtn[i].innerHTML ='';
                    }
                }
            }
        });
    }
})