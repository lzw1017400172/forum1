package com.kaishengit.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 刘忠伟 on 2016/12/16.
 * 用来获取邮件
 */
public class EmailUtils {

    private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    public static void sendHtmlEmail(String subject,String htmlMsg,String toAddress){

        //创建html邮件
        HtmlEmail htmlEmail = new HtmlEmail();
        //配置email
        htmlEmail.setHostName(Config.getConfig("email.smpt"));//发送邮件的邮箱服务器地址，126邮箱或其他的
        htmlEmail.setAuthentication(Config.getConfig("email.username"),Config.getConfig("email.password"));//帐号密码，就是自己邮箱帐号密码
        htmlEmail.setCharset(Config.getConfig("email.charset"));//设置字符编码
        htmlEmail.setSmtpPort(Integer.parseInt(Config.getConfig("email.port")));//为数字
        htmlEmail.setStartTLSEnabled(true);

        try {
            htmlEmail.setFrom(Config.getConfig("email.frommail"));//发件人
            htmlEmail.setSubject(subject);//主题
            htmlEmail.setHtmlMsg(htmlMsg);//html内容
            htmlEmail.addTo(toAddress);//发送单个，setTo发送集合
            htmlEmail.send();//发送
        } catch (EmailException e) {
            logger.error("向{}发送邮件异常",toAddress);
            //让服务器知道发邮件错误抛异常
            throw new RuntimeException("向" + toAddress + "邮件发送失败",e);
        }

    }


}
