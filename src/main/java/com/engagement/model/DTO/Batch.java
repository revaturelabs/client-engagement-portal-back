package com.engagement.model.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
     String batchId;
     String name;
     String startDate;
     String endDate;
     String skill;
     String location;
     String type;
     int goodGrade;
     int passingGrade;
     int currentWeek;
     List<EmployeeAssignment> employeeAssignments;
     List<AssociateAssignment> associateAssignments;
}
