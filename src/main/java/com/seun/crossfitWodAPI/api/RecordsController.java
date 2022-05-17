package com.seun.crossfitWodAPI.api;

import com.seun.crossfitWodAPI.domain.Records;
import com.seun.crossfitWodAPI.domain.dto.RecordsDTO;
import com.seun.crossfitWodAPI.service.RecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/record")
public class RecordsController {

    private final RecordsService recordsService;

    @GetMapping
    public ResponseEntity<List<Records>> getAllRecords(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "4") Integer elementPerPage) {
        return ResponseEntity.ok().body(recordsService.getAllRecords(pageNo, elementPerPage));
    }

    @PostMapping
    public ResponseEntity<Records> createNewRecord(@Valid @RequestBody RecordsDTO recordsDTO) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/records")));
        return ResponseEntity.created(uri).body(recordsService.createNewRecord(recordsDTO));
    }

    @GetMapping (path = "/member")
    public ResponseEntity<List<Records>> getRecordsByMembersId(@RequestParam Long membersId, @RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "4") Integer elementPerPage) {
        return ResponseEntity.ok().body(recordsService.getRecordsByMembersId(membersId, pageNo, elementPerPage));
    }

    @GetMapping (path = "/workout")
    public ResponseEntity<List<Records>> getRecordsByWorkoutId(@RequestParam Long workoutId, @RequestParam(defaultValue = "0") Integer pageNo,
                                                               @RequestParam(defaultValue = "4") Integer elementPerPage) {
        return ResponseEntity.ok().body(recordsService.getRecordsByWorkoutId(workoutId, pageNo, elementPerPage));
    }

    @DeleteMapping(path = "{recordId}")
    public ResponseEntity<String> deleteOneRecord(@PathVariable Long recordId) {
        recordsService.deleteOneRecord(recordId);
        return ResponseEntity.noContent().build();
    }

}
