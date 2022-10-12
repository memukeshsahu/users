package com.techriff.userdetails.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.techriff.mail.service.EmailService;
import com.techriff.userdetails.Exception.AgeLimitNotReachedException;
import com.techriff.userdetails.Exception.DuplicateResourceException;
import com.techriff.userdetails.Exception.ResourceNotFoundException;
import com.techriff.userdetails.dto.AddressDto;
import com.techriff.userdetails.dto.MailRequest;
import com.techriff.userdetails.dto.MailResponse;
import com.techriff.userdetails.dto.UserDTO;
import com.techriff.userdetails.dto.UserRoleMapDTO;
import com.techriff.userdetails.entity.Address;
import com.techriff.userdetails.entity.AddressType;
import com.techriff.userdetails.entity.UserRoleMap;
import com.techriff.userdetails.entity.UserRoleMapPK;
import com.techriff.userdetails.entity.Users;
import com.techriff.userdetails.entity.UsersRole;
import com.techriff.userdetails.pages.UsersPage;
import com.techriff.userdetails.pages.UsersSearchCriteria;
import com.techriff.userdetails.repository.AddressRepository;
import com.techriff.userdetails.repository.AddressTypeRepository;
import com.techriff.userdetails.repository.UserRoleMapRepository;
import com.techriff.userdetails.repository.UsersCriteriaManagerRepository;
import com.techriff.userdetails.repository.UsersRepository;
import com.techriff.userdetails.repository.UsersRoleRepository;
import com.techriff.userdetails.util.FileUtil;
import com.techriff.userdetails.validationAnnotation.ValidateAdressType;

@Service
@Profile(value = { "local", "dev","test"})
public class UsersDetailsService {
    private static Logger log= LogManager.getLogger(UsersDetailsService.class);
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRoleMapRepository roleMapRepo;

    @Autowired
    private UsersRoleRepository roleRepo;
    @Autowired
    private UsersCriteriaManagerRepository criteriaManagerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressTypeRepository addressTypeRepository;


    private EmailService emailService;
    //private static Logger logger = LogManager.getLogger(UsersDetailsService.class);


    public Users getUserDetailsById(int id, UserDTO userDto) throws ResourceNotFoundException {

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found :" + id));
        userDto.setId(user.getId());
        //userDto.setCity(user.getCity());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMiddleName(user.getMiddleName());
        //userDto.setState(user.getState());
        //		userDto.setPassword(user.getPassword());
        userDto.setEmailAddress(user.getEmailAdress());
        //userDto.setZipCode(user.getZipCode());
        userDto.setDob(user.getDob());
        //userDto.setProfilePicture(user.getProfilePicture());
        List<UserRoleMap> mapList = roleMapRepo.findByUserId(id);
        List<UserRoleMapDTO> roles = new ArrayList<UserRoleMapDTO>();

        mapList.forEach(roleList -> {
            UserRoleMapDTO dto = new UserRoleMapDTO();
            // dto.setUserId(String.valueOf(roleList.getId().getUserId()));
            dto.setUserRoleId(String.valueOf(roleList.getId().getUserRoleId()));
            Optional<UsersRole> roleMaster = roleRepo.findById(roleList.getId().getUserRoleId());
            dto.setRoleName(roleMaster.get().getRole());
            roles.add(dto);
            userDto.setRoles(roles);
        });
        List<Address> existingAddress = user.getAddress();
        List<AddressDto> addresses=new ArrayList<AddressDto>();
        for(Address mapAddress:existingAddress )
        {
            AddressDto addressDto=new AddressDto();
            addressDto.setId(mapAddress.getId());
            addressDto.setAddress(mapAddress.getAddress());
           //addressDto.setAddressType(mapAddress.getAddressTypeId());
            Optional<AddressType> addressType=addressTypeRepository.findByAddressTypeId(mapAddress.getAddressTypeId());
            addressDto.setAddressType(addressType.get().getAddressType().toString());
            
            addressDto.setCity(mapAddress.getCity());
            addressDto.setState(mapAddress.getState());
            addressDto.setZipCode(mapAddress.getZipCode());
            addresses.add(addressDto);
            userDto.setAddress(addresses);
            
        }

        return user;

    }
//
//    public Users addUserDetails(Users user, MultipartFile[] file) throws Exception {
//        String reqestEmail = user.getEmailAdress();
//        Users existingUser = usersRepository.findByEmailAdress(reqestEmail);
//        if (existingUser != null) {
//            String existingEmail = existingUser.getEmailAdress();
//            if (reqestEmail.equals(existingEmail)) {
//                throw new DuplicateEmailAddressException("Email Address Alredy Exist");
//
//            } else
//                savingUserDetail(user, file);
//
//        }
//
//        return savingUserDetail(user, file);
//
//    }

//    public Users savingUserDetail(Users user, MultipartFile[] file)
//            throws ParseException, Exception, AgeLimitNotReachedException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar dob = Calendar.getInstance();
//        String dobOfUser = sdf.format(user.getDob());
//        dob.setTime(sdf.parse(dobOfUser));
//        int age = getAge(dob);
//        if (age >= 18) {
//
//            Users saveUser = usersRepository.save(user);
//            profilePicturerSave(file, saveUser);
//
//            List<String> roleId = user.getUsersRoleId();
//            for (String usersRole : roleId) {
//                UserRoleMap roleData = new UserRoleMap();
//                roleData.setId(new UserRoleMapPK(saveUser.getId(), Integer.parseInt(usersRole)));
//                roleMapRepo.save(roleData);
//            }
//
//            return saveUser;
//        } else
//
//            throw new AgeLimitNotReachedException("User's age Should be greater than 18 ");
//    }

    public Users profilePicturerSave(MultipartFile[] file, Users saveUser) throws IOException {
        if (file.length > 0) {

            for (MultipartFile fileData : file) {

                String path = "/UserDetailsWithException/src/main/resources/static/ImageFileUpload";
                @SuppressWarnings("unused")
                String name = fileData.getOriginalFilename();
                String newFileName = FileUtil.writeFileIntoDir(fileData, path);
                Optional<Users> userFile = usersRepository.findById(saveUser.getId());
                Users userFileData = userFile.get();
                //userFileData.setProfilePicture(newFileName);
                usersRepository.save(userFileData);
            }

        } else {
            Optional<Users> existingUser = usersRepository.findById(saveUser.getId());
            Users presentData = existingUser.get();
            //presentData.setProfilePicture("empty profile picture");
            usersRepository.save(presentData);
            mailSent(saveUser);
            //						

            return presentData;
        }
        return saveUser;
    }

    public void mailSent(Users user) {
        MailRequest request = new MailRequest();
        Map<String, Object> model = new HashMap<>();
        model.put("Location", "Bubaneswar Odisha");
        request.setTo(user.getEmailAdress());

        request.setFrom("memukeshsahoo@gmail.com");
        request.setSubject("Thanks for Joining with us");
        request.setName(user.getFirstName());
        MailResponse newMail = emailService.sendEmail(request, model);
    }

    public static int getAge(Calendar dob) throws Exception {
        Calendar today = Calendar.getInstance();

        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);

        int age = curYear - dobYear;

        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }

        return age;
    }

    public String deleteuserById(int id) {
        usersRepository.deleteById(id);
        return "User Deleted with id: " + id;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getUserDetails(UsersPage usersPage, UsersSearchCriteria usersSearchCriteria) {
        JSONObject result = new JSONObject();
        JSONObject ListData = new JSONObject();
        try {
            Page<Users> listOfUsers = criteriaManagerRepository.findAllWithFilters(usersPage, usersSearchCriteria);
            JSONArray array = new JSONArray();

            for (Users user : listOfUsers) {
                UserDTO userDto = new UserDTO();
                //userDto = new UserDTO();
                userDto.setId(user.getId());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setMiddleName(user.getMiddleName());
                userDto.setEmailAddress(user.getEmailAdress());
                //userDto.setCity(user.getCity());
                //userDto.setState(user.getState());
                //userDto.setZipCode(user.getZipCode());
                userDto.setDob(user.getDob());
                //userDto.setProfilePicture(user.getProfilePicture());
                List<UserRoleMap> mapList = roleMapRepo.findByUserId(user.getId());
                List<UserRoleMapDTO> roles = new ArrayList<UserRoleMapDTO>();

                mapList.forEach(roleList -> {
                    UserRoleMapDTO dto = new UserRoleMapDTO();
                    dto.setUserRoleId(String.valueOf(roleList.getId().getUserRoleId()));
                    Optional<UsersRole> roleMaster = roleRepo.findById(roleList.getId().getUserRoleId());
                    dto.setRoleName(roleMaster.get().getRole());
                    roles.add(dto);
                    userDto.setRoles(roles);

                });
                List<Address> existingAddress = user.getAddress();
                List<AddressDto> addresses=new ArrayList<AddressDto>();
                AddressDto addressDto=new AddressDto();
                for(Address mapAddress:existingAddress )
                {
                    addressDto=new AddressDto();
                    addressDto.setId(mapAddress.getId());
                    addressDto.setAddress(mapAddress.getAddress());
                
                  Optional<AddressType>  addressType=addressTypeRepository.findByAddressTypeId(mapAddress.getAddressTypeId());
                   addressDto.setAddressType(addressType.get().getAddressType());
                   
                    addressDto.setCity(mapAddress.getCity());
                    addressDto.setState(mapAddress.getState());
                    addressDto.setZipCode(mapAddress.getZipCode());
                    addresses.add(addressDto);
                    userDto.setAddress(addresses);
                    
                }


                array.add(userDto);
                
                result.put("users", array);

            }

        } catch (Exception e) {

        }
        return result;
    }

    //add users without file
    public Users addUserDetails(Users users) throws Exception {
        String reqestEmail = users.getEmailAdress();
        Users existingUser = usersRepository.findByEmailAdress(reqestEmail);
        if (existingUser != null) {
            String existingEmail = existingUser.getEmailAdress();
            if (reqestEmail.equals(existingEmail)) {
                throw new DuplicateResourceException("Email Address Alredy Exist");

            } else
                savingUserDetail(users);

        }

        return savingUserDetail(users);

    }
    //save data without file
    private Users savingUserDetail(Users users) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dob = Calendar.getInstance();
        String dobOfUser = sdf.format(users.getDob());
        dob.setTime(sdf.parse(dobOfUser));
        int age = getAge(dob);
        if (age >= 18) {

        
            
            List<Address> addresses = users.getAddress();
            List<AddressType> exitingAddressType=addressTypeRepository.findAll();
            for (Address data : addresses)
            {
               
                    for( AddressType addressType:exitingAddressType)

                {
                    // int t1=addressType.getId();
                    //int t2=data.getAddressTypeId();
                    Optional<AddressType> addressTypes=addressTypeRepository.findById(data.getAddressTypeId());
                    if((addressTypes.isPresent()))
                    {
                        int id=data.getAddressTypeId();
                        Optional<Address> existingAddress=addressRepository.findPrimary(users.getId());
                        if(!addressTypes.toString().contains("Primary")  );
                        {
                            Address addressData = new Address();
                            addressData.setAddress(data.getAddress());
                            addressData.setAddressTypeId(data.getAddressTypeId());
                            addressData.setCity(data.getCity());
                            addressData.setState(data.getState());
                            addressData.setZipCode(data.getZipCode());
                        }
                        
                            throw new DuplicateResourceException("Duplicate Primary Address type not allowed");

//                        Address addressData = new Address();
//                        addressData.setAddress(data.getAddress());
//                        addressData.setAddressTypeId(data.getAddressTypeId());
//                        addressData.setCity(data.getCity());
//                        addressData.setState(data.getState());
//                        addressData.setZipCode(data.getZipCode());

                    }
                    else 
                        throw new ResourceNotFoundException("Invalid Address Type");


                }




            }
             
           

            List<String> roles = users.getUsersRoleId();
            Users saveUser = usersRepository.save(users);
            for (String usersRole : roles) {
                UserRoleMap roleData = new UserRoleMap();
                roleData.setId(new UserRoleMapPK(saveUser.getId(), Integer.parseInt(usersRole)));
               
                roleMapRepo.save(roleData);
            }

            return saveUser;
        } else

            throw new AgeLimitNotReachedException("User's age Should be greater than 18 ");


    }

}
