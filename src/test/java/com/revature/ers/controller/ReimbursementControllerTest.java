package com.revature.ers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dto.*;
import com.revature.ers.model.*;
import com.revature.ers.service.ReimbursementService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private final List<ReimbursementDto> reimbursementDtos = new ArrayList<>();
    private AuthorDto author;
    private AddReimbursementDto addReimbursementDto;
    private ReimbursementDto newReimbDto;
    private final List<Status> statuses = new ArrayList<>();

    @Mock
    ReimbursementService reimbursementService;

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
        addReimbursementDto = new AddReimbursementDto(700, "05/09/2022", "reimb3 description", null, type);
        newReimbDto = new ReimbursementDto(3, 700.00, "05/09/2022", null, "reimb3 description", null, author, null, pending, type);

        statuses.add(pending);
        statuses.add(approved);
        statuses.add(rejected);
    }

    @Test
    void getAllStatuses() throws Exception {

        System.out.println(statuses);

        when(reimbursementService.getAllStatuses()).thenReturn(statuses);

        System.out.println(mapper.writeValueAsString(statuses));

        this.mockMvc.perform(get("/statuses"))
                .andExpect(content().json(mapper.writeValueAsString(statuses)))
                .andExpect(status().isOk());
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

    @Test
    void addReimbursement_positive() throws Exception {

        when(reimbursementService.addReimbursement(user1.getId(), addReimbursementDto)).thenReturn(newReimbDto);

        this.mockMvc.perform(post("/users/{userId}/reimbursement", user1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addReimbursementDto)))
                .andExpect(content().json(mapper.writeValueAsString(newReimbDto)))
                .andExpect(status().isOk());
    }
}
