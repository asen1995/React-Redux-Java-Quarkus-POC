package com.poc.tableentryservice.mapper;

import com.poc.tableentryservice.dto.CreateTableEntryDto;
import com.poc.tableentryservice.dto.TableEntryDto;
import com.poc.tableentryservice.entity.TableEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct mapper for converting between TableEntry entities and DTOs.
 */
@Mapper(componentModel = "cdi")
public interface TableEntryMapper {

    /**
     * Converts a TableEntry entity to a response DTO.
     *
     * @param entity the entity to convert
     * @return the response DTO
     */
    TableEntryDto toDto(TableEntry entity);

    /**
     * Converts a list of TableEntry entities to a list of response DTOs.
     *
     * @param entities the entities to convert
     * @return the list of response DTOs
     */
    List<TableEntryDto> toDtoList(List<TableEntry> entities);

    /**
     * Converts a create DTO to a TableEntry entity.
     *
     * @param dto the create DTO
     * @return the entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TableEntry toEntity(CreateTableEntryDto dto);

    /**
     * Updates an existing entity with values from a create DTO.
     *
     * @param dto    the DTO with new values
     * @param entity the entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(CreateTableEntryDto dto, @MappingTarget TableEntry entity);
}
