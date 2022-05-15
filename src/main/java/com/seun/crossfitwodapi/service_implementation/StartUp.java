package com.seun.crossfitwodapi.service_implementation;

import com.seun.crossfitwodapi.domain.Members;
import com.seun.crossfitwodapi.domain.Roles;
import com.seun.crossfitwodapi.domain.Workout;
import com.seun.crossfitwodapi.repository.MembersRepository;
import com.seun.crossfitwodapi.repository.RolesRepository;
import com.seun.crossfitwodapi.repository.WorkoutRepository;
import com.seun.crossfitwodapi.service.MembersService;
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

    private final RolesRepository rolesRepository;

    @Override
    public void run(String... args) {
        Workout workout1 = workoutRepository.findById(1L).orElse(null);
        if (workout1 == null){
            workoutRepository.save(
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
            );
        }


        Workout workout2 = workoutRepository.findById(2L).orElse(null);
        if (workout2 == null){
            workoutRepository.save(
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
            );
        }

        Members member1 = membersRepository.findById(1L).orElse(null);
        if (member1 == null){
            membersRepository.save(
                    Members.builder().name("Seun")
                            .gender("female")
                            .dob(LocalDate.of(2009,8,7))
                            .email("seun@gmail.com")
                            .username("seun@gmail.com")
                            .password(passwordEncoder.encode("1234"))
                            .createdAt(new Timestamp(new Date().getTime()))
                            .updatedAt(new Timestamp(new Date().getTime()))
                            .build()
            );
        }

        Members member2 = membersRepository.findById(2L).orElse(null);
        if (member2 == null){
            membersRepository.save(
                    Members.builder().name("Mike")
                            .gender("Male")
                            .dob(LocalDate.of(1988,4,23))
                            .email("mike@gmail.com")
                            .username("mike@gmail.com")
                            .password(passwordEncoder.encode("4321"))
                            .createdAt(new Timestamp(new Date().getTime()))
                            .updatedAt(new Timestamp(new Date().getTime()))
                            .build()
            );
        }

        rolesRepository.save(Roles.builder().name("USER").build());
        rolesRepository.save(Roles.builder().name("ADMIN").build());
        membersService.addRoleToMembers("seun@gmail.com", "ADMIN");
        membersService.addRoleToMembers("mike@gmail.com", "USER");


    }
}
