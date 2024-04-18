console.log('Member.js')
let checkmember = [false,false,false,false,false,false,false];
//회원가입
function signup(){
    //유효성 검사
    for(let i=0; i<checkmember.length; i++){
    if(!checkmember[i]){
        alert('잘못된 부분이 존재합니다. 다시 확인해주세요')
        return;
    }
    }

    console.log("signup");

    let signUpForm = document.querySelectorAll('.signUpForm')
        console.log(signUpForm);
    let signUpFormData = new FormData(signUpForm[0]);
        console.log(signUpFormData)

    $.ajax({
            url : '/member/signup',
            method : 'POST',
            data : signUpFormData,
            contentType : false,
            processData : false,
            success : function result( result ){
                console.log(result)
                if(result){
                alert('회원가입성공');
                location.href = '/m/log'
                }else{alert('회원가입실패')}
            }
        });
}
// 회원가입 유효성 검사
// 아이디 유효성 검사
function idcheck(){
    console.log('idcheck()');
    let id = document.querySelector('#id').value;

    let idrule = /^[a-z0-9]{3,10}$/

    console.log(idrule.test(id));
    console.log( id );
    if(idrule.test( id )){
        $.ajax({
            url : "/member/find/idcheck",
            method : "get",
            data : {'id':id},
            success : (r) => {
                if(r){
                    document.querySelector('.idcheckbox').innerHTML = '<p class="text-danger mt-2 border-bottom border-danger">사용중인아이디입니다.</p>'
                    checkmember[0] = false;
                }else{
                    document.querySelector('.idcheckbox').innerHTML = '<p class="text-success mt-2 border-bottom border-success">사용가능한아이디입니다.</p>'
                    checkmember[0] = true;
                }
            }
        })
    }else{
        document.querySelector('.idcheckbox').innerHTML = '<p class="text-danger mt-2 border-bottom border-danger">영어와 숫자 조합의 3~10글자 사이로 입력하세요</p>';
        checkmember[0] = false;
    }
}
//비밀번호 유효성검사
function pwcheck(){
    console.log('pwcheck()');
    let pw = document.querySelector('#pw').value;
    let pwrule = /^[a-z0-9]{3,20}$/
    console.log(pwrule.test(pw))
    checkmember[1] = false;
    if(pwrule.test(pw)){
      document.querySelector('.pwcheckbox').innerHTML = '<p class="text-success mt-2 border-bottom border-success">사용가능한 비밀번호입니다.</p>'
      checkmember[1] = true;
    }else{
        document.querySelector('.pwcheckbox').innerHTML = '<p class="text-danger mt-2 border-bottom border-danger">사용불가능한 비밀번호입니다.</p>'
    }
}
//이름 유효성검사
function namecheck(){
    console.log("namecheck()");
    let name = document.querySelector('#name').value;
    let namerule = /^[a-zA-Z가-힣]{2,10}$/
    checkmember[2] = false;
    if(namerule.test(name)){
        checkmember[2] = true;
        document.querySelector('.namecheckbox').innerHTML ='<p class="text-success mt-2 border-bottom border-success">올바른 이름입니다.</p>'
    }else{
        document.querySelector('.namecheckbox').innerHTML ='<p class="text-danger mt-2 border-bottom border-danger">잘못된 형식의 이름입니다.</p>'
    }
}
//전화번호 유효성검사
function phonecheck(){
    console.log('phonecheck()');
    let phone = document.querySelector('#phone').value;
    let phonerule = /^([0-9]{2,3})+[-]+([0-9]{3,4})+[-]+([0-9]{4})$/
    checkmember[3] = false;
    if(phonerule.test(phone)){
        document.querySelector('.phonecheckbox').innerHTML ='<p class="text-success mt-2 border-bottom border-success">올바른 전화번호입니다.</p>'
        checkmember[3]=true;
    }else{
        document.querySelector('.phonecheckbox').innerHTML = '<p class="text-danger mt-2 border-bottom border-danger">000-0000-0000 의 형식으로 입력하세요</p>'

    }
}
//이메일 유효성검사 
function emailcheck(){
    console.log('emailcheck()');
    let email = document.querySelector('#email').value;
    let emailrule = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9]+\.[a-zA-Z0-9]+$/

    checkmember[4] = false;
    if(emailrule.test(email)){
        document.querySelector('.emailcheckbox').innerHTML = '<p class="text-success mt-2 border-bottom border-success">올바른 이메일 입니다.</p>'
    }else{
        document.querySelector('.emailcheckbox').innerHTML = '<p class="text-danger mt-2 border-bottom border-danger">xxx@xxx.xxx 의 형태로 입력하세요</p>'
    }
}
//주소 유효성 검사
function addresscheck(){
    console.log('addresscheck()')
    let address = document.querySelector('#address').value;
    let addressrule = /^[가-힣]{3,100}$/

    checkmember[5] = false;
    if(addressrule.test(address)){
        document.querySelector('.addresscheckbox').innerHTML = '<p class="text-success mt-2 border-bottom border-success">올바른 주소입니다.</p>'
        checkmember[5] = true;
    }else{
        document.querySelector('.addresscheckbox').innerHTML = '<p class="text-danger mt-2 border-bottom border-danger">잘못된 주소입니다.</p>'
    }

}
//생년월일 유효성 검사 
function birthcheck(){
    console.log('birthcheck()');
    let birth = document.querySelector('#birth').value;
    let birthrule = /^[0-9]{8}$/

    checkmember[6]=false;
    if(birthrule.test(birth)){
        document.querySelector('.birthcheckbox').innerHTML = '<p class="text-success mt-2 border-bottom border-success">올바른 생년월일 입니다.</p>'
        checkmember[6]=true;
    }else{
        document.querySelector('.birthcheckbox').innerHTML = '<p class="text-danger mt-2 border-bottom border-danger">00000000 의형식으로 입력하세요</p>'
    }
}

//로그인
function login(){
    console.log('login()')

    let id = document.querySelector('#id').value;
    let pw = document.querySelector('#pw').value;

    let info = {id:id, pw:pw}
    console.log(info);

    $.ajax({
        method: "post",
        url: "/member/login",
        data: info,
        success: function (result) {
            console.log(result);
            if(result){
                alert("로그인성공")
                location.href = "/in"
            }else{
                alert("로그인실패")
            }
        }
    });
}

// 이메일 인증하기
let timerInter = null;
let timer = 0;
let authbox = document.querySelector('.authbox');
let authBtn = document.querySelector('.authBtn');

function authrequest(){
    let html = `<span class = "timerBox"> 03 : 00 </span>
                <input type = "text" class ="codeinput"/>
                <button type="button" onclick="auth()" class="btn btn-outline-secondary btn-sm">인증확인</button>`;

    authbox.innerHTML = html;

    $.ajax({
        url : "/email/request",
        method : "get",
        data : {"email" : document.querySelector('#email').value},
        success : (r)=>{
            if(r){
                timer = 180;
                ontimer();
                authBtn.disabled = true;
            }else{
            alert('인증이 불가합니다.')
            }

        }
    })
}

function auth(){
    let codeinput = document.querySelector('.codeinput').value;

    $.ajax({
        url : "/email/find",
        method : "get",
        data : {'codeinput':codeinput},
        success : (r) => {
            if(r){
                checkmember[4] = true;
                document.querySelector('.emailcheckbox').innerHTML = '인증 성공';
                clearInterval(timerInter);
                authbox.innerHTML = ``;
                authBtn.disabled = false;
            }else{
            alert('인증번호가 잘못되었습니다.')
            }
        }
    })
}



function ontimer(){
 timerInter = setInterval(()=>{
         //1. 초 변수를 분/초 변환
         let m = parseInt(timer/60);
         let s = parseInt(timer%60);
         //2. 시간을 두 자릿수로 표현
         m = m<10 ? "0"+m : m;
         s = s<10 ? "0"+s : s;

         //3. 시간 출력
         document.querySelector('.timerBox').innerHTML = `${m}분${s}초`;

         //4. 시간 감소
         timer--;
         //5. 종료
         if(timer < 0){
             clearInterval(timerInter);
             authbox.innerHTML = `다시 입력해 주세요`;
             authBtn.disabled = false;
         }

     },1000)
}