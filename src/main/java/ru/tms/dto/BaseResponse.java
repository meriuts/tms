package ru.tms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BaseResponse <T> {
    private List<Faults> faults;
    private List<T> data;

    public BaseResponse(List<Faults> faults) {
        this.faults = faults;
    }

    public BaseResponse(List<Faults> faults, List<T> data) {
        this.faults = faults;
        this.data = data;
    }
}
