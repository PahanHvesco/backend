package com.myapp.backend.mapper.interfaces;

public interface IMapper <Dto, Entity>{
    public Dto entityToDto(Entity entity);
    public Entity dtoToEntity(Dto dto);
}
