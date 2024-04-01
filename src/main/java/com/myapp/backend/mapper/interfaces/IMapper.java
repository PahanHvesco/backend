package com.myapp.backend.mapper.interfaces;

public interface IMapper<D, E> {
    D entityToDto(E entity);

    E dtoToEntity(D dto);
}
