package com.nmotillon.athena.mapper;

import com.nmotillon.athena.dto.BookDTO;
import com.nmotillon.athena.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO toDto(Book book);

    @Mapping(target = "owners", ignore = true)
    Book toEntity(BookDTO bookDTO);

    @Mapping(target = "owners", ignore = true)
    void updateEntityWithDto(BookDTO bookDTO, @MappingTarget Book book);
}
