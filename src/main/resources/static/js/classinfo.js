let session = {
    state : 0
}

let pageObject = {
    page : 1,   //현재페이지
    pageSize : 5  //한페이지에 게시물수
};

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
                session.state = logindto.state;
            }
        });
    }
})

getOption();
getAllClassInfo(1);

// ========================= 수강 출력 ========================= //
function getAllClassInfo(page){
    console.log('getAllClassInfo');
    let classAllInfo = document.querySelector('.classAllInfo');
    pageObject.page = page;
    let html = '';
    $.ajax({
        url: "/up/getallclassinfo",
        method : "get",
        async : false,
        data: pageObject,
        success: (r)=>{
            console.log(r);
            for(let i = 0; i<r.list.length; i++){
                html +=`<tr class="text-center"><th scope="row" class="text-center">${r.list[i].no}</th>
                    <td>${r.list[i].classname}</td>
                    <td>${r.list[i].professorname}</td>
                    <td>${r.list[i].roomnumber}</td>
                    <td>${r.list[i].dayweek}${r.list[i].starttime}~${r.list[i].endtime}</td>
                    <td>${r.list[i].semester}</td>
                    <td id="state" class="instate${r.list[i].no} text-center">

                    </td>
                    <td class="text-center edutBtn${r.list[i].no}" id="editBtn">
                        <button type="button" onclick="inSelect(${r.list[i].no})" class="btn btn-outline-secondary btn-sm">
                            Edit
                        </button>
                    </td>
                </tr>`
            }
            classAllInfo.innerHTML = html;
            pagination(r);
            if(session.state == 3 || session.state == 4){
                for(let j=0; j<r.list.length; j++){
                    if(r.list[j].cstate == 0){
                        document.querySelector('.instate'+r.list[j].no).innerText = '대기'
                    }else if(r.list[j].cstate == 1){
                        document.querySelector('.instate'+r.list[j].no).innerText = '반려'
                    }else if(r.list[j].cstate == 2){
                        document.querySelector('.instate'+r.list[j].no).innerText = '승인'
                    }
                }
            }

            let editBtn = document.querySelectorAll('#editBtn');
            let state = document.querySelectorAll('#state');
            if(session.state == 1){
                for(let i=0; i<editBtn.length; i++){
                    editBtn[i].innerHTML ='';
                    state[i].innerHTML = '';
                }
            }else if(session.state == 2){
                for(let i=0; i<editBtn.length; i++){
                    editBtn[i].innerHTML ='';
                    state[i].innerHTML = '';
                }
            }else if(session.state == 3){
                for(let i=0; i<editBtn.length; i++){
                    editBtn[i].innerHTML ='';
                }
            }
        }
    });
}

//페이지네이션 함수
function pagination(r){
    console.log(r)
    let pagination = document.querySelector('.pagination');
    let html = ``;
    html += `<li class="page-item">
                 <a class="page-link" onclick="getAllClassInfo(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
            for(let i = r.startbtn ; i <= r.endbtn ; i++){
                html += `<li class="page-item"${i == r.page ? 'active' : '' }>
                <a class="page-link" onclick="getAllClassInfo(${i})">${i}</a></li>`
            }

            html +=`<li class="page-item" >
                            <a class="page-link" onclick="getAllClassInfo(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`

    pagination.innerHTML = html;
}


function inSelect(no){
    let inSelect = document.querySelector('.instate'+no);
    let editBtn = document.querySelector('.edutBtn'+no);
    inSelect.innerHTML =`<select class="state${no} form-select w-75 me-2">
                            <option selected>선택</option>
                            <option value="0">대기</option>
                            <option value="1">반려</option>
                            <option value="2">승인</option>
                        </select>`
    editBtn.innerHTML =`
        <td class="text-center" id="editBtn">
            <button type="button" onclick="changeClass(${no})" class="btn btn-secondary btn-sm">
                Edit
            </button>
        </td>
    `
}

function createAllInfo(){
    let getOptionClass = document.querySelector(`.getOptionClass`).value;
    let getOptionProfessor = document.querySelector('.getOptionProfessor').value;
    let getOptionRoomNumber = document.querySelector('.getOptionRoomNumber').value;
    let getOptionClassTime = document.querySelector('.getOptionClassTime').value;
    let getOptionSeason = document.querySelector('.getOptionSeason').value;

    let createAllInfo = {
        'classno' : getOptionClass,
        'professor' : getOptionProfessor,
        'roomnumber' : getOptionRoomNumber,
        'tno' : getOptionClassTime,
        'sno' : getOptionSeason,
    }
    
    $.ajax({
        url: "/up/inclassandtime",
        method : "put",
        async:false,
        data : {'classno' : getOptionClass, 'tno' : getOptionClassTime},
        success: (r)=>{
            console.log(r);
        }
    });

    $.ajax({
        url : "/up/createallinfo",
        method : "post",
        async:false,
        data :createAllInfo,
        success : (r)=>{
            console.log(r);
            alert('등록 성공');
            location.href = '/up/cif';
            getOption();
        }
    })
}

// ========================= 수강 등록할 때 옵션으로 출력 ========================= //
function getOption(){
    let html='';
    // ======= getOptionClass
    let getOptionClass = document.querySelector(`.getOptionClass`);
    $.ajax({
        url: "/up/gcl",
        method : "get",
        async:false,
        success: (r)=>{
            console.log(r);
            for(let i=0; i<r.length; i++){
                html+=`<option value="${r[i].classno}">${r[i].classname}</option>`
            }
            getOptionClass.innerHTML = html;
            html='';
        }
    });

    // ======= getOptionProfessor
    let getOptionProfessor = document.querySelector('.getOptionProfessor');
    $.ajax({
        url: "/up/galp",
        method : "get",
        async:false,
        success: (r)=>{
            console.log(r);
            for(let i=0; i<r.length; i++){
                html+=`<option value="${r[i].pno}">${r[i].name}</option>`
            }
            getOptionProfessor.innerHTML = html;
            html='';
        }
    });

    // ======= getOptionRoomNumber
    let getOptionRoomNumber = document.querySelector('.getOptionRoomNumber');
    $.ajax({
        url: "/up/rif",
        method : "get",
        async:false,
        success: (r)=>{
            console.log(r);
            for(let i=0; i<r.length; i++){
                html+=`<option value="${r[i].rno}">${r[i].roomnumber}</option>`
            }
            getOptionRoomNumber.innerHTML = html;
            html='';
        }
    });

    // ======= getOptionClassTime
    let getOptionClassTime = document.querySelector('.getOptionClassTime');
    $.ajax({
        url: "/up/classtimeisnull",
        method : "get",
        async:false,
        success: (r)=>{
            console.log(r);
            for(let i=0; i<r.length; i++){
                html+=`<option value="${r[i].tno}">${r[i].dayweek}${r[i].starttime}~${r[i].endtime}</option>`
            }
            getOptionClassTime.innerHTML = html;
            html = '';
        }
    });

    // ======= getOptionSeason
    let getOptionSeason = document.querySelector('.getOptionSeason');
    $.ajax({
        url: "/up/getseason",
        method : "get",
        async:false,
        success: (r)=>{
            console.log(r);
            for(let i=0; i<r.length; i++){
                html+=`<option value="${r[i].sno}">${r[i].semester}</option>`
            }
            getOptionSeason.innerHTML = html;
        }
    });
}

function changeClass(cno){
    let cstate = document.querySelector(`.state${cno}`).value;
    $.ajax({
        url : "/changestateclass",
        method : "put",
        data : {"cstate":cstate, 'cno':cno},
        success : function (r){
            console.log(r);
            getAllClassInfo(1);
        }
    })
}