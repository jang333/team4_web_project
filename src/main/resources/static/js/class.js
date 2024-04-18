let timePageObject = {
    page : 1,   //현재페이지
    pageSize : 5  //한페이지에 게시물수
};
getClassInfo(1);
let checkArray = [ false, true ]
// ========================= 강의 등록 [ 강의명, 강의타입 ] ex) 강의명:수묵화의 기초, 강의타입:교양선택 ========================= //
function onClassRegi(){
    for(let i=0; i<checkArray.length; i++){
        if(!checkArray[i]){
            alert("입력사항을 모두 정확히 입력해주세요.");
            return;
        }
    }
    let classname = document.querySelector('.classname').value;
    let classtype = document.querySelector('.classtype').value;
    $.ajax({
        url: "/up/classregi",
        method: "post",
        async:false,
        data: {'classname':classname, 'classtype':classtype},
        success: (r)=>{
            getClassInfo(1);
        }
    });
}
// ========================= 강의 등록 유효성 검사 ========================= //
    /*
        강의명을 입력하지 않았을 경우 등록 X
        한글 자음, 모음만 입력할 경우 등록 X
        숫자 포함될 경우 등록 X
        강의명이 중복 될 경우 등록 X
    */
function checkClassName(){
    let classname = document.querySelector('.classname').value;
    let classnamej = /^[가-힣a-zA-Z\s]{2,20}$/
    if(classnamej.test(classname)){
        $.ajax({
            url: "/up/checkclassname",
            data: {'classname':classname},
            method:'get',
            async:false,
            success: (r)=>{
                if(r){
                    alert("해당 강의는 등록된 강의입니다.");
                    checkArray[0] = false;
                }else{
                    if(confirm("해당 강의를 등록하시겠습니까?")){
                        checkArray[0] = true;
                        onClassRegi();
                        alert(`${classname}가 등록 되었습니다.`);
                    }else{
                        alert(`${classname} 등록을 취소합니다.`);
                        checkArray[0] = false;
                    }
                }
            } // success end
        }); // ajax end
    }else{
        alert("똑바로 입력해라.");
        checkArray[0] = false;
    }   // 정규표현식 end
}

// ========================= 강의 출력 ========================= //

function getClassInfo(page){
    let calssInfo = document.querySelector('.classInfo');
    let html ='';

    timePageObject.page = page; //매개변수페이지를 현재페이지에 대입

    $.ajax({
        url: "/up/classinfo",
        method: "get",
        data : timePageObject,
        async:false,
        success: (r)=>{
            for(let i=0; i<r.list.length; i++){
                html +=`<tr class="classUpdate${r.list[i].classno} text-center">
                <th scope="row" class="text-center">${r.list[i].classno}</th>
                <td colspan="5">${r.list[i].classname}</td>
                <td colspan="4">${r.list[i].classtype}</td>
                <td class="text-center" id="editBtn">
                    <button type="button" class="btn btn-outline-secondary btn-sm" onclick="getPutClass('${r.list[i].classname}',${r.list[i].classno})">Edit</button>
                </td>
                </tr>`
            }
            calssInfo.innerHTML = html;
            doPaging(r);
        }
    });
}
// ========================= 강의 수정 했을때 출력========================= //
function getPutClass(classname,classno){
    let classUpdate = document.querySelector('.classUpdate'+classno);
    let html = `<th scope="row" class="text-center">${classno}</th>
        <td colspan="5"><input class="classname${classno} form-control w-75" type="text" value="${classname}"/></td>
        <td colspan="4"><select class="classtype${classno} form-select w-75">
        <option selected>선택</option>
        <option value="전공필수">전공필수</option>
        <option value="전공선택">전공선택</option>
        <option value="교양필수">교양필수</option>
        <option value="교양선택">교양선택</option>
        </select></td>
        <td class="text-center"><button onclick="doPutClass(${classno})" type="button" class="btn btn-secondary btn-sm">Edit</button></td>`;
    classUpdate.innerHTML=html;
}

// ========================= 강의 수정 ========================= //
function doPutClass(classno){
    let classname = document.querySelector(`.classname${classno}`).value;
    let classtype = document.querySelector(`.classtype${classno}`).value;
    if(classtype=='선택'){
        alert('타입을 선택해주세요');
        return;
    }
    $.ajax({
        url: "/up/classupdate",
        method: "put",
        async : false,
        data: {'classno':classno, 'classname':classname, 'classtype':classtype},
        success: (r)=> {
            if(r=1){
                getClassInfo(1)
            }
        }
    });
}

function doPaging(r){
    console.log(timePageObject);
    let object = timePageObject;
    console.log(object)
    //어디에
        let pagination = document.querySelector(".pagination");
        //무엇을
        let pagehtml = "";
        pagehtml += `<li class="page-item">
                     <a class="page-link" onclick="getClassInfo(${object.page-1 < 1 ? 1 : object.page-1})" aria-label="Previous">
                         <span aria-hidden="true">&laquo;</span>
                     </a>
                     </li>`
        for(let i = r.startbtn ; i <= r.endbtn ; i++){
            pagehtml += `<li class="page-item"${i == object.page ? 'active' : '' }>
            <a class="page-link" onclick="getClassInfo(${i})">${i}</a></li>`
        }

        pagehtml +=`<li class="page-item" >
                        <a class="page-link" onclick="getClassInfo(${object.page+1>r.totalpage? r.totalpage : object.page+1})" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>`
        //출력
        pagination.innerHTML = pagehtml;

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