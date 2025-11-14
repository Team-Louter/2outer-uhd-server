package com.example.clubfind.service;

import com.example.clubfind.dto.FindDetailResponse;
import com.example.clubfind.entity.Find;
import com.example.clubfind.repository.FindRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FindService {
    private final FindRepository findRepository;

    // create
    public Find create(FindDetailResponse request) {
        Find find = Find.builder()
                .name(request.getName())
                .location(request.getLocation())
                .explain(request.getExplain())
                .category(request.getCategory())
                .imagePath(request.getImagePath())
                .build();
        return findRepository.save(find);
    }

    // read all
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<Find> findAll() {
        return findRepository.findAll();
    }

    // find one


    // update
    public Find update(Long id, FindDetailResponse request) {
        Find find = findRepository.findById(id)
                .orElseThrow(()->new RuntimeException("해당 프로젝트는 존재하지 않습니다."));
        find.setName(request.getName());
        find.setLocation(request.getLocation());
        find.setExplain(request.getExplain());
        find.setCategory(request.getCategory());
        find.setImagePath(request.getImagePath());
        return findRepository.save(find);
    }

    // delete
    public void delete(Long id) {
        findRepository.deleteById(id);
    }
}
