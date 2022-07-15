package com.revature.ERS.controller;

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
    private ObjectMapper mapper;
    private Reimbursement reimbursement1;
    private Reimbursement reimbursement2;
    private User user1;
    private User user2;
    private UserRole employee;
    private UserRole manager;
    private Status pending;
    private Status rejected;
    private Status approved;
    private Type type;
    private static final List<ReimbursementDto> reimbursementDtos = new ArrayList<>();
    private AuthorDto author;

    @Mock
    ReimbursementService reimbursementService;

    @Mock
    UserService userService;

    @InjectMocks
    ReimbursementController reimbursementController;

    @Autowired
    ModelMapper modelMapper;

    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.reimbursementController).addPlaceholderValue("ui.url", "http://localhost:4200").build();
    }

    @BeforeEach
    void init() {
        mapper = new ObjectMapper();
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
        reimbursementDtos.add(modelMapper.map(reimbursement1, ReimbursementDto.class));
        reimbursementDtos.add(modelMapper.map(reimbursement2, ReimbursementDto.class));
        author = new AuthorDto("jshim");

//        user2Dto = new UserDto(3, "Minah", "Kim", "mkim@email.com", "mkim", manager.getRole());
//        addReimbursementDto = new AddReimbursementDto(700, "05/09/2022", "reimb3 description", null, type);
//        newReimbDto = new ReimbursementDto(3, 700.00, "05/09/2022", null, "reimb3 description", null, author, null, pending, type);
//        user1Dto = new UserDto(2, "Jiwon", "Shim", "jshim@email.com", "jshim", employee.getRole());

    }

    @Test
    void getAllReimbursements_positive() throws Exception {
        when(reimbursementService.getAllReimbursements()).thenReturn(reimbursementDtos);

        this.mockMvc.perform(get("/reimbursements"))
                .andExpect(content().json(mapper.writeValueAsString(reimbursementDtos)))
                .andExpect(status().isOk());
    }

    @Test
    void getReimbursementByUser_positive() throws Exception {

        when(reimbursementService.getReimbursementsByUserId(user1.getId())).thenReturn(reimbursementDtos);

        this.mockMvc.perform(get("/users/{userId}/reimbursements", user1.getId()))
                .andExpect(content().json(mapper.writeValueAsString(reimbursementDtos)))
                .andExpect(status().isOk());
    }

//    @Test
//    void addReimbursement_positive() throws Exception {
//
//
//        addReimbursementDto = new AddReimbursementDto(700, "05/09/2022", "reimb3 description", null, type);
//
//        when(userService.getUserById(user1.getId())).thenReturn(user1Dto);
//
//        userId = String.valueOf(user1.getId());
//
//        newReimb = new Reimbursement();
//
//        newReimb.setId(3);
//        newReimb.setAmount(addReimbursementDto.getAmount());
//        newReimb.setDescription(addReimbursementDto.getDescription());
//        newReimb.setTimeSubmitted(addReimbursementDto.getTimeSubmitted());
//        newReimb.setType(addReimbursementDto.getType());
//        newReimb.setAuthor(modelMapper.map(userService.getUserById(user1.getId()), User.class));
//        newReimb.setStatus(pending);
//
//        System.out.println(newReimb.getAuthor());
//
//        when(reimbursementService.addReimbursement(newReimb)).thenReturn(newReimbDto);
//        System.out.println(newReimb);
//        System.out.println(newReimbDto);
//
//        jsonReimbursementDto = mapper.writeValueAsString(newReimbDto);
//
//        ResponseEntity<ReimbursementDto> actual = reimbursementController.addReimbursement(userId, addReimbursementDto);
//        System.out.println(actual);
//
//        assertThat(actual).isEqualTo(jsonReimbursementDto);
////        this.mockMvc.perform(post("/users/{userId}/reimbursement", userId)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(jsonAddReimbursementDto))
////                .andExpect(content().json(jsonReimbursementDto))
////                .andExpect(status().isOk());
//    }
}
