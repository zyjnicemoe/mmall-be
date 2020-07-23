package com.zyjblogs.mmall.controller.portal;

import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.zyjblogs.mmall.util.SmsUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/sms/",method = {RequestMethod.GET,RequestMethod.POST})
public class SendSmsController {


    @RequestMapping(value = "send.do")
    @ResponseBody
    public String SendSms(String phone, HttpSession session){
        System.out.println(phone);
        SendSmsResponse res=new SendSmsResponse();
        if (phone!=null) {
            int smsCode = (int) ((Math.random() * 9 + 1) * 100000);
            session.setAttribute("smsCode", smsCode);
             res = SmsUtils.sendSms(smsCode + "", "5", new String[]{"86"+phone});
            return SendSmsResponse.toJsonString(res);
        }
        return SendSmsResponse.toJsonString(res);
    }
}
