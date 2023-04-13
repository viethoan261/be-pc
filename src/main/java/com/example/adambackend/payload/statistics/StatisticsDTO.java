package com.example.adambackend.payload.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDTO {
    private ExceptDTO accounts;
    private ExceptDTO products;
    private OrdersDTO orders;
    private StackedDTO stacked;
    private CommentsDTO comments;
}
