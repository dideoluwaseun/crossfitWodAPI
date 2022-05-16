package com.seun.crossfitWodAPI.serviceImpl;

import com.seun.crossfitWodAPI.domain.Members;
import com.seun.crossfitWodAPI.domain.Roles;
import com.seun.crossfitWodAPI.domain.Workout;
import com.seun.crossfitWodAPI.domain.dto.RecordsDTO;
import com.seun.crossfitWodAPI.repository.MembersRepository;
import com.seun.crossfitWodAPI.repository.RolesRepository;
import com.seun.crossfitWodAPI.repository.WorkoutRepository;
import com.seun.crossfitWodAPI.service.MembersService;
import com.seun.crossfitWodAPI.service.RecordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StartUp implements CommandLineRunner {
    private final WorkoutRepository workoutRepository;
    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;
    private final MembersService membersService;
    private final RecordsService recordsService;
    @Override
    public void run(String... args) throws Exception {
        workoutRepository.findById(1L).orElse(workoutRepository.save(
                Workout.builder().name("Tommy V")
                        .mode("For Time")
                        .equipment(List.of("barbell", "rope"))
                        .createdAt(new Timestamp(new Date().getTime()))
                        .updatedAt(new Timestamp(new Date().getTime()))
                        .exercises(List.of("21 thrusters",
                                        "12 rope climbs, 15 ft",
                                        "15 thrusters",
                                        "9 rope climbs, 15 ft",
                                        "9 thrusters",
                                        "6 rope climbs, 15 ft"))
                        .trainerTips(List.of("Split the 21 thrusters as needed",
                                "Try to do the 9 and 6 thrusters unbroken",
                                "RX Weights: 115lb/75lb"))
                        .build()
        ));
        workoutRepository.findById(2L).orElse(workoutRepository.save(
                Workout.builder().name("Dead Push-Ups")
                        .mode("AMRAP 10")
                        .equipment(List.of("barbell"))
                        .createdAt(new Timestamp(new Date().getTime()))
                        .updatedAt(new Timestamp(new Date().getTime()))
                        .exercises(List.of("15 deadlifts",
                                "15 hand-release push-ups"))
                        .trainerTips(List.of("Deadlifts are meant to be light and fast",
                                "Try to aim for unbroken sets",
                                "RX Weights: 135lb/95lb"))
                        .build()
        ));
        Members members = membersRepository.findById(1L).orElse(null);
        if(members == null) {
    membersRepository.save(
                Members.builder().name("Seun")
                        .gender("female")
                        .dob(LocalDate.of(2009,8,07))
                        .email("seun@gmail.com")
                        .username("seun@gmail.com")
                        .password(passwordEncoder.encode("1234"))
                        .createdAt(new Timestamp(new Date().getTime()))
                        .updatedAt(new Timestamp(new Date().getTime()))
                        .build()
        );}
        membersService.saveRole(
                Roles.builder().name("USER").build()
        );
        membersService.saveRole(
                Roles.builder().name("ADMIN").build()
        );
        membersService.addRoleToMembers("seun@gmail.com", "ADMIN");
        membersService.addRoleToMembers("seun@gmail.com", "USER");
//        recordsService.createNewRecord(RecordsDTO.builder().record("10 reps").workoutId(workoutRepository.findById(1L)).build());
//        membersService.addRoleToMembers("mike@gmail.com", "USER");

    }
}
