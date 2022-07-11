package com.revature.ERS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dto.ReimbursementDto;
import com.revature.ERS.model.*;
import com.revature.ERS.service.ReimbursementService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReimbursementControllerTest {

    @Mock
    ReimbursementService reimbursementService;

    @InjectMocks
    ReimbursementController reimbursementController;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private ModelMapper modelMapper = new ModelMapper();
    private Reimbursement reimbursement1;
    private Reimbursement reimbursement2;
    private User user1;
    private User user2;
    private UserRole employee;
    private UserRole manager;
    private Status pending;
    private Status approved;
    private Type type;
    private static final List<ReimbursementDto> reimbursementDtos = new ArrayList<>();

    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.reimbursementController).build();
    }

    @BeforeEach
    void init() {
        pending = new Status(1, "pending");
        approved = new Status(2, "approved");
        type = new Type(1, "lodging");
        employee = new UserRole(1, "employee");
        manager = new UserRole(2, "manager");
        user1 = new User(1, "Jiwon", "Shim", "jshim", "password", "jshim@email.com", employee);
        user2 = new User(2, "Minah", "Kim", "mkim", "password", "mkim@email.com", manager);
        reimbursement1 = new Reimbursement(1, 500.00, "05/08/2022", null, "reimb1 description", "receipt1.jpg", user1, null, pending, type);
        reimbursement2 = new Reimbursement(2, 600.00, "05/07/2022", "05/08/2022", "reimb2 description", "receipt2.jpg", user1, user2, approved, type);
        reimbursementDtos.add(modelMapper.map(reimbursement1, ReimbursementDto.class));
        reimbursementDtos.add(modelMapper.map(reimbursement2, ReimbursementDto.class));
    }

    @Test
    void getAllReimbursements_positive() throws Exception {
        when(reimbursementService.getAllReimbursements()).thenReturn(reimbursementDtos);

        this.mockMvc.perform(get("/reimbursements"))
                .andExpect(content().json(mapper.writeValueAsString(reimbursementDtos)))
                .andExpect(status().isOk());
    }

//    @Test
//    void getReimbursementByUser_positive() throws Exception {
//        when(reimbursementService.getReimbursementsByUser(user1)).thenReturn(reimbursementDtos);
//
//        this.mockMvc.perform(get("/users/{userId}/reimbursements", 1))
//                .andExpect(content().json(mapper.writeValueAsString(reimbursementDtos)))
//                .andExpect(status().isOk());
//    }
}
