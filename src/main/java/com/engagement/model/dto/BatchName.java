package com.engagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * A DTO that returns a subset of batch information, namely batch ID and name.
 * This will be used to populate a dropdown menu.
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchName {
    private String batchId;
    private String name;
}
