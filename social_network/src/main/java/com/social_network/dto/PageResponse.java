package com.social_network.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Builder
public class PageResponse<T> {

    private int currentPage;

    private int pageSize;

    private int totalPage;

    private long totalElement;

    @Builder.Default
    private List<T> data = Collections.emptyList();

}
