package com.example.adambackend.payload.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StackDTO {
    private String label;
    private Integer total;
    private Integer isActive;
}
