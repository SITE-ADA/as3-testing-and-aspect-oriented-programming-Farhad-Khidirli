package az.edu.ada.playlist.service.implementation;

import az.edu.ada.playlist.entity.Playlist;
import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import az.edu.ada.playlist.mapper.PlaylistMapper;
import az.edu.ada.playlist.repository.PlaylistRepository;
import az.edu.ada.playlist.service.PlaylistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistMapper playlistMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistServiceImpl.class);

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, PlaylistMapper playlistMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
    }

    @Override
    public Optional<PlaylistDTO> getById(Long id) {
        LOGGER.info("Getting playlist by id {}", id);
        long startTime = System.currentTimeMillis();

        Optional<Playlist> playlistOptional = playlistRepository.findById(id);

        long endTime = System.currentTimeMillis();
        LOGGER.info("Got playlist by id {} in {}ms", id, endTime - startTime);
        if (playlistOptional.isPresent()) {
            PlaylistDTO playlistDTO = playlistMapper.toDto(playlistOptional.get());
            return Optional.of(playlistDTO);
        } else {
            return Optional.empty();
        }
    }


    public List<PlaylistDTO> list() {
        LOGGER.info("Listing all playlists");
        long startTime = System.currentTimeMillis();

        List<Playlist> playlists = playlistRepository.findAll();

        long endTime = System.currentTimeMillis();
        LOGGER.info("Listed all playlists in {}ms", endTime - startTime);
        return playlistMapper.toPlaylistDTOList(playlists);
    }

    @Override
    public PlaylistDTO update(Long id, PlaylistDTO playlistDTO) {
        LOGGER.info("Updating playlist with id {} with data {}", id, playlistDTO);
        long startTime = System.currentTimeMillis();
        Playlist playlist = playlistMapper.toEntity(playlistDTO);

        playlist.setId(id);

        long endTime = System.currentTimeMillis();
        Playlist updatedPlaylist = playlistRepository.save(playlist);
        LOGGER.info("Updated playlist with id {} in {}ms. Updated playlist data: {}", id, endTime - startTime, updatedPlaylist);
        return playlistMapper.toDto(updatedPlaylist);
    }


    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting playlist with id {}", id);
        long startTime = System.currentTimeMillis();

        playlistRepository.deleteById(id);

        long endTime = System.currentTimeMillis();
        LOGGER.info("Deleted playlist with id {} in {}ms", id, endTime - startTime);
    }

    @Override
    public PlaylistDTO save(PlaylistDTO playlistDTO) {
        LOGGER.info("Saving playlist with data {}", playlistDTO);
        long startTime = System.currentTimeMillis();

        Playlist playlist = playlistMapper.toEntity(playlistDTO);
        Playlist savedPlaylist = playlistRepository.save(playlist);

        long endTime = System.currentTimeMillis();
        PlaylistDTO savedPlaylistDTO = playlistMapper.toDto(savedPlaylist);
        LOGGER.info("Saved playlist with id {} in {}ms. Saved playlist data: {}", savedPlaylistDTO.getId(), endTime - startTime, savedPlaylistDTO);
        return savedPlaylistDTO;
    }

    @Override
    public PlaylistDTO patch(Long id, Map<String, Object> updates) {
        LOGGER.info("Patching playlist with id {} with updates {}", id, updates);
        long startTime = System.currentTimeMillis();

        Optional<Playlist> playlistOptional = playlistRepository.findById(id);
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            for (Map.Entry<String, Object> entry : updates.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                switch (key) {
                    case "name" -> playlist.setName((String) value);
                    case "description" -> playlist.setDescription((String) value);
                }
            }
            Playlist savedPlaylist = playlistRepository.save(playlist);
            PlaylistDTO playlistDTO = playlistMapper.toDto(savedPlaylist);

            long endTime = System.currentTimeMillis();
            LOGGER.info("Patched playlist with id {} in {}ms. Patched playlist data: {}", id, endTime - startTime, playlistDTO);
            return playlistDTO;

        } else {
            LOGGER.warn("Failed to patch playlist with id {} because it does not exist", id);
            return null;
        }
    }


}
