getAllProfessor()

// ========================= 교수 ALL 출력 ========================= //
function getAllProfessor(){
    console.log('getAllProfessor');
    let getAllProfessor = document.querySelector('.getAllProfessor');
    let html = '';
    console.log(getAllProfessor);
    $.ajax({
        url: "/up/galp",
        method : "get",
        async : false,
        success: (r)=>{
            console.log(r);
            for(let i=0; i<r.length; i++){
                html += `<tr class="professorUpdate${r[i].pno} text-center">
                <th scope="row" class="text-center">${r[i].pno}</th>
                <td value="${r[i].mno}">${r[i].name}</td>
                <td>${r[i].pgrade}</td>
                <td>${r[i].psalary}</td>
                <td>${r[i].proom}</td>
                <td>${r[i].degree}</td>
                <td>${r[i].majorpart}</td>
                <td>${r[i].mainmajor}</td>
                <td class="text-center" id="editBtn">
                    <button type="button" class="btn btn-outline-secondary btn-sm" onclick="getPutProfessor(${r[i].pno}, ${r[i].mno}, '${r[i].name}')">
                        Edit
                    </button>
                </td>
                </tr>`
            }
            getAllProfessor.innerHTML = html;
        }
    });
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