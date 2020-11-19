package com.engagement.model.dto;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * A batch contains all of the information needed about a batch.
 * This will be used to show the client more information about a batch they are mapped to.
 *
 * @author  Kelsey Iafrate
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Batch {
    private String batchId;
    private String name;
    private String startDate;
    private String endDate;
    private String skill; //list of skills being learned
    private String location;
    private String type;
    private int goodGrade;
    private int passingGrade;
    private int currentWeek;
    private List<EmployeeAssignment> employeeAssignments;
    private List<AssociateAssignment> associateAssignments;
}
