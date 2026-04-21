package com.bookstore.productservice.exception;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Map<String,Object> err(int s,String m){return Map.of("status",s,"message",m);}
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound(EntityNotFoundException e){return ResponseEntity.status(404).body(err(404,e.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException e){
        String msg=e.getBindingResult().getFieldErrors().stream().map(f->f.getField()+": "+f.getDefaultMessage()).collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(err(400,msg));}
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generic(Exception e){return ResponseEntity.status(500).body(err(500,"Internal error"));}
}