package com.adeies.adeies.enterprise.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table( name = "ADEIES")
public class Adeies {

    @OneToOne(
            fetch = FetchType.LAZY,
            targetEntity = Employee.class
    )
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "employee_id"
    )
    @Id
    private Long employeeId;
    @Column(
            name = "regular_day_of"
    )
    private Integer regularDayOff;
    @Column(
            name = "maternity_leave"
    )
    private Integer maternityLeave;
    @Column(
            name = "birth_leave"
    )
    private Integer birthLeave;
    @Column(
            name = "ivf_leave"
    )
    private Integer ivfLeave;
    @Column(
            name = "oaed_maternity_leave"
    )
    private Integer oaedMaternityLeave;
    @Column(
            name = "nursing_leave"
    )
    private Integer nursingLeave;
    @Column(
            name = "adoption_leave"
    )
    private Integer adoptionLeave;
    @Column(
            name = "paternity_leave"
    )
    private Integer paternityLeave;
    @Column(
            name = "single_parent_leave"
    )
    private Integer singleParentLeave;
    @Column(
            name = "school_performance_leave"
    )
    private Integer schoolPerformanceLeave;
    @Column(
            name = "dependent_member_leave"
    )
    private Integer dependentMemberLeave;
    @Column(
            name = "marriage_leave"
    )
    private Integer marriageLeave;
    @Column(
            name = "blood_donation"
    )
    private Integer bloodDonation;
    @Column(
            name = "grief_leave"
    )
    private Integer griefLeave;
    @Column(
            name = "student_leave"
    )
    private Integer studentLeave;
    @Column(
            name = "election_leave"
    )
    private Integer electionLeave;
    @Column(
            name = "court_attendance_leave"
    )
    private Integer courtAttendanceLeave;
    @Column(
            name = "strike"
    )
    private Integer strike;
    @Column(
            name = "union_leave"
    )
    private Integer unionLeave;
}
