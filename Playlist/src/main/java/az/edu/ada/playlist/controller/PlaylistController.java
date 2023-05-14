package az.edu.ada.playlist.controller;

import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import az.edu.ada.playlist.service.PlaylistService;
import az.edu.ada.playlist.service.implementation.PlaylistServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistServiceImpl.class);

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/list")
    public List<PlaylistDTO> listPlaylists() {
        return playlistService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PlaylistDTO>> getPlaylistById(@PathVariable("id") Long id) {
        Optional<PlaylistDTO> playlistDTO = playlistService.getById(id);
        if (playlistDTO.isEmpty()) {
            LOGGER.warn("Failed to GET playlist with id {} because it does not exist", id);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            LOGGER.warn("Successfully GET playlist with id {}. Playlist data: {}", id, playlistDTO);
            return ResponseEntity.ok(playlistDTO);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable("id") Long id) {
        playlistService.delete(id);
    }


    @PostMapping("/")
    public ResponseEntity<PlaylistDTO> savePlaylist(@Valid @RequestBody PlaylistDTO playlistDTO) {
        PlaylistDTO savedPlaylistDTO = playlistService.save(playlistDTO);
        return ResponseEntity.created(URI.create("/" + savedPlaylistDTO.getId())).body(savedPlaylistDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDTO> updatePlaylist(@PathVariable("id") Long id, @Valid @RequestBody PlaylistDTO playlistDTO) {
        PlaylistDTO updatedPlaylistDTO = playlistService.update(id, playlistDTO);
        return ResponseEntity.ok(updatedPlaylistDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlaylistDTO> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        PlaylistDTO updatedPlaylist = playlistService.patch(id, updates);
        if (updatedPlaylist == null) {
            LOGGER.warn("Failed to PATCH update playlist with id {} because it does not exist", id);
            return ResponseEntity.notFound().build();
        } else {
            LOGGER.info("PATCH updated playlist with id {}. Updated playlist data: {}", id, updatedPlaylist);
            return ResponseEntity.ok(updatedPlaylist);
        }
    }


}
