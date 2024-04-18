   drop database if exists upteam4;
create database upteam4;
use upteam4;

# ================== 과목 ================== #
drop table if exists class;
create table class(
   classno int auto_increment primary key,          # 과목 번호
    classname varchar(20) not null unique,          # 과목 명
    classtype char(4) not null                   # 전공, 교양 여부
);
insert into class(classname, classtype) values('이것이자바다', '전공필수');
insert into class(classname, classtype) values('MYSQL데이터베이스', '전공선택');
insert into class(classname, classtype) values('수묵화의 기초', '교양필수');
insert into class(classname, classtype) values('투자자의 인문학', '교양선택');
select * from class;
select classno, classname from class;
# ================== 회원 ================== #
drop table if exists member;
create table member(
   mno int auto_increment primary key,            # 회원 번호
    id varchar(10) not null unique,               # 회원 아이디
    pw varchar(20) not null,                  # 회원 비밀번호
    name varchar(10) not null,                  # 회원 이름
    phone varchar(14) not null unique,            # 회원 전화번호
    email varchar(30) unique,                  # 회원 이메일
    address varchar(100),                     # 회원 주소
    birth varchar(8) not null,                  # 회원 생년월일
    state int default 0
);
insert into member(id, pw, name, phone, birth) values('aa','aa','김교수','010-1111-1111','19700101');
insert into member(id, pw, name, phone, birth) values('bb','bb','박교수','010-2222-2222','19800202');
insert into member(id, pw, name, phone, birth) values('cc','cc','최학생','010-3333-3333','19980303');
insert into member(id, pw, name, phone, birth) values('dd','dd','나행정','010-4444-4444','19920404');
insert into member(id, pw, name, phone, birth) values('ee','ee','이행정','010-5555-5555','19820505');
insert into member(id, pw, name, phone, birth, state) values('ff','ff','김학생','010-1144-4411','19700101', 1);
insert into member(id, pw, name, phone, birth, state) values('gg','gg','김교수','010-2244-4411','19700101', 2);
insert into member(id, pw, name, phone, birth, state) values('hh','hh','김행정','010-1144-3322','19700101', 3);
insert into member(id, pw, name, phone, birth) values('admin','admin','admin','010-0000-0000','00000000');
select * from member;
select mno, name, birth from member;

# ================== 강의실 ================== #
drop table if exists classroom;
create table classroom(
   rno int auto_increment primary key,            # 강의실 번호
    roomnumber int not null unique,               # 강의실 호실
    totalperson int not null,                  # 강의실 총 인원
    createdate datetime default now(),            # 강의실 등록 날짜
    updatedate datetime default now()            # 강의실 수정 날짜
);
insert into classroom(roomnumber, totalperson) values(101,25);
insert into classroom(roomnumber, totalperson) values(102,25);
insert into classroom(roomnumber, totalperson) values(201,30);
insert into classroom(roomnumber, totalperson) values(202,30);
select * from classroom;

# ================== 강의 시간 ================== #
drop table if exists classtime;
create table classtime(
   tno int auto_increment primary key,
   classno int,                           # 강의 번호
    dayweek char(3) not null,                  # 강의 요일
    starttime varchar(2) not null,               # 강의 시작 시간
    endtime varchar(2) not null,               # 강의 끝 시간
    foreign key(classno) references class(classno) on update cascade on delete cascade
);
update classtime set classno=2 where tno = 1;
insert into classtime(dayweek, starttime, endtime) values('월', 1, 1);
insert into classtime(dayweek, starttime, endtime) values('수', 1, 2);
insert into classtime(dayweek, starttime, endtime) values('목', 2, 3);
insert into classtime(dayweek, starttime, endtime) values('금', 4, 4);
insert into classtime(classno, dayweek, starttime, endtime) values(2, '월', 1, 3);
insert into classtime(classno, dayweek, starttime, endtime) values(3, '월', 1, 4);
insert into classtime(classno, dayweek, starttime, endtime) values(3, '월', 1, 2);
insert into classtime(classno, dayweek, starttime, endtime) values(4, '목', 5, 5);
select * from classtime where classno is Null;
select * from classtime;
select * from classtime inner join class on classtime.classno = class.classno;
select DISTINCT dayweek from classtime;

# ================== 행정직원 ================== #
drop table if exists employee;
create table employee(
   eno int auto_increment primary key,            # 행정직원 번호
    grade varchar(5),                        # 직원 등급
    department varchar(15) not null,            # 행정직원 부서
    salary bigint,                           # 행정직원 급여
    mno_fk int,                              # member 테이블 mno
    foreign key(mno_fk) references member(mno) on update cascade on delete cascade
);
insert into employee(department, mno_fk) values('교육부', 4);
insert into employee(department, mno_fk) values('행정부', 5);
select * from employee;

# ================== 교수 ================== #
drop table if exists professor;
create table professor(
   pno int auto_increment primary key,            # 교수 번호
    pgrade varchar(5) not null,                  # 직원 등급
    psalary bigint,                           # 교수 급여
    proom varchar(100),                        # 교수 강의실 위치
   degree varchar(20) not null,               # 교수 학위
    majorpart varchar(15) not null,               # 교수 전공
   mainmajor varchar(20) not null,               # 교수 담당 학과
    mno_fk int,                              # member 테이블 mno
    foreign key(mno_fk) references member(mno) on update cascade on delete cascade
);

insert into professor(pgrade, degree, majorpart, mainmajor,mno_fk) values('aa','aa','aa','aa',1);
#insert into professor(pgrade, psalary, proom, degree, majorpart, mainmajor, mno_fk) values('aa',1,'aa','aa','aa','aa',1);
insert into professor(pgrade, degree, majorpart, mainmajor,mno_fk) values('bb','bb','bb','bb',2);
update professor set pgrade = 'cc', psalary = 2, proom = 'bb', degree = 'bb', majorpart = 'bb', mainmajor = 'bb' where mno_fk = 1 and pno = 1;
select * from professor;
select m.name, p.* from professor p inner join member m on p.mno_fk = m.mno;
# 회원의 이름과 교수 같이 출력
select m.name, p.* from professor p inner join member m on p.mno_fk = m.mno;

# ================== 학기 ================== #
drop table if exists season;
create table season(
   sno int auto_increment primary key,
    semester char(6) not null,                           # 학기
    startdate date not null,                           # 학기 시작 날짜
    enddate date not null                              # 학기 끝 날짜
);
insert into season(semester, startdate, enddate) values('202401', '2024-03-04', '2024-06-21');
insert into season(semester, startdate, enddate) values('202402', '2024-09-02', '2024-12-20');
select * from season;

# ================== 강의 정보 ================== #
drop table if exists classinfo;
create table classinfo(
   no int auto_increment primary key,                     
    classno int,                                    # 강의과목 번호 FK
    professor int,                                    # 강의교수 번호 FK         
   roomnumber int,                                    # 강의실 번호 FK
   tno int,                                       # 강의 시간 번호 FK
    sno int,                                       # 학기 번호 FK
    foreign key(classno) references class(classno) on update cascade on delete cascade,
    foreign key(professor) references professor(pno) on update cascade on delete cascade,
    foreign key(roomnumber) references classroom(rno) on update cascade on delete cascade,
    foreign key(classno) references classtime(tno) on update cascade on delete cascade,
    foreign key(sno) references season(sno) on update cascade on delete cascade
);
select * from classinfo;
insert into classinfo(classno, professor, roomnumber, tno, sno) values(1, 1, 1, 1, 1);
#insert into classinfo(classno, professor, roomnumber, tno, sno) values(1, 2, 1, 1, 1);
# [ i : classinfo, c : class, p : professor, r : classroom, t : classtime, s : season ]

select i.no, c.classname, i.professor, m.name, r.roomnumber, t.dayweek, t.starttime, t.endtime, s.semester from classinfo i
   inner join class c on i.classno = c.classno            # 강의no = 최종강의no
   inner join professor p on i.professor = p.pno         # 교수no = 최종교수no
    inner join classroom r on i.roomnumber = r.rno         # 강의실no = 최종강의실no
   inner join classtime t on i.tno = t.tno               # 강의시간no = 최종강의시간no
   inner join season s on i.sno = s.sno               # 학기no = 최종학기no
    inner join member m on p.mno_fk = m.mno order by no;               # 회원no = 교수no
    

    # 보여줘야 할거 강의명(강의번호), 강의요일, 시작시간, 끝시간 ( tno ), 교수이름(pno), 강의실(rno), 학기번호(sno)
    
# =========================== 수강신청 게시판 =========================== #
drop table if exists enrolment;
create table enrolment(
   classno int,
    mno int,
    constraint enrolment_classno_fk foreign key(classno) references class(classno) on delete cascade on update cascade,
    constraint enrolment_mno_fk foreign key(mno) references member(mno) on delete cascade on update cascade
);

select * from enrolment;

insert into enrolment (classno, mno) value (1,1);
select mno from member where id = "aa";
    
    
# =========================== 청원 게시판 =========================== #
drop table if exists petition;
create table petition(
   ppno int auto_increment,               # 글 번호
    ptitle varchar(100),               # 글 제목
    pcontent varchar(255),               # 글 내용
    participation bigint,               # 청원 참여 횟수
    regidate datetime default now(),      # 등록날짜
    duedate varchar(20) ,                  # 마감날짜
    pstate int default 0,               # 청원 상태 0:접수 1:진행, 2:마감
    mno int,                        # 쓴사람 넘버
    primary key(ppno),
    constraint petition_mno_fk foreign key(mno) references member(mno) on update cascade on delete cascade
);

UPDATE petition SET duedate = DATE_ADD(NOW(), INTERVAL 30 DAY); # 등록날짜 기준 30일 뒤 마감날짜
select * from petition;

-- select p.ptitle, p.pcontent, m.name from petition p inner join member m on petition.mno=member.mno;


select p.*, m.name from petition p inner join member m on p.mno = m.mno where p.ppno= 1 ;


select * from enrolment e inner join class c on e.classno = c.classno where mno = 1;

create table participation(
   ppno int,                  # 청원 글 번호
    mno int,                  # 청원을 참여한 사람의 회원번호
   constraint participation_pno_pk foreign key(ppno) references petition(ppno) on update cascade on delete cascade,
   constraint participation_mno_pk foreign key(mno) references member(mno) on update cascade on delete cascade
);