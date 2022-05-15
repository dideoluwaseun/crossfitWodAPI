package com.seun.crossfitwodapi.service_implementation;

import com.seun.crossfitwodapi.domain.Members;
import com.seun.crossfitwodapi.domain.Records;
import com.seun.crossfitwodapi.domain.Workout;
import com.seun.crossfitwodapi.domain.dto.RecordsDTO;
import com.seun.crossfitwodapi.exception.BadRequestException;
import com.seun.crossfitwodapi.exception.ResourceNotFoundException;
import com.seun.crossfitwodapi.repository.RecordsRepository;
import com.seun.crossfitwodapi.service.RecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecordsServiceImpl implements RecordsService {
    private final RecordsRepository recordsRepository;

    @Override
    public List<Records> getAllRecords(Integer pageNo, Integer elementPerPage) {
        return recordsRepository.findAll();
    }

    @Override
    public Records getRecordsByMembersId(Members memberId) {
        return recordsRepository.findByMembers(memberId);
    }

    @Override
    public Records getRecordsByWorkoutId(Workout workoutId) {
        return recordsRepository.findByWod(workoutId);
    }

    @Override
    @Transactional
    public Records createNewRecord(RecordsDTO recordsDTO) {
        if(recordsDTO.getRecord() == null || recordsDTO.getWorkoutId() == null  || recordsDTO.getMemberId() == null) {
            throw new BadRequestException("Either one of record, workout Id, or member Id is missing");
        }
        return recordsRepository.save(Records.builder()
                .record(recordsDTO.getRecord())
                .wod(recordsDTO.getWorkoutId())
                .members(recordsDTO.getMemberId())
                .createdAt(new Timestamp(new Date().getTime()))
                .build());
    }

    @Override
    public void deleteOneRecord(Long recordId) {
        boolean recordsExists = recordsRepository.existsById(recordId);
        if (!recordsExists) {
            throw new ResourceNotFoundException();
        } else {
            recordsRepository.deleteById(recordId);
        }
    }
}
