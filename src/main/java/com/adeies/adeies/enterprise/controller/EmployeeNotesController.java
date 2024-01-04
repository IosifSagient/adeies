package com.adeies.adeies.enterprise.controller;

import com.adeies.adeies.enterprise.entities.EmployeeNote;
import com.adeies.adeies.enterprise.entities.SuccessResponse;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.repository.EmployeeNoteRepo;
import com.adeies.adeies.enterprise.service.EmployeeNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-notes")
public class EmployeeNotesController {
    @Autowired
    private EmployeeNoteService employeeNoteService;

    @Autowired
    private EmployeeNoteRepo employeeNoteRepo;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createEmployeeNote(
            @RequestBody EmployeeNote employeeNote, @AuthenticationPrincipal User user) {
        employeeNote.setManager(user);
        employeeNoteRepo.save(employeeNote);
        return ResponseEntity.ok(new SuccessResponse());
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<EmployeeNote>> getEmployeeNotes(
            @RequestParam(value = "employeeId", required = false) String employeeId,
            Authentication auth) {
        User user = (User) auth.getPrincipal();
        List<EmployeeNote> notes = employeeNoteRepo.findEmployeeNoteByManager_IdAndEmployee_Id(
                user.getId(), Long.parseLong(employeeId));
        return ResponseEntity.ok(notes);
    }

}
