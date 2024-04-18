console.log('test.js')

$(window).on('load',function(){
    if(location.href.includes('cl')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#class").addClass('active');
    }else if(location.href.includes('ro')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#room").addClass('active');
    }else if(location.href.includes('ti')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#time").addClass('active');
    }else if(location.href.includes('pf')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#professor").addClass('active');
    }else if(location.href.includes('cif')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#classinfo").addClass('active');
    }else if(location.href.includes('sch')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#calendar").addClass('active');
    }else if(location.href.includes('pe')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#people").addClass('active');
    }else if(location.href.includes('pt')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#petition").addClass('active');
    }else if(location.href.includes('enr')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#enrolment").addClass('active');
    }else if(location.href.includes('enlist')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#enrolmentList").addClass('active');
    }else if(location.href.includes('auth')){
        $("#customNav > ul > li > a").removeClass('active');
        $("#auth").addClass('active');
    }
})