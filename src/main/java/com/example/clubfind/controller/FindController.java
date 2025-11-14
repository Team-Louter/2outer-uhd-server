package com.example.clubfind.controller;

import com.example.clubfind.dto.FindDetailResponse;
import com.example.clubfind.entity.Find;
import com.example.clubfind.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/finds")
public class FindController {
    private final FindService findService;

    // create
    @PostMapping
    public Find create(@RequestBody FindDetailResponse findRequest) {
        return findService.create(findRequest);
    }

    // read
    @GetMapping
    public List<Find> findAll() {
        return findService.findAll();
    }

    // update
    @PutMapping("/{id}")
    public Find update(@PathVariable Long id, FindDetailResponse findRequest) {
        return findService.update(id,findRequest);
    }

    // delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        findService.delete(id);
    }
}
