package com.techriff.userdetails.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.ResourceAccessException;

import com.jayway.jsonpath.Option;
import com.techriff.userdetails.Exception.InCorrectException;
import com.techriff.userdetails.Exception.PasswordNotMatchedException;
import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.Exception.RoleNotFoundException;
import com.techriff.userdetails.dto.MailResponse;
import com.techriff.userdetails.dto.PasswordDto;
import com.techriff.userdetails.entity.PasswordModel;
import com.techriff.userdetails.entity.PasswordResetToken;
import com.techriff.userdetails.entity.TemporaryPassword;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.repository.PasswordResetTokenRepository;
import com.techriff.userdetails.repository.TemporaryPasswordRepository;
import com.techriff.userdetails.repository.UsersRepository;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class PasswordService {
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UsersDetailsService userService;
	@Autowired
	private TemporaryPasswordRepository temporaryPasswordRepository;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Configuration config;
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

	public  String sentNewPassword(String emailAddress) throws ResourceNotFoundException{

		Users existingUsers=usersRepository.findByEmailAdress(emailAddress.trim());
		if(existingUsers!=null)
		{
			String userName=existingUsers.getFirstName();
			String password=PasswordService.getAlphaNumericString(10);
			// existingUsers.setPassword(password);
			//  int userId= existingUsers.getId();
			userService.createTemporaryPasswordForUser(existingUsers,password);
			// Users updatedUserPassword=  usersRepository.save(existingUsers);

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
						"\n"+"Once logged in ,plese change your password to something that you can Remember "
						+ "\n "+"NEVER Share your password to anybody."+
						"\r\n"+
						"Regards  "+
						"\r\n"+
						" Demo pvt.lmt,"
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

			return  "your updated password is "+password +" and "+response.getMessage();

		}
		else
			throw new ResourceNotFoundException("Invalid email address "+emailAddress);

	}

	public String resetPassword(PasswordDto password, String emailAddress) throws Exception {
		Users existingUsers=usersRepository.findByEmailAdress(emailAddress);
		String oldPassword="";
		if(isEmailValidForUsers(existingUsers))
		{
			if(isPasswordTypePrimary(existingUsers))
			{
				oldPassword=oldPassword+existingUsers.getPassword();
			}
			else
				oldPassword=oldPassword+temporaryPasswordRepository.findTempPassword(existingUsers.getId()).toString();
//			if(oldPassword.equals(password.getOldPassword()))
//			{
				//check newpassword and confirmpassword is same
				if(password.getNewPassword().equals(password.getConfirmPassword()))
				{
					existingUsers.setPassword(passwordEncoder.encode(password.getNewPassword()));
					existingUsers.setPasswordType("Primary");
					usersRepository.save(existingUsers);
//				}
//				else
//					throw new PasswordNotMatchedException("password and confirmPassword is not Matched...");
			}
			else
				throw new PasswordNotMatchedException("old password not matched");
			
			
		}
		else
			throw new ResourceNotFoundException("Invalid Email Id");
			return "Password Changed Successfully";
		}



	private boolean isPasswordTypePrimary(Users existingUsers) {
		if(existingUsers.getPasswordType().equals("Primary"))
			return true;
		return false;
	}

	private boolean isEmailValidForUsers(Users existingUsers) {
		if(existingUsers!=null)
			return true;
		return false;
	}

	public String sentResetPasswordToken(String email, HttpServletRequest request) throws ResourceNotFoundException {
		Users existingUser=usersRepository.findByEmailAdress(email);
		String url="";
		if(existingUser!=null)
		{
			String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(existingUser,token);
			url = passwordResetTokenMail(existingUser,applicationUrl(request), token); 
		}
		else
		{
			throw new ResourceNotFoundException("User not found");

		}
		return url;
	}

	private String passwordResetTokenMail(Users existingUser, String applicationUrl, String token) {
		String url =
				applicationUrl
				+ "/users/varifyMailForNewPassword?token="
				+ token;

		//sendVerificationEmail()
		//        log.info("Click the link to Reset your Password: {}",
		//                url);
		System.out.println("Click the link to Reset your Password" +url);
		return url;
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://" +
				request.getServerName() +
				":" +
				request.getServerPort() +
				request.getContextPath();
	}

	public String resetPassword(String temporaryPassword, PasswordModel passwordModel) throws Exception {
		String email=passwordModel.getEmail();
		Users  existingUsers=usersRepository.findByEmailAdress(email);
		if(existingUsers!=null)
		{
			Optional<TemporaryPassword> tempPassword=temporaryPasswordRepository.findTempPassword(existingUsers.getId());
			String password=tempPassword.get().getTempPassword();
			if(password.equals(temporaryPassword))
			{
				TemporaryPassword mapTemporaryPassword=temporaryPasswordRepository.findbyTempPassword(password);
				if(passwordModel.getNewPassword().equals(passwordModel.getConfirmPassword()))
				{
					existingUsers.setPassword(passwordModel.getNewPassword());
					usersRepository.save(existingUsers);
				}
				else
					throw new PasswordNotMatchedException("password and confirmPassword is not Matched...");

				mapTemporaryPassword.setFlag(true);
				temporaryPasswordRepository.save(mapTemporaryPassword);
				return "Your Password has been changed successfully... Please Login Again with your new Password ";
			}

			else
				throw new PasswordNotMatchedException("Invalid temp Password");
		}
		throw new ResourceNotFoundException("Invalid Users Details");
	}

	public String sendVarificationMailForResetPassword(String emailAddress, HttpServletRequest request) throws Exception {
		Users  existingUsers=usersRepository.findByEmailAdress(emailAddress);
		String url="";
		if(existingUsers!=null)
		{
			String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(existingUsers,token);
			url = passwordResetTokenMail(existingUsers,applicationUrl(request), token); 
			System.out.println(url);
			Map<String, Object> model=new HashMap<>();
			model.put("name", existingUsers.getFirstName());
			model.put("url", url);
			MailResponse response = new MailResponse();
			MimeMessage message = sender.createMimeMessage();
			try {
				// set mediaType
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
						StandardCharsets.UTF_8.name());
				// add attachment
				// helper.addAttachment("user-report.pdf", aAttachment);

				Template t = config.getTemplate("varificcation-mail-resetpassword.ftl");
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

				helper.setTo(existingUsers.getEmailAdress());
				helper.setText(html, true);
				

				
				helper.setSubject("Verification Mail");
				helper.setFrom("memukeshsahoo@gmail.com");
				sender.send(message);

				response.setMessage("A varification mail is send to your E-Mail : " + emailAddress);
				response.setStatus(Boolean.TRUE);

			} catch (MessagingException e) {
				response.setMessage("Mail Sending failure : "+e.getMessage());
				response.setStatus(Boolean.FALSE);
			}

			
			return response.getMessage();
		}


		throw new ResourceNotFoundException("Invalid Users Details");
	}

	public String resetPassword(String token) throws  Exception 
	{
		MailResponse response = new MailResponse();
		MimeMessage message = sender.createMimeMessage();
		PasswordResetToken passwordResetToken=passwordResetTokenRepository.findbyToken(token);
		if(passwordResetToken!=null) {
			Users existingUsers= passwordResetToken.getUser();
			String emailAddress=existingUsers.getEmailAdress();
			String userName=existingUsers.getFirstName();
			String password=PasswordService.getAlphaNumericString(10);
			// existingUsers.setPassword(password);
			//  int userId= existingUsers.getId();
			userService.createTemporaryPasswordForUser(existingUsers,password);
			
			try {
				// set mediaType
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
						StandardCharsets.UTF_8.name());
				// add attachment
				// helper.addAttachment("user-report.pdf", aAttachment);

				Map<String, Object> model=new HashMap<>();
				model.put("name",userName );
				model.put("password", password);
				Template t = config.getTemplate("newPassword.ftl");
				String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

				helper.setTo(existingUsers.getEmailAdress());
				helper.setText(html, true);

				helper.setSubject("Your new Password is Ready ");
				helper.setFrom("memukeshsahoo@gmail.com");
				sender.send(message);

				response.setMessage("A new Password has send to your mail  ");
				response.setStatus(Boolean.TRUE);

			} catch (MessagingException e) {
				response.setMessage("Mail Sending failure : "+e.getMessage());
				response.setStatus(Boolean.FALSE);
			}



		}
		else

			throw new ResourceNotFoundException("Invalid TOKEN");
		
		return response.getMessage();
	}





}
