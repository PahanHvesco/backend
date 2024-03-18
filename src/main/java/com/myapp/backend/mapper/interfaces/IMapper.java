package com.myapp.backend.mapper.interfaces;

public interface IMapper <D, E>{
    public D entityToDto(E entity);
    public E dtoToEntity(D dto);
}
