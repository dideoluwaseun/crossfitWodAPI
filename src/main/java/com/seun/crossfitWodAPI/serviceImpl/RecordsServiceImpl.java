package com.seun.crossfitWodAPI.serviceImpl;

import com.seun.crossfitWodAPI.domain.Records;
import com.seun.crossfitWodAPI.domain.dto.RecordsDTO;
import com.seun.crossfitWodAPI.exception.BadRequestException;
import com.seun.crossfitWodAPI.exception.ResourceNotFoundException;
import com.seun.crossfitWodAPI.repository.MembersRepository;
import com.seun.crossfitWodAPI.repository.RecordsRepository;
import com.seun.crossfitWodAPI.repository.WorkoutRepository;
import com.seun.crossfitWodAPI.service.RecordsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecordsServiceImpl implements RecordsService {
    private final RecordsRepository recordsRepository;
    private final MembersRepository membersRepository;
    private final WorkoutRepository workoutRepository;

    @Override
    public List<Records> getAllRecords(Integer pageNo, Integer elementPerPage) {
        Pageable recordsPage = PageRequest.of(pageNo, elementPerPage);
        log.info("Fetching list of all records" );
        return recordsRepository.findAll(recordsPage).getContent();
    }

    @Override
    public List<Records> getRecordsByMembersId(Long memberId, Integer pageNo, Integer elementPerPage) {
        if (recordsRepository.findByMemberId(memberId).isEmpty()) {
            throw new ResourceNotFoundException("Records do not exist for this member");
        }
        return recordsRepository.findByMemberId(memberId);
    }

    @Override
    public List<Records> getRecordsByWorkoutId(Long workoutId, Integer pageNo, Integer elementPerPage) {
        if (recordsRepository.findByWorkoutId(workoutId).isEmpty()) {
            throw new ResourceNotFoundException("Records do not exist for this workout");
        }
        return recordsRepository.findByWorkoutId(workoutId);
    }

    @Override
    @Transactional
    public Records createNewRecord(RecordsDTO recordsDTO) {
        if(Objects.isNull(recordsDTO)) {
            throw new BadRequestException("No request body found");
        }
        if(recordsDTO.getRecord() == null || recordsDTO.getWorkoutId() == null  || recordsDTO.getMemberId() == null) {
            throw new BadRequestException("Either one of record, workout Id, or member Id is missing");
        }
        return recordsRepository.save(Records.builder()
                .members(membersRepository.getById(recordsDTO.getMemberId()))
                .record(recordsDTO.getRecord())
                .workoutId(recordsDTO.getWorkoutId())
                .memberId(recordsDTO.getMemberId())
                .wod(workoutRepository.getById(recordsDTO.getWorkoutId()))
                .createdAt(new Timestamp(new Date().getTime()))
                .build());
    }

    @Override
    public void deleteOneRecord(Long recordId) {
        if (!recordsRepository.existsById(recordId)) {
            throw new ResourceNotFoundException("Record does not exist");
        }  else {
            recordsRepository.deleteById(recordId);
        }
    }
}
