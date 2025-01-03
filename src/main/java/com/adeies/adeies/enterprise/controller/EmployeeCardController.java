package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.dto.employee.EmployeeDto;
import com.adeies.adeies.enterprise.dto.employee.EmployeeRs;
import com.adeies.adeies.enterprise.dto.employee.searchEmployee.SearchEmployeeRq;
import com.adeies.adeies.enterprise.dto.user.UserIdLastNameDTO;
import com.adeies.adeies.enterprise.entities.EmployeeCard;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.ResponseMessages;
import com.adeies.adeies.enterprise.enums.Role;
import com.adeies.adeies.enterprise.exception.ValidationFaultException;
import com.adeies.adeies.enterprise.repository.EmployeeRepo;
import com.adeies.adeies.enterprise.repository.UserRepo;
import com.adeies.adeies.enterprise.service.EmployeeService;
import com.adeies.adeies.enterprise.utils.EmployeeSpecifications;
import com.adeies.adeies.enterprise.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCardController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private  EmployeeRepo employeeRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserUtils userUtils;

    @GetMapping("/getSth")
    public ResponseEntity<String> getsth(){
        return new ResponseEntity<>("hi " , HttpStatus.OK);
    }



    @GetMapping("/login")
    public String log(@AuthenticationPrincipal OAuth2User oAuth2User, Model model ){
        model.addAttribute("body",model.getAttribute("name"));
        return "sth";
    }


    @PostMapping("/createEmployeeCard")
    public ResponseEntity<EmployeeRs> createEmployeeCard(@RequestBody EmployeeCard employee) {
        EmployeeRs newEmployeeRs = employeeService.createEmployee(employee);
        return ResponseEntity.ok(newEmployeeRs);

    }

    @PostMapping("/updateEmployeeCard")
    public ResponseEntity<SuccessResponse> updateEmployeeCard(@RequestBody EmployeeCard dto) {
        employeeService.updateEmployee(dto);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @PostMapping("/deleteEmployeeCard")
    public ResponseEntity<SuccessResponse> deleteEmployeeCard(@RequestBody EmployeeDto dto) {
        employeeService.deleteEmployee(dto);
        return ResponseEntity.ok(new SuccessResponse(ResponseMessages.SUCCESSFUL_DELETE, null));
    }

    @PostMapping("/searchEmployeeCards")
    public ResponseEntity<List<EmployeeCard>> searchEmployeeCard(
            @RequestBody SearchEmployeeRq request) {
        List<EmployeeCard> res = employeeService.searchEmployees(request);
        return ResponseEntity.ok(res);
    }

    @GetMapping("get/by-department")
    public List<UserIdLastNameDTO> getEmployeesByDpt(@AuthenticationPrincipal OAuth2User oAuth2User){
         User user = userUtils.getUserFromOAuth(oAuth2User);

        Long deptId = user.getEmployeeCard().getDepartment().getId();
        List<User> allUsersInDpt = userRepo.findAll().stream().filter(user1 -> user1.getEmployeeCard().getDepartment().getId().equals(deptId)).toList();
        return allUsersInDpt.stream()
                .map(user1 -> {
                   UserIdLastNameDTO userIdLastNameDTO =  new UserIdLastNameDTO();
                   userIdLastNameDTO.setLastName(user1.getEmployeeCard().getLastName());
                   userIdLastNameDTO.setUserId(user1.getId());
                   return userIdLastNameDTO;
                })
                .toList();
    }

    @GetMapping("get/allEmployees")
    public ResponseEntity<SuccessResponse<List<EmployeeCard>>> getAllEmployeeCards(@AuthenticationPrincipal OAuth2User oAuth2User){

        User user = userUtils.getUserFromOAuth(oAuth2User);
        Long deptId = user.getEmployeeCard().getDepartment().getId();

        switch (user.getRole()) {
            case USER -> {
                return ResponseEntity.ok(new SuccessResponse<>("DATA " ,List.of(user.getEmployeeCard())));
            }
            case MANAGER -> {
                Specification<EmployeeCard> spec = EmployeeSpecifications.hasDepartment(deptId);
                return ResponseEntity.ok(new SuccessResponse<>("DATA " ,employeeRepo.findAll(spec)));
            }
            case ADMIN -> {
                return ResponseEntity.ok(new SuccessResponse<>("DATA " ,employeeRepo.findAll()));
            }
            default -> throw new ValidationFaultException("5555", "No Role");
        }
    }
}
