package com.example.DoAnCDIO3.configuration;

import com.example.DoAnCDIO3.entity.Role;
import com.example.DoAnCDIO3.enums.RoleEnum;
import com.example.DoAnCDIO3.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            log.info("Bắt đầu khởi tạo dữ liệu mặc định...");

            initRoles();

            log.info("Khởi tạo dữ liệu hoàn tất!");
        };
    }

    // Khởi tạo các role mặc định
    private void initRoles() {

        createRoleIfNotExists(
                RoleEnum.ADMIN,
                "Quản trị viên hệ thống, toàn quyền kiểm soát"
        );

        createRoleIfNotExists(
                RoleEnum.OWNER,
                "Chủ sân, có quyền quản lý sân và lịch đặt"
        );

        createRoleIfNotExists(
                RoleEnum.USER,
                "Khách hàng thông thường"
        );
    }

    // Hàm dùng chung để tạo role nếu chưa tồn tại
    private void createRoleIfNotExists(RoleEnum roleEnum, String description) {

        if (!roleRepository.existsByName(roleEnum.name())) {

            Role role = Role.builder()
                    .name(roleEnum.name())
                    .description(description)
                    .build();

            roleRepository.save(role);

            log.info("Đã tạo role: {}", roleEnum.name());
        }
    }
}
