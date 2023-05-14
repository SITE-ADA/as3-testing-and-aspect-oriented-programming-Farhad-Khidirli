package az.edu.ada.playlist.service.implementation;

import az.edu.ada.playlist.entity.Playlist;
import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import az.edu.ada.playlist.mapper.PlaylistMapper;
import az.edu.ada.playlist.repository.PlaylistRepository;
import az.edu.ada.playlist.service.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
    }

    @Override
    public Optional<PlaylistDTO> getById(Long id) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(id);
        if (playlistOptional.isPresent()) {
            PlaylistDTO playlistDTO = playlistMapper.toDto(playlistOptional.get());
            return Optional.of(playlistDTO);
        } else {
            return Optional.empty();
        }
    }


    public List<PlaylistDTO> list() {
        List<Playlist> playlists = playlistRepository.findAll();
        return playlistMapper.toPlaylistDTOList(playlists);
    }

    @Override
    public PlaylistDTO update(Long id, PlaylistDTO playlistDTO) {
        Playlist playlist = playlistMapper.toEntity(playlistDTO);

        playlist.setId(id);

        Playlist updatedPlaylist = playlistRepository.save(playlist);
        return playlistMapper.toDto(updatedPlaylist);
    }


    @Override
    public void delete(Long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public PlaylistDTO save(PlaylistDTO playlistDTO) {
        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        Playlist savedPlaylist = playlistRepository.save(playlist);
        return playlistMapper.toDto(savedPlaylist);
    }

}
