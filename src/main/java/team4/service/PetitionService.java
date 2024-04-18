package team4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import team4.model.dao.PetitionDao;
import team4.model.dto.ClassPageDto;
import team4.model.dto.PetitionDto;

import java.util.List;
import java.util.Map;

@Service
public class PetitionService {
    @Autowired
    PetitionDao petitionDao;

    // 청원게시글 등록
    public int PetitionWrite(PetitionDto petitionDto){
        System.out.println("PetitionService.PetitionWrite");
        return petitionDao.PetitionWrite(petitionDto);
    }


    // 청원게시글 출력
    public Object PetitiotionList(int page, int pageSize){
        System.out.println("PetitionController.PetitionWrite");
        int startRow = (page-1)*pageSize;

        int totalSize = petitionDao.PetitiotionListTotal();
        System.out.println("totalSize = " + totalSize);

        int totalpage = totalSize%pageSize == 0 ?
                totalSize/pageSize :
                totalSize/pageSize +1;
        System.out.println("totalpage = " + totalpage);

        int btnSize = 5;
        int startbtn = (page-1)/btnSize*btnSize+1;;
        int endbtn = startbtn + btnSize - 1;
        if(endbtn >= totalpage) endbtn = totalpage;

        List<Object> list = petitionDao.PetitiotionList(startRow, pageSize);

        Object object = ClassPageDto.builder()
                .page(page)
                .totalpage(totalpage)
                .totalSize(totalSize)
                .startbtn(startbtn)
                .endbtn(endbtn)
                .list(list)
                .build();

        System.out.println("object = " + object);
        return object;
    }

    // 청원게시글 개별 출력
    public PetitionDto PetitionView(@RequestParam int ppno){
        System.out.println("PetitionService.PetitionView");
        return petitionDao.PetitionView(ppno);
    }

    // 청원게시글 삭제
    public boolean petitionDelete(@RequestParam int ppno){
        System.out.println("PetitionService.petitionDelete");
        // 현재로그인된 아이디 유효성검사
        boolean result = petitionDao.petitionDelete(ppno);
        return result;
    }

    // 게시물 작성자 인증
    public boolean petitionWriterAuth(int ppno, String id){
        System.out.println("PetitionService.petitionWriterAuth");
        return petitionDao.petitionWriterAuth(ppno, id);
    }

    // 청원게시글 동의하기
    public int petitionAgree(int ppno, int mno){
        System.out.println("PetitionService.agree");
        return petitionDao.petitionAgree(ppno, mno);
    }

    // 댓글 등록
    public boolean replyWrite (@RequestParam Map<String, String> map) {
        System.out.println("PetitionService.replyWrite");
        return petitionDao.replyWrite(map);
    }

    // 댓글 출력
    public List<Map<String, Object>> getReply (int ppno){
        System.out.println("PetitionService.getReply");
        return petitionDao.getReply(ppno);
    }

} //PetitionService end
