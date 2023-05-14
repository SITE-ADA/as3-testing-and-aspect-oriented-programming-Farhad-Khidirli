package az.edu.ada.playlist.mapper;

import az.edu.ada.playlist.entity.Playlist;
import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface PlaylistMapper {

    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    @Mapping(source = "id", target = "id")
    PlaylistDTO toDto(Playlist playlist);

    @Mapping(source = "id", target = "id")
    Playlist toEntity(PlaylistDTO playlistDto);

    default List<PlaylistDTO> toPlaylistDTOList(List<Playlist> playlists) {
        return playlists.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default PlaylistDTO toPlaylistDTOOrNull(Playlist playlist) {
        return playlist == null ? null : toDto(playlist);
    }
}