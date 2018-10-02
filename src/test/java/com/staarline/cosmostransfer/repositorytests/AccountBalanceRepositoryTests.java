package com.staarline.cosmostransfer.repositorytests;

import com.staarline.cosmostransfer.models.Account;
import com.staarline.cosmostransfer.repositories.AccountBalanceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountBalanceRepositoryTests {
  
    @Autowired
    private TestEntityManager entityManager;
  
    @Autowired
    private AccountBalanceRepository accountBalanceRepository;
 
    private static Validator validator;
 
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
 
    @Test
    public void whenFindByAccountNumber_thenReturnAccount() {
        // given
        Account account = new Account("0141736285", 50000);
        entityManager.persist(account);
        entityManager.flush();
 
        // when
        Account found = accountBalanceRepository.findByAccountNumber(account.getAccountNumber());
 
        // then
        assertEquals(found.getAccountNumber(), account.getAccountNumber());
    }
 
    @Test
    public void whenAccountNumberIsShort_thenThrowException() {
        // given
        Account account = new Account("014173628", 50000);
 
        //when
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
 
        //then
        assertFalse(violations.isEmpty());
    }
 
    @Test
    public void whenAccountIsDebited_thenSubtractAmount() {
        // given
        Account account = new Account("0141736285", 50000);
        entityManager.persist(account);
        entityManager.flush();
 
        // when
        Account found = accountBalanceRepository.findByAccountNumber(account.getAccountNumber());
        found.setBalance(found.getBalance() - 3500);
 
        entityManager.persist(found);
        entityManager.flush();
 
        Account debited = accountBalanceRepository.findByAccountNumber(account.getAccountNumber());
 
        // then
        assertEquals(debited.getBalance(), 46500, 1);
    }
 
    @Test
    public void whenAccountIsCredited_thenAddAmount() {
        // given
        Account account = new Account("0141736285", 50000);
        entityManager.persist(account);
        entityManager.flush();
 
        // when
        Account found = accountBalanceRepository.findByAccountNumber(account.getAccountNumber());
        found.setBalance(found.getBalance() + 3500);
 
        entityManager.persist(found);
        entityManager.flush();
 
        Account credited = accountBalanceRepository.findByAccountNumber(account.getAccountNumber());
 
        // then
        assertEquals(credited.getBalance(), 53500, 1);
    }
}