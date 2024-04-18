let pageObject = {
    page : 1,
    pageSize : 5
}
console.log(pageObject);

viewMember()
getAllProfessor(1)
// ========================= 전체회원 출력 [ 교수 등록하기 위함 ] ========================= //
function viewMember(){
    let viewMember = document.querySelector(`.viewMember`);
    let html = '';
    $.ajax({
        url: "/up/viewmember",
        method : "get",
        async:false,
        success: (r)=>{
            console.log(r);
            for(let i=0; i<r.length; i++){
                html+=`<option value="${r[i].mno}">${r[i].name}</option>`
            }
            viewMember.innerHTML = html;
        }
    });
}

// ========================= 전체 회원 중에서 교수로 등록하기 ========================= //
function postProfessor(){
    console.log('postProfessor');
    let createPutProfessor = $(".createPutProfessor").serialize();
    $.ajax({
        url: "/up/createprofessor",
        method : "post",
        async : false,
        data : createPutProfessor,
        success: (r)=>{
            viewMember()
            location.href="/up/pf"
        }
    });
}

// ========================= 교수 수정 ========================= //
function putProfessor(pno){
    console.log('putProfessor');
    let professorTd = document.querySelectorAll('.professorUpdate'+pno+' th');
    console.log(professorTd[0].innerText);
    let professorInput = document.querySelectorAll('.professorUpdate'+pno+' td input');
    let str = [];
    for(let i=0; i<professorInput.length; i++){
        console.log(professorInput[i].value);
        str[i] = professorInput[i].value;
    }
    let professorObject = {
        pno : professorTd[0].innerText,
        mno : str[0],
        pgrade : str[1],
        psalary : str[2],
        proom : str[3],
        degree : str[4],
        majorpart : str[5],
        mainmajor : str[6]
    }
    console.log(professorObject);
    $.ajax({
        url: "/up/putprofessor",
        method : "put",
        async : false,
        data : professorObject,
        success: (r)=>{
            viewMember()
            location.href="/up/pf"
        }
    });
}

// ========================= 교수 ALL 출력 ========================= //
function getAllProfessor(page){
    console.log(page);
    pageObject.page = page;
    console.log('getAllProfessor');
    let getAllProfessor = document.querySelector('.getAllProfessor');
    let html = '';
    console.log(getAllProfessor);
    $.ajax({
        url: "/up/getallprofessor",
        method : "get",
        data : pageObject,
        async : false,
        success: (r)=>{
            console.log(r);
            for(let i = 0; i<r.list.length; i++){
                html += `<tr class="professorUpdate${r.list[i].pno} text-center">
                <th scope="row" class="text-center">${r.list[i].pno}</th>
                <td value="${r.list[i].mno}">${r.list[i].name}</td>
                <td>${r.list[i].pgrade}</td>
                <td>${r.list[i].psalary}</td>
                <td>${r.list[i].proom}</td>
                <td>${r.list[i].degree}</td>
                <td>${r.list[i].majorpart}</td>
                <td>${r.list[i].mainmajor}</td>
                <td class="text-center">
                    <button type="button" class="btn btn-outline-secondary btn-sm" onclick="getPutProfessor(${r.list[i].pno}, ${r.list[i].mno}, '${r.list[i].name}')">
                        Edit
                    </button>
                </td>
                </tr>`
            }
            getAllProfessor.innerHTML = html;
            pagination(r);
        }
    });
}
// ========================= 페이지네이션 함수========================= //
function pagination(r){
    console.log(r);
    let pagination = document.querySelector('.pagination');
    let html = '';
    html += `<li class="page-item">
             <a class="page-link" onclick="getAllProfessor(${r.page-1 < 1 ? 1 : r.page-1})" aria-label="Previous">
                 <span aria-hidden="true">&laquo;</span>
             </a>
             </li>`
    for(let i = r.startbtn ; i <= r.endbtn ; i++){
        html += `<li class="page-item"${i == r.page ? 'active' : '' }>
        <a class="page-link" onclick="getAllProfessor(${i})">${i}</a></li>`
    }

    html +=`<li class="page-item" >
                <a class="page-link" onclick="getAllProfessor(${r.page+1>r.totalpage? r.totalpage : r.page+1})" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>`
    pagination.innerHTML = html;
}

// ========================= 교수 수정 했을때 출력========================= //
function getPutProfessor(pno, mno, name){
    let professorUpdate = document.querySelector('.professorUpdate'+pno);
    console.log(professorUpdate);
    console.log(pno);
    let html = '';
    $.ajax({
        url:"/up/getoneprofessor",
        mehtod:"get",
        data : {'pno' : pno},
        async:false,
        success: (r)=>{
            console.log(r);
                html += `<th scope="row" class="text-center">${r.pno}</th>
                <td><input type="hidden" class="form-control w-100" value="${mno}"/>${name}</td>
                <td><input type="text" class="form-control w-100" value="${r.pgrade}"/></td>
                <td><input type="text" class="form-control w-100" value="${r.psalary}"/></td>
                <td><input type="text" class="form-control w-100" value="${r.proom}"/></td>
                <td><input type="text" class="form-control w-100" value="${r.degree}"/></td>
                <td><input type="text" class="form-control w-100" value="${r.majorpart}"/></td>
                <td><input type="text" class="form-control w-100" value="${r.mainmajor}"/></td>
                <td class="text-center">
                    <button onclick="putProfessor(${r.pno})" class="btn btn-secondary btn-sm" type="button">
                        수정
                    </button>
                </td>`
            professorUpdate.innerHTML=html;
        }
    });

}