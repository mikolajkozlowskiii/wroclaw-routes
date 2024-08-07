package com.wroclawroutes.routes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReverseGeocodingResponse {
    private List<ReverseGeocodingLocation> hits;
}
