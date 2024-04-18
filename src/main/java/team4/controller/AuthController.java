package team4.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team4.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController

public class AuthController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthService authService;

    @GetMapping("/email/request")
    public boolean getEmailRequest (@RequestParam String email){
        System.out.println("AuthController.getEmailRequest");
        Random random = new Random();
        String code = "";
        for(int i =0; i<=6; i++){
            code += random.nextInt(10);
        }
        System.out.println("code = " + code);
        request.getSession().setAttribute("code",code);
        request.getSession().setMaxInactiveInterval(180);
        authService.send(email,"[발송]upteam4 인증 코드","인증코드 : "+code);

        return true;
    }

    @GetMapping("/email/find")
    public boolean getEmailfind(@RequestParam String codeinput){
        System.out.println("AuthController.getEmailfind");
        System.out.println("codeinput = " + codeinput);
        Object object = request.getSession().getAttribute("code");

        if(object != null){
            String code = (String)object;
            if(code.equals(codeinput)){return true;}
        }
        return  false;
    }

}
