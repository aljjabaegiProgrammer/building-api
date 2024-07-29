package com.geonlee.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Member Entity
 *
 * @author GEONLEE
 * @since 2024-07-23<br />
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "member")
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pw")
    private String password;

    @Column(name = "member_nm")
    private String memberName;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "birth_dt")
    private LocalDate birthDate;

    @Column(name = "age")
    private Integer age;

    @Column(name = "height")
    private Double height;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "atk")
    private String accessToken;

    @Column(name = "rtk")
    private String refreshToken;

    @Column(name = "pw_update_dt")
    private LocalDate passwordUpdateDate;

    @Column(name = "login_attempts")
    private Integer loginAttemptsCount;
}
