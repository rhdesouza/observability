package com.example.observability.webapi.unit.exception;

import com.example.observability.webapi.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotFoundExceptionTest {

    @Test
    void construct_super(){
        NotFoundException notFoundException = new NotFoundException();
        Assertions.assertEquals(notFoundException.getCause(), null);
    }

    @Test
    void construct_string(){
        NotFoundException notFoundException = new NotFoundException("Test");
        Assertions.assertEquals(notFoundException.getMessage(), "Test");
    }

    @Test
    void construct_throwable(){
        NotFoundException notFoundException = new NotFoundException("Test", new Throwable("error"));
        Assertions.assertEquals(notFoundException.getMessage(), "Test");
        Assertions.assertEquals(notFoundException.getCause().getMessage(), "error");
    }


}
