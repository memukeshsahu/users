package com.techriff.userdetails.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.techriff.userdetails.Exception.PasswordNotMatchedException;
import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.Exception.RoleNotFoundException;
import com.techriff.userdetails.dto.MailResponse;
import com.techriff.userdetails.dto.PasswordDto;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.repository.UsersRepository;

@Service
public class PasswordService {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private UsersRepository usersRepository;

    static String getAlphaNumericString(int n) 
    { 

        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz"; 

        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 

        for (int i = 0; i < n; i++) { 

            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
            = (int)(AlphaNumericString.length() 
                    * Math.random()); 

            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                    .charAt(index)); 
        } 

        return sb.toString(); 
    }

    public String sentNewPassword(String emailAddress) throws ResourceNotFoundException{

        Users existingUsers=usersRepository.findByEmailAdress(emailAddress.trim());
        if(existingUsers!=null)
        {
            String userName=existingUsers.getFirstName();
            String password=PasswordService.getAlphaNumericString(10);
            existingUsers.setPassword(password);
            Users updatedUserPassword=  usersRepository.save(existingUsers);
            
            MailResponse response = new MailResponse();
            MimeMessage message = sender.createMimeMessage();
            try {
                // set mediaType
                MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                // add attachment
               // helper.addAttachment("user-report.pdf", aAttachment);
    
                //Template t = config.getTemplate("email-template.ftl");
                //String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
    
                helper.setTo(emailAddress);
                //helper.setText(html, true);
                String text = "Dear \r\n"+userName
                        + "\r\n"
                        + "Your password is changed Your new password is "+password
                       + "\r\n"+
                        "Regards Team Demo.pvt.lmt,"
                        ;
    
                helper.setText(text, false);
                helper.setSubject("Your new Password is Ready ");
                helper.setFrom("memukeshsahoo@gmail.com");
                sender.send(message);
    
                response.setMessage("mail send to : " + emailAddress);
                response.setStatus(Boolean.TRUE);
    
            } catch (MessagingException e) {
                response.setMessage("Mail Sending failure : "+e.getMessage());
                response.setStatus(Boolean.FALSE);
            }
            
            return  "your updated password is "+updatedUserPassword.getPassword()+" and "+response.getMessage();

        }
        else
            throw new ResourceNotFoundException("Invalid email address "+emailAddress);

    }

    public String resetPassword(PasswordDto password, String emailAddress) throws Exception {
        Users existingUsers=usersRepository.findByEmailAdress(emailAddress.trim());
        if(existingUsers!=null)
        {
            if(password.getPassword().equals(password.getConfirmPassword()))
            {
                existingUsers.setPassword(password.getPassword());
                 usersRepository.save(existingUsers);
            }
            else
                throw new PasswordNotMatchedException("password and confirmPassword is not Matched...");
                
        return "you have successfully reset your password";
        }
        else
            throw new ResourceNotFoundException("Invalid User's details");
    } 

}
