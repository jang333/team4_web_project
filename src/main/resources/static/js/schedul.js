console.log("schedul.js 실행")
let pageObject={
    page : 1,
    pageSize : 5
}
console.log(pageObject)
schedulList(1);

function schedulPut(){

    console.log("스케줄등록")
    let schedulForm = document.querySelector('.schedulForm');
    let schedulFormData = new FormData(schedulForm);
    console.log(schedulFormData)

    $.ajax({
        type: "post",
        url: "/up/sch/write.do",
        data: schedulFormData,
        contentType : false,
        processData : false,
        success: (r) => {
            console.log(r)
            if(r){
                alert('등록 완료');
                location.href = "/up/sch/cal.do"
            }else{
                alert('등록 실패');
            }
        }
    });
    
}
//출력
function schedulList(page){
    pageObject.page = page;
    $.ajax({
        type: "get",
        url: "/up/sch/info",
        data : pageObject,
        success: (r) => {
            console.log(r)
            let schedulInfo = document.querySelector('.schedulInfo');
            let html = '';
            for(let i = 0 ; i < r.list.length ; i++){
                html += `<tr class = "schedulList${r.list[i].scno} text-center">
                                <th scope="col">${r.list[i].scno}</th>
                                <td class="text-center">
                                    <span style="background-color:${r.list[i].scolor}; color: rgb(255, 255, 255, 0);">
                                        ${r.list[i].scolor}
                                    </span>
                                </td>
                                <td>${r.list[i].sccontent}</td>
                                <td>${r.list[i].stdate}</td>
                                <td><button onclick="schedulUpdateInput(${r.list[i].scno})" class="btn btn-outline-secondary" type="button">수정</button></td>
                                <td><button onclick="schedulDelete(${r.list[i].scno})" class="btn btn-outline-danger" type="button">삭제</button></td>
                            </tr>`
            }
            schedulInfo.innerHTML = html;
            pagination(r);
        }
    });
}

//페이지네이션 함수
function pagination(r){
    console.log(r)
    let pagination = document.querySelector('.pagination');
    let html = ``;
    html += `<li class="page-item">
                 <a class="page-link" onclick="schedulList(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                     <span aria-hidden="true">&laquo;</span>
                 </a>
                 </li>`
            for(let i = r.startbtn ; i <= r.endbtn ; i++){
                html += `<li class="page-item"${i == r.page ? 'active' : '' }>
                <a class="page-link" onclick="schedulList(${i})">${i}</a></li>`
            }

            html +=`<li class="page-item" >
                            <a class="page-link" onclick="schedulList(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`

    pagination.innerHTML = html;
}



//수정 인풋박스
function schedulUpdateInput(scno){
    console.log(scno)
    $.ajax({
        type: "get",
        url: "/up/sch/updateInfo",
        data: {'scno':scno},
        success: (r) => {
            console.log(r)
            let schedulInfo = document.querySelector('.schedulList'+scno);
            let html = '';
                html += `<form class = "schedulUpdateInput">
                            <th scope="col">${scno}</th>
                            <td><input  type="color" class="scolor${r[0].scno} form-control" value="${r[0].scolor}"></td>
                            <td><input type="text" class="sccontent${r[0].scno} form-control w-75" value="${r[0].sccontent}"></td>
                            <td><input type="date" class="stdate${r[0].scno} form-control w-75" value="${r[0].stdate}"></td>
                            <td>
                                <button onclick="schedulUpdate(${r[0].scno})" type="button" class="btn btn-secondary">
                                    Edit
                                </button>
                            </td>
                            <td>
                                <button onclick="schedulUpdate(${r[0].scno})" type="button" class="btn btn-danger">
                                    Delete
                                </button>
                        </td>
                        </form>`
            schedulInfo.innerHTML = html;
        }
    });
}

//수정
function schedulUpdate(scno){
    console.log(scno)
    let scolor = document.querySelector('.scolor'+scno).value;
    let sccontent = document.querySelector('.sccontent'+scno).value;
    let stdate = document.querySelector('.stdate'+scno).value;

    $.ajax({
        type: "put",
        url: "/up/sch/update",
        data:  {'scno':scno, 'scolor':scolor, 'sccontent':sccontent, 'stdate': stdate},
        success: (r) => {
            console.log(r);
            if(r){
                alert('수정 성공');
                location.href = "/up/sch/cal.do"
            }else{
                alert('수정 실패');
            }
        }
    });
}

//삭제
function schedulDelete(scno){
    console.log(scno)

    $.ajax({
        type: "delete",
        url: "/up/sch/delete",
        data: {'scno':scno},
        success: (r) => {
            console.log(r);
            if(r){
                alert('삭제 성공');
                location.href = "/up/sch/cal.do"
            }else{
                alert('삭제 실패');
            }
        }
    });
}