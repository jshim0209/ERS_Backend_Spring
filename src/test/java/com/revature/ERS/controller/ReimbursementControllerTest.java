package com.revature.ERS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dto.*;
import com.revature.ERS.model.*;
import com.revature.ERS.service.ReimbursementService;
import com.revature.ERS.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReimbursementControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private ModelMapper modelMapper;
    private Reimbursement reimbursement1;
    private Reimbursement reimbursement2;
    private Reimbursement newReimbursement;
    private User user1;
    private User user2;
    private UserRole employee;
    private UserRole manager;
    private Status pending;
    private Status rejected;
    private Status approved;
    private Type type;
    private UserDto user1Dto;
    private UserDto user2Dto;
    private static final List<ReimbursementDto> reimbursementDtos = new ArrayList<>();
    private AddReimbursementDto addReimbursementDto;
    private ReimbursementDto newReimbDto;
    private AuthorDto author;
    private String jsonReimbursementDto;
    private int userId;

    @Mock
    ReimbursementService reimbursementService;

//    @Mock
//    UserService userService;

    @InjectMocks
    ReimbursementController reimbursementController;



    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.reimbursementController).addPlaceholderValue("ui.url", "http://localhost:4200").build();
    }

    @BeforeEach
    void init() {
        modelMapper = new ModelMapper();
        rejected = new Status(3, "Rejected");
        approved = new Status(2, "Approved");
        pending = new Status(1, "Pending");
        type = new Type(1, "lodging");
        employee = new UserRole(1, "employee");
        manager = new UserRole(2, "manager");
        user1 = new User(2, "Jiwon", "Shim", "jshim", "password", "jshim@email.com", employee);
        user2 = new User(3, "Minah", "Kim", "mkim", "password", "mkim@email.com", manager);
        reimbursement1 = new Reimbursement(1, 500.00, "05/08/2022", "05/09/2022", "reimb1 description", "receipt1.jpg", user1, user2, rejected, type);
        reimbursement2 = new Reimbursement(2, 600.00, "05/07/2022", "05/08/2022", "reimb2 description", "receipt2.jpg", user1, user2, approved, type);
        newReimbursement = new Reimbursement(3, 700.00, "05/09/2022", null, "reimb3 description", "receipt3.jpg", user1, null, pending, type);
        reimbursementDtos.add(modelMapper.map(reimbursement1, ReimbursementDto.class));
        reimbursementDtos.add(modelMapper.map(reimbursement2, ReimbursementDto.class));
        user2Dto = new UserDto(3, "Minah", "Kim", "mkim@email.com", "mkim", manager.getRole());
        author = new AuthorDto("jshim");
        addReimbursementDto = new AddReimbursementDto(700, "05/09/2022", "reimb3 description", "receipt3.jpg", type);
        newReimbDto = new ReimbursementDto(3, 700.00, "05/09/2022", null, "reimb3 description", "receipt3.jpg", author, null, pending, type);
        user1Dto = new UserDto(2, "Jiwon", "Shim", "jshim@email.com", "jshim", employee.getRole());

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
//
////        when(userService.getUserById(2)).thenReturn(user1Dto);
////        User jiwon = modelMapper.map(user1Dto, User.class);
//        when(reimbursementService.getReimbursementsByUser(user1)).thenReturn(reimbursementDtos);
//        userId = user1.getId();
//
//        System.out.println(userId);
//
//        jsonReimbursementDto = mapper.writeValueAsString(reimbursementDtos);
//
//        System.out.println(jsonReimbursementDto);
//
//        this.mockMvc.perform(get("/users/{userId}/reimbursements", "userId"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void addReimbursement_positive() throws Exception {
//        when(reimbursementService.addReimbursement(newReimbursement)).thenReturn(newReimbDto);
//        System.out.println(newReimbDto);
//        System.out.println(newReimbursement);
//
//        this.mockMvc.perform(post("/users/{userId}/reimbursement", "1"))
//                .andExpect(content().json(mapper.writeValueAsString(newReimbDto)))
//                .andExpect(status().isOk());
//    }
}
