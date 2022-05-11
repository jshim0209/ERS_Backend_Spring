package com.revature.ERS.service;

import com.revature.ERS.dto.AuthorDto;
import com.revature.ERS.dto.ReimbursementDto;
import com.revature.ERS.dto.ResolverDto;
import com.revature.ERS.exception.NotFound;
import com.revature.ERS.model.*;
import com.revature.ERS.repository.ReimbursementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {
    @Mock
    ReimbursementRepository reimbRepo;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    ReimbursementService reimbService;

    Reimbursement fakeReimb1 = new Reimbursement();
    Reimbursement fakeReimb2 = new Reimbursement();
    User fakeUser1 = new User();
    User fakeUser2 = new User();
    UserRole fakeRole1 = new UserRole();
    UserRole fakeRole2 = new UserRole();
    Status fakeStatus1 = new Status();
    Status fakeStatus2 = new Status();
    Type fakeType1 = new Type();
    Type fakeType2 = new Type();

    @BeforeEach
    public void setup() {
        fakeRole1.setId(1);
        fakeRole1.setRole("employee");

        fakeRole2.setId(2);
        fakeRole2.setRole("manager");

        fakeUser1.setId(1);
        fakeUser1.setFirstName("firstName1");
        fakeUser1.setLastName("lastName1");
        fakeUser1.setUsername("username1");
        fakeUser1.setPassword("password1");
        fakeUser1.setEmail("email1");
        fakeUser1.setRole(fakeRole1);

        fakeUser2.setId(2);
        fakeUser2.setFirstName("firstName2");
        fakeUser2.setLastName("lastName2");
        fakeUser2.setUsername("username2");
        fakeUser2.setPassword("password2");
        fakeUser2.setEmail("email2");
        fakeUser2.setRole(fakeRole2);

        fakeStatus1.setId(2);
        fakeStatus1.setStatus("approved");

        fakeStatus2.setId(1);
        fakeStatus2.setStatus("pending");

        fakeType1.setId(1);
        fakeType1.setType("lodging");
        fakeType2.setId(2);
        fakeType2.setType("food");

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
    }

    @Test
    void test_get_all_reimbursements_positive() {
        List<Reimbursement> fakeReimbs = new ArrayList<>();
        fakeReimbs.add(fakeReimb1);
        fakeReimbs.add(fakeReimb2);

        when(reimbRepo.findAll()).thenReturn(fakeReimbs);

        List<ReimbursementDto> expected = new ArrayList<>();
        expected.add(new ReimbursementDto(1, 1000.00, "05/07/2022",
                "05/08/2022", "description", "image.jpg", new AuthorDto(1, "username1", "email1"),
                new ResolverDto(2, "username2", "email2"), fakeStatus1, fakeType1));

        expected.add(new ReimbursementDto(2, 500.00, "05/08/2022",
                null, "description1", "image1.jpg", new AuthorDto(1, "username1", "email1"),
                null, fakeStatus2, fakeType2));

        List<ReimbursementDto> actual = reimbService.getAllReimbursements();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_get_reimbursementById_positive() throws NotFound {

        when(reimbRepo.findById(1)).thenReturn(Optional.of(fakeReimb1));

        ReimbursementDto expected = new ReimbursementDto(1, 1000.00, "05/07/2022",
                "05/08/2022", "description", "image.jpg", new AuthorDto(1, "username1", "email1"),
                new ResolverDto(2, "username2", "email2"), fakeStatus1, fakeType1);

        ReimbursementDto actual = reimbService.getReimbursementById(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_add_reimbursement_positive() {

        AuthorDto fakeUserDto1 = new AuthorDto(1, "username1", "email1");

        Reimbursement addedReimb = new Reimbursement();
        addedReimb.setId(3);
        addedReimb.setAmount(300.00);
        addedReimb.setDescription("Hotel");
        addedReimb.setTimeSubmitted("05/08/2022");
        addedReimb.setTimeResolved(null);
        addedReimb.setReceipt("image2.jpg");
        addedReimb.setType(fakeType1);
        addedReimb.setStatus(fakeStatus2);
        addedReimb.setAuthor(fakeUser1);
        addedReimb.setResolver(null);

        when(reimbRepo.save(addedReimb)).thenReturn(addedReimb);

        ReimbursementDto expected = new ReimbursementDto(3, 300.00, "05/08/2022", null, "Hotel", "image2.jpg",
                fakeUserDto1, null, fakeStatus2, fakeType1 );

        ReimbursementDto actual = reimbService.addReimbursement(addedReimb);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_find_reimbursement_byUser_positive() {

        List<Reimbursement> fakeReimbs = new ArrayList<>();
        fakeReimbs.add(fakeReimb1);
        fakeReimbs.add(fakeReimb2);

        when(reimbRepo.findByUser(fakeUser1)).thenReturn(fakeReimbs);

        List<ReimbursementDto> expected = new ArrayList<>();
        expected.add(new ReimbursementDto(1, 1000.00, "05/07/2022",
                "05/08/2022", "description", "image.jpg", new AuthorDto(1, "username1", "email1"),
                new ResolverDto(2, "username2", "email2"), fakeStatus1, fakeType1));

        expected.add(new ReimbursementDto(2, 500.00, "05/08/2022",
                null, "description1", "image1.jpg", new AuthorDto(1, "username1", "email1"),
                null, fakeStatus2, fakeType2));

        List<ReimbursementDto> actual = reimbService.getReimbursementsByUser(fakeUser1);

        Assertions.assertEquals(expected, actual);
    }

}