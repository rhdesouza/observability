package com.example.observability.webapi.unit.exception;

import com.example.observability.webapi.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BusinessExceptionTest {

    @Test
    void construct_super(){
        BusinessException businessException = new BusinessException();
        Assertions.assertEquals(businessException.getCause(), null);
    }

    @Test
    void construct_string(){
        BusinessException businessException = new BusinessException("Test");
        Assertions.assertEquals(businessException.getMessage(), "Test");
    }

    @Test
    void construct_throwable(){
        BusinessException businessException = new BusinessException("Test", new Throwable("error"));
        Assertions.assertEquals(businessException.getMessage(), "Test");
        Assertions.assertEquals(businessException.getCause().getMessage(), "error");
    }

}
