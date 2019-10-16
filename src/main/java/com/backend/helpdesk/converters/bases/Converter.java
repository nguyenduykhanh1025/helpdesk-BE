package com.backend.helpdesk.converters.bases;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Converter<S, T> {
    public abstract T convert(S source);

    public List<T> convert(List<S> source){
        return source.stream().map(this::convert).collect(Collectors.toList());
    }
}
