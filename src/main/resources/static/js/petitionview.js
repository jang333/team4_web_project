let ppno = new URL(location.href).searchParams.get('ppno');

console.log(ppno);
let session = {
    state : 0
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
                session.state = logindto.state;
            }
        });
    }
})

// 청원게시글 개별 출력
onView();
onRelyList();

function onView(){
    console.log("onView()");

    $.ajax({
        url : "/up/view",
        method : "get",
        async : false,
        data : {'ppno' : ppno},
        success : (r)=>{
            console.log(r);
            document.querySelector('.ptitle').innerHTML = r.ptitle;
            document.querySelector('.pcontent').innerHTML = r.pcontent;
            document.querySelector('.participation').innerHTML = r.participation;
            document.querySelector('.duedate').innerHTML = r.duedate;
            document.querySelector('.pstate').innerHTML = r.pstate;
            document.querySelector('.changeBtn').innerHTML += `<button onclick="ptBtn(${ppno})" type="button" class="btn btn-primary btn text-end">참여하기</button>`;
        }
    });
} // onView end

// 청원게시글 동의하기
function ptBtn( ppno ){
    console.log("ptBtn()")
    
       let result = confirm("해당 청원에 동의 하시겠습니까?");
       if(result){
        $.ajax({
             url : "/up/agree" ,
             method : "post" ,
             async : false ,
             data: { 'ppno' : ppno },
             success : (r)=>{
                 if(r==-1){
                    alert('로그인을 해주세요.')
                    location.href='/m/log';
                 }else if(r >=0) {
                    alert('해당 청원에 동의 하였습니다.');
                    document.querySelector('.participation').innerHTML = r;
                 } else if (r == -2){
                    alert('해당 청원은 이미 동의를 참여 하였습니다.');
                 }
             }
        });
       }else{
            alert('해당 청원의 동의를 취소합니다.');
       }
}

// 청원게시글 삭제
function onDelete(){
    $.ajax({
        url : "/up/delete" ,
        method : "delete" ,
        async : false ,
        data : {'ppno' : ppno},
        success : (r) =>{
            if(r) {
                alert('해당 청원 글을 삭제 하였습니다')
                location.href = "/up/pt/li"
            }else {
                alert('삭제실패');
                alert('※ 삭제에 실패하였습니다. 관리자에게 문의하세요');
            }
        }
    });
}


// 댓글쓰기
function onRelyWrite(){
    console.log("onRelyWrite()");
    $.ajax({
        url : "/up/reply",
        method : "post",
        async : false ,
        data : {'ppno' : ppno, 'replycontent' : document.querySelector(`.replycontent`).value },
        success : (r) =>{
            console.log(r)
            if(r){
                onRelyList();
                document.querySelector('.replycontent').value='';
            }else {
                alert('※ 댓글 작성에 실패 하였습니다. 관리자에게 문의 하세요.');
            }
        }
    });
}

// 댓글 출력
function onRelyList(){
    $.ajax({
        url : "/up/reply" ,
        method : "get",
        data : {"ppno" : ppno},
        async : false,
        success : (r) => {
            console.log(r);
            let replyListBox = document.querySelector('.replyListBox ul');
            console.log(replyListBox);
            r.forEach((reply) => {
            replyListBox.innerHTML += `
                    <li class="form-control w-75 m-1">${reply.replycontent}</li>`
            }); // forEach end
        } // success end
    }) // ajax end
}
