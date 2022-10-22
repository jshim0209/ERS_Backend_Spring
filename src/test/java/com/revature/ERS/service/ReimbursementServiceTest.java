package com.revature.ERS.service;

import com.revature.ERS.dto.AddReimbursementDto;
import com.revature.ERS.dto.AuthorDto;
import com.revature.ERS.dto.ReimbursementDto;
import com.revature.ERS.dto.ResolverDto;
import com.revature.ERS.model.*;
import com.revature.ERS.repository.ReimbursementRepository;
import com.revature.ERS.repository.StatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReimbursementServiceTest {
    @Mock
    ReimbursementRepository reimbRepo;

    @Mock
    StatusRepository statusRepo;

    @Mock
    UserService userService;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    ReimbursementService reimbService;

    Reimbursement fakeReimb1 = new Reimbursement();
    Reimbursement fakeReimb2 = new Reimbursement();
    Reimbursement fakeReimb3 = new Reimbursement();
    User fakeUser1 = new User();
    User fakeUser2 = new User();
    UserRole fakeRole1 = new UserRole();
    UserRole fakeRole2 = new UserRole();
    Status fakeStatus1 = new Status();
    Status fakeStatus2 = new Status();
    Status fakeStatus3 = new Status();
    Type fakeType1 = new Type();
    Type fakeType2 = new Type();
    AuthorDto fakeAuthorDto = new AuthorDto();

    @BeforeEach
    public void setup() {
        fakeRole1.setId(1);
        fakeRole1.setRole("employee");

        fakeRole2.setId(2);
        fakeRole2.setRole("manager");

        // employee
        fakeUser1.setId(1);
        fakeUser1.setFirstName("firstName1");
        fakeUser1.setLastName("lastName1");
        fakeUser1.setUsername("username1");
        fakeUser1.setPassword("password1");
        fakeUser1.setEmail("email1");
        fakeUser1.setRole(fakeRole1);

        // manager
        fakeUser2.setId(2);
        fakeUser2.setFirstName("firstName2");
        fakeUser2.setLastName("lastName2");
        fakeUser2.setUsername("username2");
        fakeUser2.setPassword("password2");
        fakeUser2.setEmail("email2");
        fakeUser2.setRole(fakeRole2);

        fakeStatus1.setId(2);
        fakeStatus1.setStatus("Approved");

        fakeStatus2.setId(1);
        fakeStatus2.setStatus("Pending");

        fakeStatus3.setId(3);
        fakeStatus3.setStatus("Rejected");

        fakeType1.setId(1);
        fakeType1.setType("Lodging");
        fakeType2.setId(2);
        fakeType2.setType("Food");

        // approved
        fakeReimb1.setId(1);
        fakeReimb1.setAmount(1000.00);
        fakeReimb1.setTimeSubmitted("05/07/2022");
        fakeReimb1.setTimeResolved("05/08/2022");
        fakeReimb1.setDescription("description");
        fakeReimb1.setReceipt("image.jpg");
        fakeReimb1.setAuthor(fakeUser1);
        fakeReimb1.setResolver(fakeUser2);
        fakeReimb1.setStatus(fakeStatus1);
        fakeReimb1.setType(fakeType1);

        // pending
        fakeReimb2.setId(2);
        fakeReimb2.setAmount(500.00);
        fakeReimb2.setTimeSubmitted("05/08/2022");
        fakeReimb2.setTimeResolved(null);
        fakeReimb2.setDescription("description1");
        fakeReimb2.setReceipt("image1.jpg");
        fakeReimb2.setAuthor(fakeUser1);
        fakeReimb2.setResolver(null);
        fakeReimb2.setStatus(fakeStatus2);
        fakeReimb2.setType(fakeType2);

        // pending
        fakeReimb3.setId(3);
        fakeReimb3.setAmount(300.00);
        fakeReimb3.setTimeSubmitted("06/08/2022");
        fakeReimb3.setTimeResolved(null);
        fakeReimb3.setDescription("description2");
        fakeReimb3.setReceipt("image2.jpg");
        fakeReimb3.setAuthor(fakeUser1);
        fakeReimb3.setResolver(null);
        fakeReimb3.setStatus(fakeStatus2);
        fakeReimb3.setType(fakeType2);

        fakeAuthorDto.setUsername("username1");

    }

    @Test
    void test_get_all_reimbursements_positive() {
        List<Reimbursement> fakeReimbs = new ArrayList<>();
        fakeReimbs.add(fakeReimb1);
        fakeReimbs.add(fakeReimb2);

        when(reimbRepo.findAll()).thenReturn(fakeReimbs);

        List<ReimbursementDto> expected = new ArrayList<>();
        expected.add(new ReimbursementDto(1, 1000.00, "05/07/2022",
                "05/08/2022", "description", "image.jpg", new AuthorDto("username1"),
                new ResolverDto("username2"), fakeStatus1, fakeType1));

        expected.add(new ReimbursementDto(2, 500.00, "05/08/2022",
                null, "description1", "image1.jpg", new AuthorDto("username1"),
                null, fakeStatus2, fakeType2));

        List<ReimbursementDto> actual = reimbService.getAllReimbursements();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_get_reimbursementById_positive() {

        when(reimbRepo.findById(1)).thenReturn(Optional.of(fakeReimb1));

        ReimbursementDto expected = new ReimbursementDto(1, 1000.00, "05/07/2022",
                "05/08/2022", "description", "image.jpg", new AuthorDto("username1"),
                new ResolverDto("username2"), fakeStatus1, fakeType1);

        ReimbursementDto actual = reimbService.getReimbursementById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_add_reimbursement_positive() {

        when(userService.getUserById(fakeUser1.getId())).thenReturn(fakeUser1);

        AddReimbursementDto dto = new AddReimbursementDto();
        dto.setAmount(300.00);
        dto.setDescription("Hotel");
        dto.setReceipt("image2.jpg");
        dto.setTimeSubmitted("05/08/2022");
        dto.setType(fakeType1);

        Reimbursement addedReimb = new Reimbursement();
        addedReimb.setId(0);
        addedReimb.setAmount(dto.getAmount());
        addedReimb.setDescription(dto.getDescription());
        addedReimb.setTimeSubmitted(dto.getTimeSubmitted());
        addedReimb.setTimeResolved(null);
        addedReimb.setReceipt(dto.getReceipt());
        addedReimb.setType(dto.getType());
        addedReimb.setStatus(fakeStatus2);
        addedReimb.setAuthor(fakeUser1);
        addedReimb.setResolver(null);

        when(reimbRepo.save(addedReimb)).thenReturn(addedReimb);

        ReimbursementDto expected = new ReimbursementDto(0, 300.00, "05/08/2022", null, "Hotel", "image2.jpg",
                fakeAuthorDto, null, fakeStatus2, fakeType1 );

        ReimbursementDto actual = reimbService.addReimbursement(fakeUser1.getId(), dto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_find_reimbursement_byUser_positive() {

        List<Reimbursement> fakeReimbs = new ArrayList<>();
        fakeReimbs.add(fakeReimb1);
        fakeReimbs.add(fakeReimb2);

        when(reimbRepo.findByAuthorId(fakeUser1.getId())).thenReturn(fakeReimbs);

        List<ReimbursementDto> expected = new ArrayList<>();
        expected.add(new ReimbursementDto(1, 1000.00, "05/07/2022",
                "05/08/2022", "description", "image.jpg", new AuthorDto("username1"),
                new ResolverDto("username2"), fakeStatus1, fakeType1));

        expected.add(new ReimbursementDto(2, 500.00, "05/08/2022",
                null, "description1", "image1.jpg", new AuthorDto("username1"),
                null, fakeStatus2, fakeType2));

        List<ReimbursementDto> actual = reimbService.getReimbursementsByUserId(fakeUser1.getId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_find_reimbursement_byStatus_positive() {
        List<Reimbursement> pendingReimbs = new ArrayList<>();
        pendingReimbs.add(fakeReimb2);
        pendingReimbs.add(fakeReimb3);

        when(reimbRepo.findByStatusId(Optional.of(1))).thenReturn(pendingReimbs);

        List<ReimbursementDto> expected = new ArrayList<>();
        expected.add(new ReimbursementDto(2, 500.00, "05/08/2022",
                null, "description1", "image1.jpg", new AuthorDto("username1"),
                null, fakeStatus2, fakeType2));
        expected.add(new ReimbursementDto(3, 300.00, "06/08/2022",
                null, "description2", "image2.jpg", new AuthorDto("username1"),
                null, fakeStatus2, fakeType2));

        List<ReimbursementDto> actual = reimbService.getReimbursementsByStatus(Optional.of(1));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_getAllStatuses() {
        List<Status> expected = new ArrayList<>();
        expected.add(fakeStatus2);
        expected.add(fakeStatus1);
        expected.add(fakeStatus3);

        when(statusRepo.findAll()).thenReturn(expected);

        List<Status> actual = reimbService.getAllStatuses();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_findReimbursementsByUserIdAndStatus_positive() {
        List<Reimbursement> approvedReimbsByUser = new ArrayList<>();
        approvedReimbsByUser.add(fakeReimb1);

        when(reimbRepo.findByAuthorIdAndStatusId(1, Optional.of(2))).thenReturn(approvedReimbsByUser);

        List<ReimbursementDto> expected = new ArrayList<>();
        expected.add(new ReimbursementDto(1, 1000.00, "05/07/2022",
                "05/08/2022", "description", "image.jpg",
                new AuthorDto("username1"), new ResolverDto("username2"),
                fakeStatus1, fakeType1));

        List<ReimbursementDto> actual = reimbService.getReimbursementsByUserIdAndStatus(1, Optional.of(2));

        Assertions.assertEquals(expected, actual);
    }

}
