package com.ifs.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DecisionCount {
	private String _id; 
    private long count;
}
