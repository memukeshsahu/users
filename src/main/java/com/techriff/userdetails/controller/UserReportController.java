package com.techriff.userdetails.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techriff.userdetails.dto.ReportDTO;
import com.techriff.userdetails.entity.Address;
import com.techriff.userdetails.entity.UserRoleMap;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.repository.AddressRepository;
import com.techriff.userdetails.repository.UserRoleMapRepository;
import com.techriff.userdetails.repository.UsersRepository;
import com.techriff.userdetails.repository.UsersRoleRepository;

import freemarker.template.Configuration;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@RestController
//@SecurityRequirement(name = "Bearer Authentication")

public class UserReportController {
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private Configuration config;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserRoleMapRepository roleMapRepo; 
    @Autowired
    private UsersRoleRepository roleRepo;
    @Autowired
    private AddressRepository addressRepository;
    String path="D://userReport";

    @GetMapping("users/report")
    @Cacheable(value="usersReportInfo") 
    public ResponseEntity<byte[]> exportReport() throws FileNotFoundException, JRException
    {
        List<Users> listOfUser=usersRepository.findAll();
        JSONArray usersList=new JSONArray();
        for (Users user : listOfUser) {

            ReportDTO reportDto=new ReportDTO();
            reportDto.setId(user.getId());
            //reportDto.setCity(user.getCity());
            reportDto.setFirstName(user.getFirstName());
            reportDto.setLastName(user.getLastName());
            reportDto.setMiddleName(user.getMiddleName());
            //reportDto.setState(user.getState());
            //userDto.setPassword(user.getPassword());
            reportDto.setEmailAdress(user.getEmailAdress());
            //reportDto.setZipCode(user.getZipCode());


            reportDto.setDob(user.getDob());
            //reportDto.setProfilePicture(user.getProfilePicture());
            List<UserRoleMap> mapList = roleMapRepo.findByUserId(user.getId());
            List<String> roles = new ArrayList<String>();

            mapList.forEach(roleList -> {
                //UserRoleMapDTO dto = new UserRoleMapDTO();
                //dto.setUserRoleId(String.valueOf(roleList.getId().getUserRoleId()));
                Optional<UsersRole> roleMaster = roleRepo.findById(roleList.getId().getUserRoleId());
                //dto.setRoleName(roleMaster.get().getRole());
                roles.add(roleMaster.get().getRole());
                String rolesDisplay = roles.toString();
                rolesDisplay = rolesDisplay.substring(1, rolesDisplay.length() - 1);

                reportDto.setRoles(rolesDisplay.toString());

                reportDto.setRoles(roles.toString());
            });
            Address addressReportMap=addressRepository.findPrimaryAddress(user.getId());
            if(addressReportMap==null)
            {
                reportDto.setAddress(" ");
                reportDto.setCity(" ");
                reportDto.setState(" ");
                reportDto.setZipCode(0);
                
            }
            else
            {
            //System.out.println(addressReportMap.getAddress());
            reportDto.setAddress(addressReportMap.getAddress());
            reportDto.setCity(addressReportMap.getCity());
            reportDto.setState(addressReportMap.getState());
            reportDto.setZipCode(addressReportMap.getZipCode());
            }
            usersList.add(reportDto);
            //result.put("Users", array);


        }
        //load File and compile it
        File file = ResourceUtils.getFile("classpath:report.jrxml");
        JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(usersList);
        Map<String, Object> parameter= new HashMap<>();
        parameter.put("","");

        JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameter,dataSource);
        byte userReport[] = JasperExportManager.exportReportToPdf(jasperPrint);

        System.err.println(userReport);
        //Sent Mail
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        DataSource aAttachment =  new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
        //	    MailResponse response = new MailResponse();
        //		MimeMessage message = sender.createMimeMessage();
        //		try {
        //			// set mediaType
        //			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        //					StandardCharsets.UTF_8.name());
        //			// add attachment
        //			helper.addAttachment("user-report.pdf", aAttachment);
        //
        //			//Template t = config.getTemplate("email-template.ftl");
        //			//String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        //
        //			helper.setTo("mukesh.sahu@techriff.in");
        //			//helper.setText(html, true);
        //		    String text = "Good day Sir\r\n"
        //		    		+ "\r\n"
        //		    		+ "Please see below the Report ";
        //
        //		    helper.setText(text, false);
        //			helper.setSubject("Sending You User Report");
        //			helper.setFrom("memukeshsahoo@gmail.com");
        //			sender.send(message);
        //
        //			response.setMessage("mail send to : " + "mukesh.sahu@techriff.in");
        //			response.setStatus(Boolean.TRUE);
        //
        //		} catch (MessagingException e) {
        //			response.setMessage("Mail Sending failure : "+e.getMessage());
        //			response.setStatus(Boolean.FALSE);
        //		}
        //
        //		

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=user-report.pdf");

        return  ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(userReport);



    }



}
