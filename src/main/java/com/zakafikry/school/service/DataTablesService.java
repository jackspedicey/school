package com.zakafikry.school.service;

import com.zakafikry.school.dto.DataTablesInput;
import com.zakafikry.school.dto.DataTablesOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class DataTablesService {

    public <T> DataTablesOutput<T> getDataTableOutput(DataTablesInput input,
                                                      JpaRepository<T, ?> repository,
                                                      JpaSpecificationExecutor<T> specificationExecutor,
                                                      Specification<T> spec) {
        Sort sort = Sort.by(input.getSortDirection().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, input.getSortColumn());

        Pageable pageable = PageRequest.of(input.getStart() / input.getLength(),
                input.getLength(),
                sort);

        Page<T> page = specificationExecutor.findAll(spec, pageable);

        DataTablesOutput<T> output = new DataTablesOutput<>();
        output.setDraw(input.getDraw());
        output.setRecordsTotal(repository.count());
        output.setRecordsFiltered(page.getTotalElements());
        output.setData(page.getContent());

        return output;
    }
}