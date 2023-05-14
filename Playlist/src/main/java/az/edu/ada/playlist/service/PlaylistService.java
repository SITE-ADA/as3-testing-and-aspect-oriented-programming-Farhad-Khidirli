package az.edu.ada.playlist.service;

import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import java.util.List;
import java.util.Optional;

public interface PlaylistService {

    List<PlaylistDTO> list();

    Optional<PlaylistDTO> getById(Long id);

    void delete(Long id);

    PlaylistDTO save(PlaylistDTO playlistDTO);

    PlaylistDTO update(Long id, PlaylistDTO playlistDTO);
}
