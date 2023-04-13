package com.example.adambackend.payload.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StackedDTO {
    private StackDTO colors;
    private StackDTO materials;
    private StackDTO categories;
    private StackDTO tags;
}
