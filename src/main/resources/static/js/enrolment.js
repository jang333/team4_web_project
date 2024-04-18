console.log("Enrolment 실행");
let findInfo = {
    classno : 0,
    mno : 0
};

let pageObject = {
    page : 1,   //현재페이지
    pageSize : 5  //한페이지에 게시물수
};


getAllClassInfoStudent(1);
function getAllClassInfoStudent(page){
    pageObject.page = page;
    console.log('getAllClassInfo');
    let classAllInfo = document.querySelector('.classAllInfo');
    let html = '';
    $.ajax({
        url: "/up/getallclassinfo",
        method : "get",
        data : pageObject,
        async : false,
        success: (r)=>{
            console.log(r);
            for(let i = 0; i<r.list.length; i++){
                html +=`<tr class="text-center"><th scope="row" class="text-center">${r.list[i].no}</th>
                    <td>${r.list[i].classname}</td>
                    <td>${r.list[i].professorname}</td>
                    <td>${r.list[i].roomnumber}</td>
                    <td>${r.list[i].dayweek} / ${r.list[i].starttime}~${r.list[i].endtime}</td>
                    <td>${r.list[i].semester}</td>
                    <td class="text-center" id="editBtn">
                        <button type="button" class="btn btn-success" onclick="addClassStudent(${r.list[i].no})">신청</button>
                    </td>
                </tr>`
            }
            classAllInfo.innerHTML = html;
            pagination(r);
        }
    })
}

//페이지네이션 함수
function pagination(r){
    console.log(r)
    let pagination = document.querySelector('.pagination');
    let html = ``;
    html += `<li class="page-item">
                 <a class="page-link" onclick="getAllClassInfoStudent(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
            for(let i = r.startbtn ; i <= r.endbtn ; i++){
                html += `<li class="page-item"${i == r.page ? 'active' : '' }>
                <a class="page-link" onclick="getAllClassInfoStudent(${i})">${i}</a></li>`
            }

            html +=`<li class="page-item" >
                            <a class="page-link" onclick="getAllClassInfoStudent(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`

    pagination.innerHTML = html;
}



searchMno()
function searchMno(){
    $.ajax({
            type: "get",
            url: "/searchMno",
            success: (r)=> {
                console.log(r)
                findInfo.mno = `${r.mno}`;
                console.log(findInfo.mno);
            }
        });
}

//수강신청 등록
function addClassStudent(no){
findInfo.classno = no;
console.log(no);
console.log(findInfo);
    $.ajax({
        type: "post",
        url: "/regist",
        data: findInfo,
        success: (r)=> {
            console.log(r)
            if(r==1){
                alert("수강신청 완료");

            }else{
                alert("수강신청 실패")
            }
        }
    });    
}